package com.sekwah.advancedportals.core.api.effect;

import com.sekwah.advancedportals.core.api.portal.Portal;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author sekwah41
 */
public class TestEffect implements WarpEffect {

    @Override
    public void onWarp(Player player, Location loc, Action action, Portal portal) {

    }

    @Override
    public Type getType() {
        return null;
    }
}
