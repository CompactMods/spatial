package dev.compactmods.spatial.test.junit;

import dev.compactmods.spatial.aabb.AABBAligner;
import dev.compactmods.spatial.aabb.AABBHelper;
import dev.compactmods.spatial.random.RandomSourceExtras;
import dev.compactmods.spatial.test.util.MCAssertions;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

public class AABBHelperTests {

    @Test
    public void normalizeToZero() {
        AABB before = AABB.ofSize(Vec3.ZERO.relative(Direction.UP, 7.5), 5, 5, 5);

        // Align to Y-0
        final var after = AABBAligner.normalize(before);

        Assertions.assertEquals(5, before.minY, "Before was modified in-place rather than immutably moved.");

        Assertions.assertEquals(0, after.minX, "After x level was not zero (was: %s)".formatted(after.minX));
        Assertions.assertEquals(0, after.minY, "After y level was not zero (was: %s)".formatted(after.minY));
        Assertions.assertEquals(0, after.minZ, "After z level was not zero (was: %s)".formatted(after.minZ));

        Assertions.assertEquals(5, after.getYsize(), "AABB size was modified; should have remained the same.");
    }

    @TestFactory
    public Stream<DynamicTest> normalizeBoundaryTests() {
        final var random = RandomSource.create();

        return Stream.concat(
                        RandomSourceExtras.randomVec3Stream(random).limit(10),

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

        final var after = AABBAligner.create(bounds, before)
                .normalize()
                .align();

        Assertions.assertEquals(5, before.minY, "Before was modified in-place rather than immutably moved.");
        MCAssertions.assertVec3Equals(AABBHelper.minCorner(bounds), AABBHelper.minCorner(after));
        Assertions.assertEquals(5, after.getYsize(), "AABB size was modified; should have remained the same.");
    }
}
