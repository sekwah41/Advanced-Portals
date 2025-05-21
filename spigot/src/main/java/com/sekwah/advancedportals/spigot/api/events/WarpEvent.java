package com.sekwah.advancedportals.spigot.api.events;

import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.services.EventDispatcher;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Fired at the end of each of the stages of a portal trigger. Though returning false will cancel the portal trigger or fail the activation,
 * returning false on post activation will do nothing.
 */
public class WarpEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;
    private final Player player;
    private final AdvancedPortal portal;
    private final ActivationData activationData;
    private final EventDispatcher.WarpStage stage;

    public WarpEvent(Player player, AdvancedPortal portal,
                     ActivationData activationData, EventDispatcher.WarpStage stage) {
        this.player = player;
        this.portal = portal;
        this.activationData = activationData;
        this.stage = stage;
    }

    public Player getPlayer() {
        return player;
    }

    public AdvancedPortal getPortal() {
        return portal;
    }

    public ActivationData getActivationData() {
        return activationData;
    }

    public EventDispatcher.WarpStage getStage() {
        return stage;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
