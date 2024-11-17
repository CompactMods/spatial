package dev.compactmods.spatial.random;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.stream.Stream;

public class RandomSourceExtras {

    public static double randomDouble(RandomSource random) {
        boolean minNegative = random.nextBoolean(); // remove bias towards positive min
        double rangeMin = (random.nextInt() + random.nextDouble()) * (minNegative ? -1 : 1);
        double rangeMax = Mth.floor(rangeMin) + random.nextInt(10) + random.nextDouble();
        return randomDouble(random, rangeMin, rangeMax);
    }

    public static double randomDouble(RandomSource random, double min, double max) {
        // Switch sides if caller did a whoops
        if(max < min) return randomDouble(random, max, min);

        double randomDouble = random.nextDouble() * (max - min) + min;
        return Mth.clamp(randomDouble, min, max);
    }

    public static Vec3 randomVec3(RandomSource random) {
        return new Vec3(randomDouble(random), randomDouble(random), randomDouble(random));
    }

    public static Vec3 randomVec3(RandomSource random, AABB within) {
        return new Vec3(
                randomDouble(random, within.minX, within.maxX),
                randomDouble(random, within.minY, within.maxY),
                randomDouble(random, within.minZ, within.maxZ));
    }

    public static Stream<Vec3> randomVec3Stream(RandomSource random) {
        return Stream.generate(() -> randomVec3(random));
    }

    public static Stream<Vec3> randomVec3Stream(RandomSource random, AABB within) {
        return Stream.generate(() -> randomVec3(random, within));
    }
}
