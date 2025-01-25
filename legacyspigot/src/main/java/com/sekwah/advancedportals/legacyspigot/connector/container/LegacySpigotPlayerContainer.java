package com.sekwah.advancedportals.legacyspigot.connector.container;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.connector.containers.GameMode;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.Vector;
import com.sekwah.advancedportals.shadowed.inject.Inject;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import com.sekwah.advancedportals.legacyspigot.AdvancedPortalsPlugin;
import java.util.Arrays;
import java.util.UUID;

/**
 * Just a temporary container for whenever advanced portals needs to get data
 * from a player
 */
public class LegacySpigotPlayerContainer
    extends LegacySpigotEntityContainer implements PlayerContainer {
    @Inject
    private AdvancedPortalsCore portalsCore;

    private final Player player;

    public LegacySpigotPlayerContainer(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendActionBar(String message) {
        try {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        } catch (NoSuchMethodError e) {
            player.sendMessage(message);
        }
    }

    @Override
    public boolean isOp() {
        return this.player.isOp();
    }

    @Override
    public boolean teleport(PlayerLocation location) {
        return this.player.teleport(new Location(
            Bukkit.getWorld(location.getWorldName()), location.getPosX(),
            location.getPosY(), location.getPosZ(), location.getYaw(),
            location.getPitch()));
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.player.hasPermission(permission);
    }

    @Override
    public void giveItem(String material, String itemName,
                         String... itemDescription) {
        ItemStack regionselector =
            new ItemStack(Material.getMaterial(material));
        ItemMeta selectorname = regionselector.getItemMeta();
        selectorname.setDisplayName(itemName);
        selectorname.setLore(Arrays.asList(itemDescription));
        regionselector.setItemMeta(selectorname);
        this.player.getInventory().addItem(regionselector);
    }

    @Override
    public boolean sendPacket(String channel, byte[] bytes) {
        player.sendPluginMessage(AdvancedPortalsPlugin.getInstance(), channel,
                                 bytes);
        return true;
    }

    @Override
    public GameMode getGameMode() {
        try {
            return GameMode.valueOf(this.player.getGameMode().name());
        } catch (IllegalArgumentException e) {
            return GameMode.SURVIVAL;
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void playSound(String sound, float volume, float pitch) {
        this.player.playSound(this.player.getLocation(), sound, volume, pitch);
    }

    @Override
    public ServerContainer getServer() {
        return new LegacySpigotServerContainer(this.player.getServer());
    }

    @Override
    public void spawnColoredDust(Vector position, double xSpread,
                                 double ySpread, double zSpread, int count,
                                 Color color) {
        // Check distance to player
        if (this.player.getLocation().distance(
                new Location(this.player.getWorld(), position.getX(),
                             position.getY(), position.getZ()))
            > 180) {
            return;
        }

        // If red is 0 it seems to treat it as fully red too
        if(color.getRed() == 0) {
            color = new Color(1, color.getGreen(), color.getBlue());
        }


        this.player.spigot().playEffect(new Location(player.getWorld(), position.getX(), position.getY(), position.getZ()), Effect.COLOURED_DUST, 0,  0,color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1, 0, 64);
    }
}

