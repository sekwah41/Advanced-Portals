package com.sekwah.advancedportals.forge.connector.container;

import com.sekwah.advancedportals.core.connector.containers.GameMode;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.connector.containers.ServerContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.Vector;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ForgePlayerContainer extends ForgeEntityContainer implements PlayerContainer {
    private final ServerPlayer player;

    public ForgePlayerContainer(ServerPlayer player) {
        super(player);
        this.player = player;
    }

    @Override
    public UUID getUUID() {
        return this.player.getUUID();
    }

    @Override
    public void sendMessage(String message) {
        this.player.sendSystemMessage(Component.literal(message));
    }

    @Override
    public void sendActionBar(String message) {
        this.player.sendSystemMessage(Component.literal(message), true);
    }

    @Override
    public void giveItem(String material, String itemName, String... itemDescription) {
        Item item = BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", material.toLowerCase()));
        ItemStack itemStack = new ItemStack(item);
        itemStack.set(DataComponents.CUSTOM_NAME, Component.literal(itemName));
        List<Component> componentList = Arrays.stream(itemDescription).map(desc -> (Component) Component.literal(desc)).toList();
        itemStack.set(DataComponents.LORE, new ItemLore(componentList));

        this.player.getInventory().add(itemStack);
    }

    @Override
    public boolean sendPacket(String channel, byte[] bytes) {
        return false;
    }

    @Override
    public void playSound(String sound, float volume, float pitch) {

    }

    @Override
    public ServerContainer getServer() {
        return null;
    }

    @Override
    public GameMode getGameMode() {
        try {
            return GameMode.valueOf(this.player.gameMode.getGameModeForPlayer().name());
        } catch (IllegalArgumentException e) {
            return GameMode.SURVIVAL;
        }
    }

    @Override
    public void spawnColoredDust(Vector pos, double xSpread, double ySpread, double zSpread, int count, Color color) {

    }

    @Override
    public PlayerLocation getLoc() {
        return super.getLoc();
    }

    @Override
    public void setVelocity(Vector vector) {
        Vec3 velocity = new Vec3(vector.getX(), vector.getY(), vector.getZ());
        this.player.connection.send(new ClientboundSetEntityMotionPacket(player.getId(), velocity));
        super.setVelocity(vector);
    }

    @Override
    public boolean isOp() {
        return this.player.hasPermissions(4);
    }

    @Override
    public boolean hasPermission(String permission) {
        // There is no permission system supported atm for forge.
        return false;
    }
}
