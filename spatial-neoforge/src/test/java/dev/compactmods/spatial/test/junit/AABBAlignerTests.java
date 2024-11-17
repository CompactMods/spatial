package dev.compactmods.spatial.test.junit;

import dev.compactmods.spatial.aabb.AABBAligner;
import dev.compactmods.spatial.aabb.AABBHelper;
import dev.compactmods.spatial.random.RandomSourceExtras;
import dev.compactmods.spatial.test.util.MCAssertions;
import dev.compactmods.spatial.test.util.TestUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AABBAlignerTests {

    @Test
    public void canFloorToY0() {
        // Source minY = 5
        AABB before = AABB.ofSize(new Vec3(0, 7.5, 0), 5, 5, 5);

        // Align to Y-0
        final var after = AABBAligner.floor(before, 0);

        Assertions.assertEquals(5, before.minY, "Before was modified in-place rather than immutably moved.");
        Assertions.assertEquals(0, after.minY, "After y level should be zero. (was: %s)".formatted(after.minY));
        Assertions.assertEquals(5, after.getYsize(), "AABB size was modified; should have remained the same.");
    }

    @Test
    public void canFloorToAnotherAABB() {
        // Source minY = 5
        AABB before = AABB.ofSize(Vec3.ZERO.relative(Direction.UP, 7.5), 5, 5, 5);

        // Target minY = 1 (bounds are Y 1-11)
        AABB bounds = AABB.ofSize(Vec3.ZERO.relative(Direction.UP, 6), 10, 10, 10);

        // Align to Y-0
        final var after = AABBAligner.floor(before, bounds);

        Assertions.assertEquals(5, before.minY, "Before was modified in-place rather than immutably moved.");
        Assertions.assertEquals(1, after.minY, "After y level should be 1. (was: %s)".formatted(after.minY));

        Assertions.assertEquals(5, after.getYsize(), "AABB size was modified; should have remained the same.");
    }

    @Test
    public void canNormalize() {
        final var random = RandomSource.create();

        // Target boundaries, placed from {0,0,0} - {10,10,10}
        final var bounds = AABB.ofSize(Vec3.ZERO, 10, 10, 10);

        // Randomly placed boundaries of size {5,5,5}
        final var randomBounds = AABB.ofSize(RandomSourceExtras.randomVec3(random), 5, 5, 5);

        final var aligned = AABBAligner.create(bounds, randomBounds)
                .normalize()
                .align();

        final var EXPECTED_CENTER = new Vec3(-2.5, -2.5, -2.5);
        final var EXPECTED_CORNER = AABBHelper.minCorner(bounds);
        final var EXPECTED_SIZE = new Vector3d(5, 5, 5);

        MCAssertions.assertVec3Equals(aligned.getCenter(), EXPECTED_CENTER);
        MCAssertions.assertVec3Equals(AABBHelper.minCorner(aligned), EXPECTED_CORNER);
        MCAssertions.assertVec3Equals(AABBHelper.sizeOf(aligned), EXPECTED_SIZE);
    }

    @Test
    public void canAlignOnBlockPosition() {
        final var random = RandomSource.create();

        AABB randomAABB = TestUtils.randomAABB(random);
        final var originalSize = AABBHelper.sizeOf(randomAABB);

        // Center on 1, 2, 3
        final var aligned = AABBAligner.create(randomAABB)
                .center(new BlockPos(1, 2, 3))
                .align();

        // Assert center position
        MCAssertions.assertVec3Equals(new Vec3(1.5, 2.5, 3.5), aligned.getCenter());

        // Assert size did not change
        MCAssertions.assertVec3Equals(originalSize, AABBHelper.sizeOf(aligned));
    }
}
