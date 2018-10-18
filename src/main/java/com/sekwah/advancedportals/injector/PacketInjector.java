package com.sekwah.advancedportals.injector;

import com.sekwah.advancedportals.AdvancedPortalsPlugin;

public class PacketInjector {

    public PacketInjector(AdvancedPortalsPlugin plugin, String bukkitImpl) {
            String craftBukkitPackage = "org.bukkit.craftbukkit." + bukkitImpl + ".";
            String minecraftPackage = "net.minecraft.server." + bukkitImpl + ".";

            
    }

}
