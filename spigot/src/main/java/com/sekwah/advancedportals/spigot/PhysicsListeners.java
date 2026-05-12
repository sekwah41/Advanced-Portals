package com.sekwah.advancedportals.spigot;

import com.sekwah.advancedportals.core.CoreListeners;
import com.sekwah.advancedportals.shadowed.inject.Inject;
import com.sekwah.advancedportals.spigot.utils.ContainerHelpers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class PhysicsListeners implements Listener {
    @Inject
    private CoreListeners coreListeners;

    @EventHandler(priority = EventPriority.HIGH)
    public void onPhysicsEvent(BlockPhysicsEvent event) {
        if (!coreListeners.physicsEvent(
                ContainerHelpers.toBlockLocation(
                    event.getBlock().getLocation()))) {
            event.setCancelled(true);
        }
    }
}
