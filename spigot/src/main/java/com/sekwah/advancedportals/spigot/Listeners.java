package com.sekwah.advancedportals.spigot;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.CoreListeners;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.spigot.connector.container.SpigotPlayerContainer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {

    @Inject
    private CoreListeners coreListeners;

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        coreListeners.playerJoin(new SpigotPlayerContainer(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
       // coreListeners.playerLeave(new SpigotPlayerContainer(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.isCancelled()) {
            Location blockloc = event.getBlock().getLocation();
            this.coreListeners.blockPlace(new SpigotPlayerContainer(event.getPlayer()),
                    new BlockLocation(blockloc.getWorld().getName(), blockloc.getBlockX(), blockloc.getBlockY(), blockloc.getBlockZ()), event.getBlockPlaced().getType().toString(),
                    event.getItemInHand().getType().toString(), event.getItemInHand().getItemMeta().getDisplayName());
        }
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        if (!event.isCancelled() && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null) {
            Location blockloc = event.getClickedBlock().getLocation();
            boolean allowEvent = this.coreListeners.playerInteractWithBlock(new SpigotPlayerContainer(event.getPlayer()),
                    event.getClickedBlock().getType().toString(),
                    event.getMaterial().toString(),
                    event.getItem().getItemMeta().getDisplayName(),
                    new BlockLocation(blockloc.getWorld().getName(), blockloc.getBlockX(), blockloc.getBlockY(), blockloc.getBlockZ()),
                    event.getAction() == Action.LEFT_CLICK_BLOCK);
            event.setCancelled(!allowEvent);
        }
    }

}
