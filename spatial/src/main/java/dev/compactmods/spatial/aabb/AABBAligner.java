package dev.compactmods.spatial.aabb;

import dev.compactmods.spatial.direction.DirectionalMath;
import dev.compactmods.spatial.vector.VectorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.joml.Vector3dc;

/**
 * Utility for aligning an AABB instance inside a boundary.
 */
public abstract class AABBAligner {

    /**
     * How large the AABB instance to be aligned is.
     */
    protected final Vector3d size;

    /**
     * Where the center of the AABB instance will be after alignment.
     */
    protected Vector3d center;

    private AABBAligner(Vector3d center, Vector3d size) {
        this.size = size;
        this.center = center;
    }

    public static AABBAligner.Bounded create(AABB boundaries, AABB pendingAlign) {
        return new AABBAligner.Bounded(boundaries, VectorUtils.convert3d(pendingAlign.getCenter()), AABBHelper.sizeOf(pendingAlign));
    }

    public static AABBAligner.Unbounded create(AABB original) {
        return new AABBAligner.Unbounded(original);
    }

    public AABB align() {
        return AABB.ofSize(VectorUtils.convert3d(center), size.x, size.y, size.z);
    }

    public static AABB center(AABB source, Vec3 center) {
        final var size = AABBHelper.sizeOf(source);
        return AABB.ofSize(center, size.x, size.y, size.z);
    }

    public static AABB center(AABB source, Vector3dc center) {
        final var size = AABBHelper.sizeOf(source);
        return AABB.ofSize(VectorUtils.convert3d(center), size.x, size.y, size.z);
    }

    public static AABB floor(AABB source, AABB within) {
        double targetY = within.minY;
        return floor(source, targetY);
    }

    public static AABB floor(AABB source, double targetY) {
        double offset = source.minY - targetY;
        return source.move(0, offset * -1, 0);
    }

    /**
     * Given an area, normalizes the bounds so that the minimum coordinates
     * all equal zero. (i.e. 5,5,5 to 11,11,11 becomes 0,0,0 to 6,6,6)
     * @param boundaries Original AABB instance.
     * @return A new instance of AABB, normalized to zero coordinates.
     */
    public static AABB normalize(AABB boundaries) {
        var offset = AABBHelper.minCorner(boundaries).negate(new Vector3d());
        return boundaries.move(offset.x, offset.y, offset.z);
    }

    public static class Bounded extends AABBAligner {

        /**
         * Alignment boundaries.
         */
        private final AABB targetBoundaries;

        private Bounded(AABB outerBounds, Vector3d center, Vector3d size) {
            super(center, size);
            this.targetBoundaries = outerBounds;
        }

        /**
         * Changes the center point of the AABB to the centerpoint of the boundaries.
         *
         * @return Aligner instance, for chaining
         */
        public AABBAligner.Bounded center() {
            this.center = VectorUtils.convert3d(targetBoundaries.getCenter());
            return this;
        }

        /**
         * Changes the center point of the AABB to an exact location.
         * Must be within the outer boundaries.
         *
         * @param center The position to align on.
         * @return Aligner instance, for chaining
         */
        public AABBAligner.Bounded center(Vector3dc center) {
            this.center = new Vector3d(center);
            return this;
        }

        /**
         * Changes the center point of the AABB to an exact location.
         * Must be within the outer boundaries.
         *
         * @param center The position to align on.
         * @return Aligner instance, for chaining
         */
        public AABBAligner.Bounded center(Vec3 center) {
            if(!targetBoundaries.contains(center)) return this;
            this.center = VectorUtils.convert3d(center);
            return this;
        }

        public AABBAligner.Bounded normalize() {
            final var corner = new Vector3d();
            corner.sub(AABBHelper.minCorner(targetBoundaries))
                .negate()
                .add(new Vector3d(size.x / 2, size.y / 2, size.z / 2));

            return this.center(corner);
        }

        /**
         * Given a direction, aligns the AABB instance so the "walls" of the boundaries align
         * in the given direction.
         *
         * @param direction
         * @return
         */
        public AABBAligner.Bounded boundedDirection(Direction direction) {
            var outerEdge = DirectionalMath.directionalEdge(direction, targetBoundaries);
            var targetPoint = DirectionalMath.directionalEdge(direction, center, size);

            var offset = outerEdge.sub(targetPoint);
            center.add(offset);

            return this;
        }
    }

    public static class Unbounded extends AABBAligner {
        public Unbounded(AABB original) {
            super(VectorUtils.convert3d(original.getCenter()), AABBHelper.sizeOf(original));
        }

        public AABBAligner.Unbounded center(Vector3dc center) {
            this.center = new Vector3d(center);
            return this;
        }

        public AABBAligner.Unbounded center(Vec3 center) {
            this.center = VectorUtils.convert3d(center);
            return this;
        }

        /**
         * Sets the center point to the center of a given block position.
         *
         * @param center The block position to center on.
         * @return The aligner, for chaining.
         */
        public AABBAligner.Unbounded center(BlockPos center) {
            this.center = VectorUtils.convert3d(Vec3.atCenterOf(center));
            return this;
        }

        /**
         * Changes the targeted minimum position (min X-Y-Z corner) to a target position.
         *
         * @param target The min corner to target.
         * @return The aligner, for chaining.
         */
        public AABBAligner.Unbounded normalize(Vector3dc target) {
            var offset = new Vector3d(center.x() - (size.x() / 2),
                    center.y() - (size.y() / 2),
                    center.z() - (size.z() / 2));

            offset.sub(target).negate();
            this.center = offset;
            return this;
        }
    }
}
