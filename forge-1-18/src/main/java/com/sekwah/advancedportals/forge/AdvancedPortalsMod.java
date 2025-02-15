package com.sekwah.advancedportals.forge;

import com.google.inject.Injector;
import com.mojang.logging.LogUtils;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.core.module.AdvancedPortalsModule;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.forge.connector.command.ForgeCommandRegister;
import com.sekwah.advancedportals.forge.connector.container.ForgeServerContainer;
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

    public static final String MOD_ID = "advancedportals";

    private static final Logger LOGGER = LogUtils.getLogger();

    private AdvancedPortalsCore portalsCore;

    public AdvancedPortalsMod() {
        // TODO make sure that this doesnt crash trying to run on a client pack

        String version = ModList.get().getModContainerById("minecraft").get().getModInfo().getVersion().toString();


        ForgeServerContainer serverContainer =
                new ForgeServerContainer();

        this.portalsCore = new AdvancedPortalsCore(
                version, FMLPaths.CONFIGDIR.get().toFile(),
                new ForgeInfoLogger(LOGGER), new ForgeServerContainer());
        AdvancedPortalsModule module = this.portalsCore.getModule();

        module.addInstanceBinding(CommandRegister.class,
                new ForgeCommandRegister());

        Injector injector = module.getInjector();

        injector.injectMembers(this);
        injector.injectMembers(this.portalsCore);
        injector.injectMembers(serverContainer);

//        Listeners listeners = injector.getInstance(Listeners.class);
//        injector.injectMembers(listeners);
//        this.getServer().getPluginManager().registerEvents(listeners, this);

        GameScheduler scheduler = injector.getInstance(GameScheduler.class);
//        this.getServer().getScheduler().scheduleSyncRepeatingTask(
//                this, scheduler::tick, 1, 1);

//        SpigotWarpEffects warpEffects = new SpigotWarpEffects();
//        injector.injectMembers(warpEffects);
//        warpEffects.registerEffects();

        // Try to do this after setting up everything that would need to be
        // injected to.
        this.portalsCore.onEnable();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {

    }

}
