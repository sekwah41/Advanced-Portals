package com.sekwah.advancedportals.forge;

import com.mojang.logging.LogUtils;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

@Mod("advancedportals")
@Mod.EventBusSubscriber(modid = AdvancedPortalsMod.MOD_ID)
public class AdvancedPortalsMod {

    public static final String MOD_ID = "narutomod";

    private static final Logger LOGGER = LogUtils.getLogger();

    //private AdvancedPortalsCore portalsCore;

    public AdvancedPortalsMod() {
        String version = ModList.get().getModContainerById("minecraft").get().getModInfo().getVersion().toString();

        /*this.portalsCore = new AdvancedPortalsCore(
            version, FMLPaths.CONFIGDIR.get().toFile(),
            new ForgeInfoLogger(LOGGER), null);*/

       /* this.portalsCore = new AdvancedPortalsCore(
                matcher.find() ? matcher.group(1) : "0.0.0", this.getDataFolder(),
                new SpigotInfoLogger(this), serverContainer);*/
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {

    }

}
