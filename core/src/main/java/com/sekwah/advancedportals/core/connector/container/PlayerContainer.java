package com.sekwah.advancedportals.core.connector.container;

import com.sekwah.advancedportals.core.entities.PlayerLocation;
import com.sekwah.advancedportals.core.entities.PortalLocation;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Just a temporary container for whenever advanced portals needs to get data from a player
 */
public interface PlayerContainer {

    UUID getUUID();

    public void sendMessage(String message);

    boolean isOp();

    PlayerLocation getLoc();

    double getEyeHeight();

    void teleport(PlayerLocation location);

    boolean hasPermission(String permission);

    WorldContainer getWorld();

    /**
     * @param blockPos
     * @param material
     */
    void sendFakeBlock(PortalLocation blockPos, String material);

    /**
     * Only 1.12 and below supported
     * @param blockPos
     * @param material
     * @param data
     */
    void sendFakeBlockWithData(PortalLocation blockPos, String material, byte data);

    void giveWool(String dyeColor, String itemName, String... itemDescription);

    public void giveItem(String material, String itemName, String... itemDescription) {
        ItemStack regionselector = new ItemStack(Material.getMaterial(material));
        ItemMeta selectorname = regionselector.getItemMeta();
        selectorname.setDisplayName(itemName);
        selectorname.setLore(Arrays.asList(itemDescription));
        regionselector.setItemMeta(selectorname);
        this.player.getInventory().addItem(regionselector);
    }
}
