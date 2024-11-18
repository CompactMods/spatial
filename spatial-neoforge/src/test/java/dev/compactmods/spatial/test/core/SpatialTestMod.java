package dev.compactmods.spatial.test.core;

import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SpatialTestMod.MOD_ID)
public class SpatialTestMod {

    public static final String MOD_ID = "spatial_test";
    private static final Logger LOGS = LogManager.getLogger("spatial");

    public SpatialTestMod() {
        LOGS.debug("Spatial test mod loaded.");
    }
}
