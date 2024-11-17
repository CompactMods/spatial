package dev.compactmods.spatial.test.junit;

import dev.compactmods.spatial.random.RandomSourceExtras;
import dev.compactmods.spatial.test.util.MCAssertions;
import dev.compactmods.spatial.vector.VectorUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomSourceExtrasTests {

    @Test
    public void canGenerateRandomDoubles() {
        RandomSource random = RandomSource.create();

        Assertions.assertDoesNotThrow(() -> {
            RandomSourceExtras.randomDouble(random);
        });

        double zeroAndOne = RandomSourceExtras.randomDouble(random, 0, 1);
        double negative = RandomSourceExtras.randomDouble(random, -10, -1);
        double positive = RandomSourceExtras.randomDouble(random, 1, 10);

        Assertions.assertTrue(zeroAndOne > 0);
        Assertions.assertTrue(negative < 0);
        Assertions.assertTrue(positive > 0);
    }

    @Test
    public void canGenerateRandomVecWithinBounds() {
        RandomSource random = RandomSource.create();
        final var bounds = AABB.ofSize(Vec3.ZERO, 10, 10, 10);

        for(int i = 0; i < 25; i++) {
            var randomV3 = RandomSourceExtras.randomVec3(random, bounds);

            Assertions.assertTrue(bounds.contains(randomV3), "Random generated outside of boundaries: " + randomV3.toString());
        }
    }

    @Test
    public void canStreamRandomVectorsInBoundaries() {
        RandomSource random = RandomSource.create();
        final var bounds = AABB.ofSize(Vec3.ZERO, 10, 10, 10);

        RandomSourceExtras.randomVec3Stream(random, bounds)
                .limit(10)
                .forEach(pos -> Assertions.assertTrue(bounds.contains(pos)));
    }
}
