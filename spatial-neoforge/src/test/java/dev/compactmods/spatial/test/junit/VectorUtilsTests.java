package dev.compactmods.spatial.test.junit;

import dev.compactmods.spatial.test.util.MCAssertions;
import dev.compactmods.spatial.vector.VectorUtils;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.junit.jupiter.api.Test;

public class VectorUtilsTests {

    @Test
    public void testConvertVec3i() {
        Vec3i pos = new Vec3i(1, 2, 3);
        Vector3d converted = VectorUtils.convert3d(pos);

        MCAssertions.assertVec3Equals(new Vector3d(1, 2, 3), converted);
    }

    @Test
    public void testConvertVector3dc() {
        Vector3dc vec = new Vector3d(2.5, 2.5, 2.5);
        Vec3 converted = VectorUtils.convert3d(vec);

        MCAssertions.assertVec3Equals(new Vec3(2.5, 2.5, 2.5), converted);
    }
}
