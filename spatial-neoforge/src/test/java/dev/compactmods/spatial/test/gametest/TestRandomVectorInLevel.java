package dev.compactmods.spatial.test.gametest;

import dev.compactmods.spatial.random.RandomSourceExtras;
import dev.compactmods.spatial.test.core.EmptyTestSizes;
import dev.compactmods.spatial.test.core.SpatialTestMod;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;
import net.neoforged.testframework.gametest.EmptyTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ForEachTest(groups = "in_level_randoms")
public class TestRandomVectorInLevel {

    private static final Logger LOGS = LogManager.getLogger("spatial");

    @TestHolder
    @GameTest
    @EmptyTemplate(EmptyTestSizes.ONE_CUBED)
    public static void checkRandomVector3(GameTestHelper testHelper) {
        final var random = testHelper.getLevel().random;

        try {
            final var vec3 = RandomSourceExtras.randomVec3(random);
        }

        catch (final Exception e) {
            LOGS.error(e);
            testHelper.fail(e.getMessage());
        }

        testHelper.succeed();
    }
}
