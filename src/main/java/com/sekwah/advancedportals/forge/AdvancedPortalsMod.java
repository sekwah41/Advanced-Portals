package com.sekwah.advancedportals.forge;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.forge.coreconnector.command.ForgeCommandRegister;
import com.sekwah.advancedportals.forge.coreconnector.info.ForgeDataCollector;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * The mod is not needed client side but will add some useful additions
 */
@Mod(modid = AdvancedPortalsMod.modid, name = "Advanced Portals", version = AdvancedPortalsCore.version, acceptableRemoteVersions = "*")
public class AdvancedPortalsMod {

    public static final String modid = "advancedportals";

    public static final Logger logger = LogManager.getLogger("Advanced Portals");

    private AdvancedPortalsCore portalsCore;

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
        this.portalsCore = new AdvancedPortalsCore(new File(event.getModConfigurationDirectory(), modid),
                new ForgeInfoLogger(logger), new ForgeDataCollector(), new int[] {1,12,2});
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLServerStartingEvent event)
    {
        this.portalsCore.registerCommands(new ForgeCommandRegister(event));
    }

}
