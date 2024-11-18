package com.sekwah.advancedportals.spigot.connector.container;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.connector.containers.GameMode;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.spigot.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.spigot.reflection.MinecraftCustomPayload;
import java.util.Arrays;
import java.util.UUID;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Just a temporary container for whenever advanced portals needs to get data
 * from a player
 */
public class SpigotPlayerContainer
    extends SpigotEntityContainer implements PlayerContainer {
    @Inject
    private AdvancedPortalsCore portalsCore;

    private final Player player;

    public SpigotPlayerContainer(Player player) {
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
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                    TextComponent.fromLegacyText(message));
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

    /**
     * @param blockPos
     * @param material
     */
    @Override
    public void sendFakeBlock(BlockLocation blockPos, String material) {
    }

    /**
     * Only 1.12 and below supported
     *
     * @param blockPos
     * @param material
     * @param data
     */
    @Override
    public void sendFakeBlockWithData(BlockLocation blockPos, String material,
                                      byte data) {
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
        if (channel.startsWith("minecraft:")) {
            return MinecraftCustomPayload.sendCustomPayload(player, channel,
                                                            bytes);
        } else {
            player.sendPluginMessage(AdvancedPortalsPlugin.getInstance(),
                                     channel, bytes);
        }
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
        return new SpigotServerContainer(this.player.getServer());
    }
}
