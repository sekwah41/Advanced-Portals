package com.sekwah.advancedportals.compat;

import fr.neatmonster.nocheatplus.checks.moving.MovingData;
import org.bukkit.entity.Player;

public class NoCheatPlus {

    public static void teleport(Player player) {
        MovingData.getData(player).setTeleported(player.getLocation());
       // MovingData.getData(player).prepareSetBack(player.getLocation());
        //MovingData.getData(player).onSetBack(player.getLocation());
        System.out.println("Warp NoCheat");
    }
}
