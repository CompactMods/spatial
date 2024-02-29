package dev.compactmods.spatial.aabb;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AABBHelper {

    public static Vec3 minCorner(AABB aabb) {
        return new Vec3(aabb.minX, aabb.minY, aabb.minZ);
    }

    public static Vec3 maxCorner(AABB aabb) {
        return new Vec3(aabb.maxX, aabb.maxY, aabb.maxZ);
    }

    /**
     * Given an area, normalizes the bounds so that the minimum coordinates
     * all equal zero. (i.e. 5,5,5 to 11,11,11 becomes 0,0,0 to 6,6,6)
     * @param boundaries Original AABB instance.
     * @return A new instance of AABB, normalized to zero coordinates.
     */
    public static AABB normalize(AABB boundaries) {
        Vec3 offset = minCorner(boundaries).reverse();
        return boundaries.move(offset);
    }

    public static AABB normalizeWithin(AABB source, AABB within) {
        Vec3 offset = minCorner(source).subtract(minCorner(within)).reverse();
        return source.move(offset);
    }

    public static AABB alignFloor(AABB source, AABB within) {
        double targetY = within.minY;
        return alignFloor(source, targetY);
    }

    public static AABB alignFloor(AABB source, double targetY) {
        double offset = source.minY - targetY;
        return source.move(0, offset * -1, 0);
    }

    public static String toString(AABB aabb) {
        return "%s,%s,%s".formatted(aabb.getXsize(), aabb.getYsize(), aabb.getZsize());
    }
}