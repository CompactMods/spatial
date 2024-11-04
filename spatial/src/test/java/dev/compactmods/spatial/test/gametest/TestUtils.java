package dev.compactmods.spatial.test.gametest;

import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import java.util.stream.Stream;

public class TestUtils {

    public static Stream<Vec3> randomVec3Stream(RandomSource random) {
        return Stream.generate(() -> new Vec3(random.nextDouble(), random.nextDouble(), random.nextDouble()));
    }
}
