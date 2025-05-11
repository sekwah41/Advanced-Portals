package com.sekwah.advancedportals.forge.mixin.common;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract void playerTouch(Player p_20081_);

    @Shadow public double xOld;
    @Shadow public double yOld;
    @Shadow public double zOld;

    /*@Inject(method = "teleportSetPosition", at = @At("HEAD"))
    public void teleportSetPosition(PositionMoveRotation p_376669_, Set<Relative> p_376772_, CallbackInfo ci) {
        if((Object) this instanceof Player player) {
            System.out.println("teleportSetPosition " + player.getName());
        }
    }

    @Inject(method = "moveTo(DDDFF)V", at = @At("HEAD"))
    public void moveTo(double p_20108_, double p_20109_, double p_20110_, float p_20111_, float p_20112_, CallbackInfo ci) {
        if((Object) this instanceof Player player) {
            System.out.println("moveTo " + player.getName());
        }
    }*/

    @Inject(method = "setPosRaw", at= @At("HEAD"))
    public final void setPosRaw(double p_20344_, double p_20345_, double p_20346_, CallbackInfo ci) {
        if((Object) this instanceof Player player) {
            if(this.xOld != player.getX() || this.yOld != player.getY() || this.zOld != player.getZ()) {
                System.out.println("changed setPosRaw " + player.getX() + " " + player.getY() + " " + player.getZ());
            }
        }
    }
}
