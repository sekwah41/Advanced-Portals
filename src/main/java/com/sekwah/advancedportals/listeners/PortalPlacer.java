package com.sekwah.advancedportals.listeners;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;
import com.sekwah.advancedportals.portals.Portal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class PortalPlacer implements Listener {

    @SuppressWarnings("unused")
    private final AdvancedPortalsPlugin plugin;
    private final boolean DISABLE_GATEWAY_BEAM;

    public PortalPlacer(AdvancedPortalsPlugin plugin) {
        this.plugin = plugin;

        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        boolean portalPlace = config.getConfig().getBoolean("CanBuildPortalBlock");

        this.DISABLE_GATEWAY_BEAM = config.getConfig().getBoolean("DisableGatewayBeam", true);


        if (portalPlace) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getPlayer().hasPermission("advancedportals.build") && event.getItemInHand() != null &&
                event.getItemInHand().hasItemMeta()){
            String name = event.getItemInHand().getItemMeta().getDisplayName();

            if(name == null) return;

            if (name.equals("\u00A75Portal Block Placer")){
                event.getBlockPlaced().setType(Material.NETHER_PORTAL);
            }
            else if (name.equals("\u00A78End Portal Block Placer")){
                event.getBlockPlaced().setType(Material.END_PORTAL);
            }
            else if (name.equals("\u00A78Gateway Block Placer")){
                event.getBlockPlaced().setType(Material.END_GATEWAY);
                if(this.DISABLE_GATEWAY_BEAM) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        this.plugin.compat.setGatewayAgeHigh(event.getBlock());
                    }, 1);
                }

            }
        }

    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if(!this.DISABLE_GATEWAY_BEAM) {
            return;
        }
        BlockState[] tileEntities = event.getChunk().getTileEntities();
        for(BlockState block : tileEntities) {
            this.plugin.compat.setGatewayAgeHigh(block.getBlock());
        }
        //event.getHandlers();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        if (material == Material.NETHER_PORTAL && Portal.inPortalRegion(block.getLocation(), Portal.getPortalProtectionRadius()))
            event.setCancelled(true);
    }
}
