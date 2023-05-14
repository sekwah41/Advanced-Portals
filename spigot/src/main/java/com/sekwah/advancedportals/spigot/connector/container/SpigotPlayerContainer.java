package com.sekwah.advancedportals.spigot.connector.container;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

/**
 * Just a temporary container for whenever advanced portals needs to get data from a player
 */
public class SpigotPlayerContainer implements PlayerContainer {

    @Inject
    private AdvancedPortalsCore portalsCore;

    private final Player player;

    public SpigotPlayerContainer(Player player) {
        this.player = player;
    }

    public UUID getUUID() {
        return player.getUniqueId();
    }
    
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public boolean isOp() {
        return this.player.isOp();
    }

    public PlayerLocation getLoc() {
        Location loc = this.player.getLocation();
        return new PlayerLocation(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ());
    }

    public double getEyeHeight() {
        return 0;
    }

    public void teleport(PlayerLocation location) {
        this.player.teleport(new Location(Bukkit.getWorld(location.getWorldName()), location.getPosX(), location.getPosY(), location.getPosZ()));
    }

    public boolean hasPermission(String permission) {
        return this.player.hasPermission(permission);
    }

    public WorldContainer getWorld() {
        return new SpigotWorldContainer(this.player.getWorld());
    }

    /**
     * @param blockPos
     * @param material
     */
    public void sendFakeBlock(BlockLocation blockPos, String material) {

    }

    /**
     * Only 1.12 and below supported
     * @param blockPos
     * @param material
     * @param data
     */
    public void sendFakeBlockWithData(BlockLocation blockPos, String material, byte data) {

    }

    public void giveItem(String material, String itemName, String... itemDescription) {
        ItemStack regionselector = new ItemStack(Material.getMaterial(material));
        ItemMeta selectorname = regionselector.getItemMeta();
        selectorname.setDisplayName(itemName);
        selectorname.setLore(Arrays.asList(itemDescription));
        regionselector.setItemMeta(selectorname);
        this.player.getInventory().addItem(regionselector);
    }
}
