package dev.compactmods.spatial.aabb;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class BlockBreakerController {
    private final Stream<BlockPos> currentStream;
    private final Iterator<BlockPos> currentIterator;
    private BlockPos currentBlockPos = null;
    private BlockPos nextBlockPos = null;
    private Vec3 lastPos = Vec3.ZERO;
    private Vec3 currentPos = Vec3.ZERO;
    private Vec3 nextPos = Vec3.ZERO;
    private int ticksBetweenOperation = 20;
    private int currentTicks = 0;
    private final Consumer<BlockPos> operation;
    private boolean finished = false;

    BlockBreakerController(Stream<BlockPos> stream, BlockPos startingBlockPos, int ticks, Consumer<BlockPos> op)
    {
        //will return an empty stream if startingBlockPosition is outside of stream
        stream = stream.dropWhile(blockPos -> blockPos != startingBlockPos);
        currentStream = stream;
        currentIterator = stream.iterator();
        ticksBetweenOperation = ticks;
        currentTicks = 0;
        operation = op;
        nextBlockPos = currentBlockPos = getNext();
        if (currentBlockPos != null)
           lastPos = currentPos = nextPos = Vec3.atCenterOf(currentBlockPos);
        else finished = true;
    }

    BlockBreakerController(Stream<BlockPos> stream, int ticks, Consumer<BlockPos> op)
    {
        currentStream = stream;
        currentIterator = stream.iterator();
        ticksBetweenOperation = ticks;
        currentTicks = 0;
        operation = op;
        nextBlockPos = currentBlockPos = getNext();
        if (currentBlockPos != null)
            lastPos = currentPos = nextPos = Vec3.atCenterOf(currentBlockPos);
        else finished = true;
    }

    private BlockPos getNext() {
        BlockPos next = null;
        if (currentIterator.hasNext()) next = currentIterator.next();
        return next;
    }

    public void Update()
    {
        if (isFinished()) return;
        currentTicks++;

        currentPos = lastPos.lerp(nextPos, (double) currentTicks/ticksBetweenOperation);

        if(currentTicks <= ticksBetweenOperation) return;
        currentTicks = 0;
        lastPos = nextPos;
        currentBlockPos = nextBlockPos;

        operation.accept(currentBlockPos);

        nextBlockPos = getNext();
        if (nextBlockPos == null) finished = true;
        else
            nextPos = Vec3.atCenterOf(nextBlockPos);
    }

    public Vec3 getCurrentPos() {
        return currentPos;
    }
    public int getCurrentTicks() {
        return currentTicks;
    }
    public boolean isFinished() {
        return finished;
    }
}
