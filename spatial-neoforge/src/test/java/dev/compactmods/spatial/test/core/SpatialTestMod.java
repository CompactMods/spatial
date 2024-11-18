package dev.compactmods.spatial.test.core;

import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.testframework.conf.Feature;
import net.neoforged.testframework.conf.FrameworkConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SpatialTestMod.MOD_ID)
public class SpatialTestMod {

    public static final String MOD_ID = "spatial_test";
    private static final Logger LOGS = LogManager.getLogger("spatial");

    public SpatialTestMod(ModContainer container, IEventBus modBus) {
        LOGS.debug("Spatial test mod loaded.");

//        final var config = FrameworkConfiguration
//                .builder(ResourceLocation.fromNamespaceAndPath(SpatialTestMod.MOD_ID,  "tests"))
//                .enable(Feature.GAMETEST)
//                .enable(Feature.MAGIC_ANNOTATIONS)
//                .build();
//
//        var fw = config.create();
//        fw.registerCommands(Commands.literal("spatial-test"));
//        fw.init(modBus, container);
    }
}
