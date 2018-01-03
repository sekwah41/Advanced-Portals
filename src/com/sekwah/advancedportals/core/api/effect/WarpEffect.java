package com.sekwah.advancedportals.core.api.effect;

import com.sekwah.advancedportals.core.api.portal.Portal;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Effects to be registered to the list.
 * <p>
 * Fires once at each end.
 * <p>
 * Can be a Visual effect or a Sound. Just register to the correct one
 *
 * @author sekwah41
 */
public interface WarpEffect {

    void onWarp(Player player, Location loc, Action action, Portal portal);

    Type getType();

    enum Action {
        ENTER,
        EXIT;
    }

    enum Type {
        SOUND,
        VISUAL;
    }

}
