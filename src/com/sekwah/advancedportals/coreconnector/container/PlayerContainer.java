package com.sekwah.advancedportals.coreconnector.container;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.sekwah.advancedportals.core.data.PlayerLocation;
import com.sekwah.advancedportals.core.data.PortalLocation;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

/**
 * Just a temporary container for whenever advanced portals needs to get data from a player
 */
public class PlayerContainer {

    private final Player player;

    public PlayerContainer(Player player) {
        this.player = player;
    }

    public UUID getUUID() {
        return player.getUniqueId();
    }
    
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public boolean isOp() {
        return player.isOp();
    }

    public PlayerLocation getLoc() {
        return null;
    }

    public double getEyeHeight() {
        return 0;
    }

    public void teleport(PlayerLocation location) {}

    public boolean hasPermission(String permission) {
        return false;
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

    public void giveItem(String material, String itemName, String... itemDescription) {
        ItemStack regionselector = new ItemStack(Material.getMaterial(material));
        ItemMeta selectorname = regionselector.getItemMeta();
        selectorname.setDisplayName(itemName);
        selectorname.setLore(Arrays.asList(itemDescription));
        regionselector.setItemMeta(selectorname);
        this.player.getInventory().addItem(regionselector);
    }
}
