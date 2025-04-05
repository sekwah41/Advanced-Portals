package com.sekwah.advancedportals.legacyspigot;

import com.sekwah.advancedportals.core.CoreListeners;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.legacyspigot.connector.container.LegacySpigotEntityContainer;
import com.sekwah.advancedportals.legacyspigot.connector.container.LegacySpigotPlayerContainer;
import com.sekwah.advancedportals.legacyspigot.utils.ContainerHelpers;
import com.sekwah.advancedportals.shadowed.inject.Inject;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

/**
 * Some of these will be passed to the core listener to handle the events,
 * others it's easier to just check directly.
 */
public class Listeners implements Listener {
    @Inject
    private CoreListeners coreListeners;

    @Inject
    private PortalServices portalServices;

    @Inject
    private ConfigRepository configRepository;

    // Entity and portal events
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        coreListeners.playerJoin(
            new LegacySpigotPlayerContainer(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        coreListeners.playerLeave(
            new LegacySpigotPlayerContainer(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMoveEvent(PlayerMoveEvent event) {
        Location to = event.getTo();
        coreListeners.playerMove(
            new LegacySpigotPlayerContainer(event.getPlayer()),
            ContainerHelpers.toPlayerLocation(to));
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityPortalEvent(EntityPortalEvent event) {
        if (!this.coreListeners.entityPortalEvent(
                new LegacySpigotEntityContainer(event.getEntity()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPortalEvent(PlayerPortalEvent event) {
        if (!this.coreListeners.playerPortalEvent(
                new LegacySpigotPlayerContainer(event.getPlayer()),
                ContainerHelpers.toPlayerLocation(event.getFrom()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player
            && (event.getCause() == EntityDamageEvent.DamageCause.LAVA
                || event.getCause() == EntityDamageEvent.DamageCause.FIRE
                || event.getCause()
                    == EntityDamageEvent.DamageCause.FIRE_TICK)) {
            if (this.coreListeners.preventEntityCombust(
                    new LegacySpigotEntityContainer(event.getEntity()))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCombustEntityEvent(EntityCombustEvent event) {
        if (this.coreListeners.preventEntityCombust(
                new LegacySpigotEntityContainer(event.getEntity()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.isCancelled()) {
            Location blockloc = event.getBlock().getLocation();

            if (blockloc.getWorld() == null)
                return;

            if (event.getItemInHand().getItemMeta() == null)
                return;

            String displayName =
                event.getItemInHand().getItemMeta().getDisplayName();

            if (!this.coreListeners.blockPlace(
                    new LegacySpigotPlayerContainer(event.getPlayer()),
                    new BlockLocation(
                        blockloc.getWorld().getName(), blockloc.getBlockX(),
                        blockloc.getBlockY(), blockloc.getBlockZ()),
                    event.getBlockPlaced().getType().toString(),
                    event.getItemInHand().getType().toString(), displayName)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPhysicsEvent(BlockPhysicsEvent event) {
        if (!coreListeners.physicsEvent(
                ContainerHelpers.toBlockLocation(
                    event.getBlock().getLocation()),
                event.getBlock().getType().toString())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWorldChangeEvent(PlayerChangedWorldEvent event) {
        coreListeners.worldChange(
            new LegacySpigotPlayerContainer(event.getPlayer()));
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        if (!event.isCancelled()
            && (event.getAction() == Action.LEFT_CLICK_BLOCK
                || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            && event.getItem() != null) {
            if (event.getClickedBlock() == null)
                return;
            if (event.getItem().getItemMeta() == null)
                return;

            Location blockloc = event.getClickedBlock().getLocation();

            if (blockloc.getWorld() == null)
                return;

            boolean allowEvent = this.coreListeners.playerInteractWithBlock(
                new LegacySpigotPlayerContainer(event.getPlayer()),
                event.getClickedBlock().getType().toString(),
                event.getMaterial().toString(),
                event.getItem().getItemMeta().getDisplayName(),
                new BlockLocation(blockloc.getWorld().getName(),
                                  blockloc.getBlockX(), blockloc.getBlockY(),
                                  blockloc.getBlockZ()),
                event.getAction() == Action.LEFT_CLICK_BLOCK);
            event.setCancelled(!allowEvent);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void spawnMobEvent(CreatureSpawnEvent event) {
        if (event.getSpawnReason()
                == CreatureSpawnEvent.SpawnReason.NETHER_PORTAL
            && portalServices.inPortalRegionProtected(
                ContainerHelpers.toPlayerLocation(event.getLocation()))) {
            event.setCancelled(true);
        }
    }

    // Block events

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockFromTo(BlockFromToEvent event) {
        if (!configRepository.getStopWaterFlow()) {
            return;
        }
        if (!coreListeners.blockPlace(null,
                                      ContainerHelpers.toBlockLocation(
                                          event.getBlock().getLocation()),
                                      event.getBlock().getType().toString(),
                                      null, null)
            || !coreListeners.blockPlace(null,
                                         ContainerHelpers.toBlockLocation(
                                             event.getToBlock().getLocation()),
                                         event.getBlock().getType().toString(),
                                         null, null)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack itemInHand = event.getPlayer().getItemInHand();
        if (!coreListeners.blockBreak(
                new LegacySpigotPlayerContainer(event.getPlayer()),
                ContainerHelpers.toBlockLocation(
                    event.getBlock().getLocation()),
                event.getBlock().getType().toString(),
                itemInHand == null ? null : itemInHand.getType().toString(),
                itemInHand == null || itemInHand.getItemMeta() == null
                    ? null
                    : itemInHand.getItemMeta().getDisplayName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onExplosion(EntityExplodeEvent event) {
        if (!configRepository.getPortalProtection())
            return;

        List<Block> blockList = event.blockList();
        for (int i = 0; i < blockList.size(); i++) {
            Block block = blockList.get(i);
            if (portalServices.inPortalRegionProtected(
                    ContainerHelpers.toBlockLocation(block.getLocation()))) {
                blockList.remove(i);
                i--;
            }
        }
    }
}
