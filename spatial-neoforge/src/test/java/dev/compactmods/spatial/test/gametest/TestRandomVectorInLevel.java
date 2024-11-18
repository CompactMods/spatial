package dev.compactmods.spatial.test.gametest;

import dev.compactmods.spatial.random.RandomSourceExtras;
import dev.compactmods.spatial.test.core.EmptyTestSizes;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;
import net.neoforged.testframework.gametest.EmptyTemplate;

@ForEachTest(groups = "in_level_randoms")
public class TestRandomVectorInLevel {

    @TestHolder
    @GameTest
    @EmptyTemplate(EmptyTestSizes.ONE_CUBED)
    public static void checkRandomVector3(final GameTestHelper testHelper) {
        final var random = testHelper.getLevel().random;

        try {
            final var vec3 = RandomSourceExtras.randomVec3(random);
        }

        catch (final Exception e) {
            testHelper.fail(e.getMessage());
        }

        testHelper.succeed();
    }
}
