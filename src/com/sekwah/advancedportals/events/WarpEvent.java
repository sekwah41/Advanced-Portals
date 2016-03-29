package com.sekwah.advancedportals.events;

import com.sekwah.advancedportals.portals.AdvancedPortal;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class WarpEvent extends Event implements Cancellable {

    /**
     * Use listeners so you can add new triggers easier and also other plugins can listen for the event
     * and add their own triggers
     */


    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    private Player player;

    @SuppressWarnings("unused")
    private AdvancedPortal portalData;

    private boolean hasWarped = false;

    public WarpEvent(Player player, AdvancedPortal portalData) {
        this.player = player;
        this.portalData = portalData;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Returns if the event has been cancelled
     *
     * @return cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public AdvancedPortal getPortalData() {
        return portalData;
    }

    /**
     * This will return true if another plugin has warped the player(and set this to true)
     *
     * @return hasWarped
     */
    public boolean getHasWarped() {
        return hasWarped;
    }

    /**
     * If the
     *
     * @param warped
     */
    @SuppressWarnings("unused")
    public void setHasWarped(boolean warped) {
        this.hasWarped = warped;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}