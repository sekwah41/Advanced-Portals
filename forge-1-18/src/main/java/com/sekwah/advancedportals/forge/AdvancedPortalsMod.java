package com.sekwah.advancedportals.forge;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod("advancedportals")
@Mod.EventBusSubscriber(modid = AdvancedPortalsMod.MOD_ID)
public class AdvancedPortalsMod {

    public static final String MOD_ID = "narutomod";

    private static final Logger LOGGER = LogUtils.getLogger();

    public AdvancedPortalsMod() {

    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {
        NarutoCommands.register(event.getDispatcher());
    }

}
