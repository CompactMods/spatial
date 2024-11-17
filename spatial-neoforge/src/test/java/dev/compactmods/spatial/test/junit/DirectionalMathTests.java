package dev.compactmods.spatial.test.junit;

import dev.compactmods.spatial.aabb.AABBHelper;
import dev.compactmods.spatial.direction.DirectionalMath;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3dc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DirectionalMathTests {

    @Test
    public void canAlignUp() {
        AABB boundaries = AABBHelper.zeroOriginSized(10);

        Vec3 centerCenter = boundaries.getCenter();
        Vector3dc topCenter = DirectionalMath.directionalEdge(Direction.UP, boundaries);

        Assertions.assertEquals(centerCenter.x, topCenter.x());
        Assertions.assertEquals(boundaries.maxY, topCenter.y());
        Assertions.assertEquals(centerCenter.z, topCenter.z());
    }

    @Test
    public void canAlignDown() {
        AABB boundaries = AABBHelper.zeroOriginSized(10);

        Vec3 centerCenter = boundaries.getCenter();
        Vector3dc bottomCenter = DirectionalMath.directionalEdge(Direction.DOWN, boundaries);

        Assertions.assertEquals(centerCenter.x, bottomCenter.x());
        Assertions.assertEquals(boundaries.minY, bottomCenter.y());
        Assertions.assertEquals(centerCenter.z, bottomCenter.z());
    }

    @Test
    public void canAlignNorth() {
        AABB boundaries = AABBHelper.zeroOriginSized(10);

        Vec3 centerCenter = boundaries.getCenter();
        Vector3dc northCenter = DirectionalMath.directionalEdge(Direction.NORTH, boundaries);

        Assertions.assertEquals(0, northCenter.x());
        Assertions.assertEquals(centerCenter.y, northCenter.y());
        Assertions.assertEquals(-5, northCenter.z());
    }

    @Test
    public void canAlignWest() {
        AABB boundaries = AABBHelper.zeroOriginSized(10);

        Vec3 centerCenter = boundaries.getCenter();
        Vector3dc westCenter = DirectionalMath.directionalEdge(Direction.WEST, boundaries);

        Assertions.assertEquals(-5, westCenter.x());
        Assertions.assertEquals(centerCenter.y, westCenter.y());
        Assertions.assertEquals(0, westCenter.z());
    }
}
