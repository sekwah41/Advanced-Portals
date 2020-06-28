package com.sekwah.advancedportals.spigot.coreconnector.container;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.core.data.PortalLocation;
import com.sekwah.advancedportals.core.connector.container.PlayerContainer;
import com.sekwah.advancedportals.core.connector.container.WorldContainer;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

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
    public void sendFakeBlock(PortalLocation blockPos, String material) {

    }

    /**
     * Only 1.12 and below supported
     * @param blockPos
     * @param material
     * @param data
     */
    public void sendFakeBlockWithData(PortalLocation blockPos, String material, byte data) {

    }

    public void giveWool(String dyeColor, String itemName, String... itemDescription) {
        ItemStack regionselector = new Wool(DyeColor.valueOf(dyeColor)).toItemStack(1);
        ItemMeta selectorname = regionselector.getItemMeta();
        selectorname.setDisplayName(itemName);
        selectorname.setLore(Arrays.asList(itemDescription));
        regionselector.setItemMeta(selectorname);
        this.player.getInventory().addItem(regionselector);
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
