package com.sekwah.advancedportals.forge;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.connector.commands.CommandRegister;
import com.sekwah.advancedportals.core.module.AdvancedPortalsModule;
import com.sekwah.advancedportals.core.util.GameScheduler;
import com.sekwah.advancedportals.forge.connector.command.ForgeCommandRegister;
import com.sekwah.advancedportals.forge.connector.container.ForgeServerContainer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("advancedportals")
public class AdvancedPortalsMod {

    public static final String MOD_ID = "advancedportals";

    private static final Logger LOGGER = LogManager.getLogger(AdvancedPortalsMod.MOD_ID);

    private AdvancedPortalsCore portalsCore;

    @Inject
    private ForgeCommandRegister commandRegister;

    public AdvancedPortalsMod() {
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

        // Using the private methods to trigger allows for use of the dependency injection better.
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);

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

    private void onServerStarting(RegisterCommandsEvent event) {
        this.commandRegister.registerCommandsEvent(event);
    }

}
