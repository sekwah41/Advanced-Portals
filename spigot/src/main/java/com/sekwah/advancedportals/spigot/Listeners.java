package com.sekwah.advancedportals.spigot;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.CoreListeners;
import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.EndGateway;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.world.ChunkLoadEvent;

/**
 * Some of these will be passed to the core listener to handle the events,
 * others it's easier to just check directly.
 */
public class Listeners implements Listener {

    private CoreListeners coreListeners = AdvancedPortalsCore.getInstance().getCoreListeners();

    @Inject
    private PortalServices portalServices;

    @Inject
    private ConfigRepository configRepository;

    // Entity and portal events
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        coreListeners.playerJoin(new SpigotPlayerContainer(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        coreListeners.playerLeave(new SpigotPlayerContainer(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.isCancelled()) {
            Location blockloc = event.getBlock().getLocation();
            this.coreListeners.blockPlace(new SpigotPlayerContainer(event.getPlayer()),
                    new PortalLocation(blockloc.getWorld().getName(), blockloc.getBlockX(), blockloc.getBlockY(), blockloc.getBlockZ()), event.getBlockPlaced().getType().toString(),
                    event.getItemInHand().getType().toString(), event.getItemInHand().getItemMeta().getDisplayName());
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
        coreListeners.worldChange(new SpigotPlayerContainer(event.getPlayer()));
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        if (!event.isCancelled()
            && (event.getAction() == Action.LEFT_CLICK_BLOCK
                || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            && event.getItem() != null) {
            Location blockloc = event.getClickedBlock().getLocation();
            boolean allowEvent = this.coreListeners.playerInteractWithBlock(new SpigotPlayerContainer(event.getPlayer()),
                    event.getClickedBlock().getType().toString(),
                    event.getMaterial().toString(),
                    event.getItem().getItemMeta().getDisplayName(),
                    new PortalLocation(blockloc.getWorld().getName(), blockloc.getBlockX(), blockloc.getBlockY(), blockloc.getBlockZ()),
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
        var itemInHand = event.getPlayer().getItemInHand();
        if (!coreListeners.blockBreak(
                new SpigotPlayerContainer(event.getPlayer()),
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


    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if(!configRepository.getDisableGatewayBeam()) {
            return;
        }
        SpigotWorldContainer world = new SpigotWorldContainer(event.getWorld());
        BlockState[] tileEntities = event.getChunk().getTileEntities();
        for(BlockState block : tileEntities) {
            if(block.getType() == Material.END_GATEWAY) {
                var loc = block.getLocation();
                if(portalServices.inPortalRegion(new BlockLocation(loc.getWorld().getName(),
                        loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), 2)) {
                    EndGateway tileState = (EndGateway) block;
                    tileState.setAge(Long.MIN_VALUE);
                    tileState.update();
                }
            }

        }
    }
}
