package dev.compactmods.spatial.test.core;

import dev.compactmods.spatial.test.core.SpatialTestMod;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.neoforged.testframework.annotation.OnInit;
import net.neoforged.testframework.annotation.RegisterStructureTemplate;
import net.neoforged.testframework.gametest.StructureTemplateBuilder;

public class EmptyTestSizes {

   public static final String ONE_CUBED = "1x1x1";
   public static final String FIFTEEN_CUBED = "15x15x15";

   @RegisterStructureTemplate(SpatialTestMod.MOD_ID + ":empty_1_cubed")
   public static final StructureTemplate empty_1 = StructureTemplateBuilder.empty(1, 1, 1);

   @RegisterStructureTemplate(SpatialTestMod.MOD_ID + ":empty_15_cubed")
   public static final StructureTemplate empty_15 = StructureTemplateBuilder.empty(15, 15, 15);

   @OnInit
   public static void init() {}
}
