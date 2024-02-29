package dev.compactmods.spatial;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.level.validation.DirectoryValidator;
import net.neoforged.bus.EventBus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

@Mod("spatial")
public class SpatialTestMod {

    private static final Logger LOG = LogManager.getLogger();

    public SpatialTestMod() {
        NeoForge.EVENT_BUS.addListener(this::onServerStarted);
    }

    public void onServerStarted(final ServerStartedEvent evt) {
        final MinecraftServer server = evt.getServer();

        // Add "test/resources" as a resource pack to the pack repository
        final var packs = server.getPackRepository();

        final String test_resources = System.getenv("TEST_RESOURCES");
        if(test_resources != null) {

            final var testPack = new FolderRepositorySource(Path.of(test_resources), PackType.SERVER_DATA, PackSource.DEFAULT, new DirectoryValidator(l -> true));
            packs.addPackFinder(testPack);
            packs.reload();

            // add "file/resources" to selected pack list
            final ImmutableSet<String> toSelect = ImmutableSet.<String>builder()
                    .addAll(packs.getSelectedIds())
                    .add("file/test_pack")
                    .build();

            packs.setSelected(toSelect);

            try {
                server.reloadResources(packs.getSelectedIds()).get();

            } catch (InterruptedException | ExecutionException e) {
                LOG.error("Failed to reload test resource packs.", e);
            }
        }
    }
}
