package dev.compactmods.spatial.test.core;

import dev.compactmods.spatial.test.core.SpatialTestMod;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.testframework.conf.Feature;
import net.neoforged.testframework.conf.FrameworkConfiguration;

@Mod(SpatialTestMod.MOD_ID)
public class SpatialGametests {

    public SpatialGametests(ModContainer container, IEventBus modBus) {
        final var config = FrameworkConfiguration
                .builder(ResourceLocation.fromNamespaceAndPath(SpatialTestMod.MOD_ID,  "tests"))
                .enable(Feature.GAMETEST)
                .enable(Feature.MAGIC_ANNOTATIONS)
                .build();

        var fw = config.create();
        fw.registerCommands(Commands.literal("spatial-test"));
        fw.init(modBus, container);
    }

}
