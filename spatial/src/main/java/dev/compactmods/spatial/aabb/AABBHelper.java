package dev.compactmods.spatial.aabb;

import dev.compactmods.spatial.vector.VectorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.stream.Stream;

public abstract class AABBHelper {

    public static Vector3d sizeOf(AABB aabb) {
        return new Vector3d(aabb.getXsize(), aabb.getYsize(), aabb.getZsize());
    }

    public static AABB zeroOriginSized(double size) {
        return AABB.ofSize(Vec3.ZERO, size, size, size);
    }

    public static AABB zeroOriginSized(Vector3dc size) {
        return AABB.ofSize(Vec3.ZERO, size.x(), size.y(), size.z());
    }

    public static boolean fitsInside(AABB aabb, Vec3 dimensions) {
        return fitsInside(aabb, VectorUtils.convert3d(dimensions));
    }

    public static boolean fitsInside(AABB aabb, Vector3dc dimensions) {
        return dimensions.x() <= aabb.getXsize() &&
                dimensions.y() <= aabb.getYsize() &&
                dimensions.z() <= aabb.getZsize();
    }

    public static boolean fitsInside(AABB outer, AABB inner) {
        if(!fitsInside(inner, sizeOf(outer))) return false;
        return outer.contains(VectorUtils.convert3d(minCorner(inner))) &&
            outer.contains(VectorUtils.convert3d(maxCorner(outer)));
    }

    public static Stream<BlockPos> blocksInside(AABB bounds) {
        return BlockPos.betweenClosedStream(bounds.contract(1, 1, 1));
    }

    public static Stream<BlockPos> allCorners(AABB bounds) {
        Stream.Builder<BlockPos> stream = Stream.builder();
        stream.add(BlockPos.containing(bounds.maxX - 1, bounds.maxY - 1, bounds.maxZ - 1));
        stream.add(BlockPos.containing(bounds.minX, bounds.maxY - 1, bounds.maxZ - 1));
        stream.add(BlockPos.containing(bounds.maxX - 1, bounds.minY, bounds.maxZ - 1));
        stream.add(BlockPos.containing(bounds.minX, bounds.minY, bounds.maxZ - 1));
        stream.add(BlockPos.containing(bounds.maxX - 1, bounds.maxY - 1, bounds.minZ));
        stream.add(BlockPos.containing(bounds.minX, bounds.maxY - 1, bounds.minZ));
        stream.add(BlockPos.containing(bounds.maxX - 1, bounds.minY, bounds.minZ));
        stream.add(BlockPos.containing(bounds.minX, bounds.minY, bounds.minZ));
        return stream.build();
    }

    public static Vector3dc minCorner(AABB aabb) {
        return new Vector3d(aabb.minX, aabb.minY, aabb.minZ);
    }

    public static ChunkPos minCornerChunk(AABB aabb) {
        var mn = BlockPos.containing(aabb.minX, aabb.minY, aabb.minZ);
        return new ChunkPos(mn);
    }

    public static Vector3dc maxCorner(AABB aabb) {
        return new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ);
    }

    public static ChunkPos maxCornerChunk(AABB aabb) {
        var mx = BlockPos.containing(aabb.maxX, aabb.maxY, aabb.maxZ);
        return new ChunkPos(mx);
    }

    public static Stream<ChunkPos> chunkPositions(AABB aabb) {
        return ChunkPos.rangeClosed(minCornerChunk(aabb), maxCornerChunk(aabb));
    }

    public static String toString(AABB aabb) {
        return "%s,%s,%s".formatted(aabb.getXsize(), aabb.getYsize(), aabb.getZsize());
    }

    //region Deprecated
    @Deprecated
    public static AABB alignFloor(AABB source, AABB within) {
        return AABBAligner.floor(source, within);
    }

    @Deprecated
    public static AABB alignFloor(AABB source, double targetY) {
        return AABBAligner.floor(source, targetY);
    }
    //endregion
}