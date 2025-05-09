package com.sekwah.advancedportals.forge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

/**
 * Events to wrap and pass to AP
 */
@EventBusSubscriber(modid = AdvancedPortalsMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class Events {
    @SubscribeEvent
    public static void onEntityUpdate(LivingFallEvent event) {
        // Do chakra updates and other stuff here unless handled in capabilities
    }
}
