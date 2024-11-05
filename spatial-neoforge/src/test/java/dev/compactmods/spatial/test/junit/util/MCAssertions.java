package dev.compactmods.spatial.test.junit.util;

import com.google.common.math.DoubleMath;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Assertions;

public class MCAssertions {
    public static void assertVec3Equals(Vec3 actual, Vec3 expected) {
        if(!DoubleMath.fuzzyEquals(actual.x, expected.x, 0.001))
            Assertions.fail("X did not match expected value (was: %s; expected: %s)".formatted(actual.x, expected.x));

        if(!DoubleMath.fuzzyEquals(actual.y, expected.y, 0.001))
            Assertions.fail("Y did not match expected value (was: %s; expected: %s)".formatted(actual.y, expected.y));

        if(!DoubleMath.fuzzyEquals(actual.z, expected.z, 0.001))
            Assertions.fail("Z did not match expected value (was: %s; expected: %s)".formatted(actual.z, expected.z));
    }
}
