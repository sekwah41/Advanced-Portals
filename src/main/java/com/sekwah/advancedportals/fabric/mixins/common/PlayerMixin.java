package com.sekwah.advancedportals.fabric.mixins.common;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerMixin {

    @Inject(method="setPosition", at=@At("HEAD"))
    public void setPosition(double x, double y, double z, CallbackInfo info) {
        System.out.println("MOVE");
    }


}
