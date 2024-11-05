package dev.compactmods.spatial.test.junit;

import dev.compactmods.spatial.aabb.AABBHelper;
import dev.compactmods.spatial.test.TestUtils;
import dev.compactmods.spatial.test.junit.util.MCAssertions;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.stream.Stream;

public class AABBHelperTests {

    @Test
    public void canFloorToY0() {
        // Source minY = 5
        AABB before = AABB.ofSize(new Vec3(0, 7.5, 0), 5, 5, 5);

        // Align to Y-0
        final var after = AABBHelper.alignFloor(before, 0);

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
        final var after = AABBHelper.alignFloor(before, bounds);

        Assertions.assertEquals(5, before.minY, "Before was modified in-place rather than immutably moved.");
        Assertions.assertEquals(1, after.minY, "After y level should be 1. (was: %s)".formatted(after.minY));

        Assertions.assertEquals(5, after.getYsize(), "AABB size was modified; should have remained the same.");
    }

    @Test
    public void normalizeToZero() {
        AABB before = AABB.ofSize(Vec3.ZERO.relative(Direction.UP, 7.5), 5, 5, 5);

        // Align to Y-0
        final var after = AABBHelper.normalize(before);

        Assertions.assertEquals(5, before.minY, "Before was modified in-place rather than immutably moved.");

        Assertions.assertEquals(0, after.minX, "After x level was not zero (was: %s)".formatted(after.minX));
        Assertions.assertEquals(0, after.minY, "After y level was not zero (was: %s)".formatted(after.minY));
        Assertions.assertEquals(0, after.minZ, "After z level was not zero (was: %s)".formatted(after.minZ));

        Assertions.assertEquals(5, after.getYsize(), "AABB size was modified; should have remained the same.");
    }

    @TestFactory
    public static Stream<DynamicTest> normalizeBoundaryTests() {
        final var random = RandomSource.create();

        return Stream.concat(
                        TestUtils.randomVec3Stream(random).limit(10),

                        // Ensure at least one negative and one positive bound are part of the test
                        Stream.of(
                                Vec3.ZERO.subtract(-3, -2, 5),
                                Vec3.ZERO.add(2, 5, 1)
                        )
                ).map(randomOffset -> DynamicTest.dynamicTest(
                        "normalize_boundaries_%s".formatted(randomOffset.toString()),
                        () -> normalizeIntoBoundaries(randomOffset)
                ));
    }

    private static void normalizeIntoBoundaries(Vec3 randomOffset) {
        AABB before = AABB.ofSize(Vec3.ZERO.relative(Direction.UP, 7.5), 5, 5, 5);
        AABB bounds = AABB.ofSize(randomOffset, 5, 5, 5);

        final var after = AABBHelper.normalizeWithin(before, bounds);

        Assertions.assertEquals(5, before.minY, "Before was modified in-place rather than immutably moved.");

        MCAssertions.assertVec3Equals(AABBHelper.minCorner(after), AABBHelper.minCorner(bounds));

        Assertions.assertEquals(5, after.getYsize(), "AABB size was modified; should have remained the same.");
    }
}
