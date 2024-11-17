package dev.compactmods.spatial.test.util;

import dev.compactmods.spatial.random.RandomSourceExtras;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TestUtils {

    public static AABB randomAABB(RandomSource random) {
        final var position = RandomSourceExtras.randomVec3(random, AABB.ofSize(Vec3.ZERO, 50, 50, 50));
        final var size = RandomSourceExtras.randomVec3(random);

        return AABB.ofSize(position, size.x, size.y, size.z);
    }
}
