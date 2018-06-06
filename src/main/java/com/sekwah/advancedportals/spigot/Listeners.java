package com.sekwah.advancedportals.spigot;

import com.google.inject.name.Named;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.CoreListeners;
import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;

public class Listeners implements Listener {

    private CoreListeners coreListeners = AdvancedPortalsCore.getCoreListeners();

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        coreListeners.playerJoin(new PlayerContainer(event.getPlayer()));
    }

    @EventHandler
    public void onJoinEvent(PlayerQuitEvent event) {
        coreListeners.playerLeave(new PlayerContainer(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.isCancelled()) {
            Location blockloc = event.getBlock().getLocation();
            this.coreListeners.blockPlace(new PlayerContainer(event.getPlayer()),
                    new PortalLocation(blockloc.getWorld().getName(), blockloc.getBlockX(), blockloc.getBlockY(), blockloc.getBlockZ()), event.getBlockPlaced().getType().toString(),
                    event.getItemInHand().getType().toString(), event.getItemInHand().getItemMeta().getDisplayName());
        }
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        if (!event.isCancelled() && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null) {
            Location blockloc = event.getClickedBlock().getLocation();
            boolean allowEvent = this.coreListeners.playerInteractWithBlock(new PlayerContainer(event.getPlayer()), event.getMaterial().toString(),
                    event.getItem().getItemMeta().getDisplayName(),
                    new PortalLocation(blockloc.getWorld().getName(), blockloc.getBlockX(), blockloc.getBlockY(), blockloc.getBlockZ()),
                    event.getAction() == Action.LEFT_CLICK_BLOCK);
            event.setCancelled(!allowEvent);
        }
    }

}
