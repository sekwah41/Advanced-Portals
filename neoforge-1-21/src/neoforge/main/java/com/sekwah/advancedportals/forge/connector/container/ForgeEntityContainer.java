package com.sekwah.advancedportals.forge.connector.container;

import com.sekwah.advancedportals.core.connector.containers.EntityContainer;
import com.sekwah.advancedportals.core.connector.containers.WorldContainer;
import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import com.sekwah.advancedportals.core.serializeddata.PlayerLocation;
import com.sekwah.advancedportals.core.serializeddata.Vector;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class ForgeEntityContainer implements EntityContainer {

    private final Entity entity;

    public ForgeEntityContainer(Entity entity) {
        this.entity = entity;
    }

    @Override
    public PlayerLocation getLoc() {
        return new PlayerLocation(this.entity.level().dimension().location().toString(),
                this.entity.getX(), this.entity.getY(), this.entity.getZ(),
                this.entity.getYRot(), this.entity.getXRot());
    }

    @Override
    public double getHeight() {
        return this.entity.getEyeHeight();
    }

    @Override
    public BlockLocation getBlockLoc() {
        return null;
    }

    @Override
    public boolean teleport(PlayerLocation location) {
        return false;
    }

    @Override
    public WorldContainer getWorld() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getWorldName() {
        return "";
    }

    @Override
    public void setVelocity(Vector vector) {

    }
}
