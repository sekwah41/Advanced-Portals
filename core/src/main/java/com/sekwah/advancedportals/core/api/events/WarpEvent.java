package com.sekwah.advancedportals.bukkit.api.events;

import com.sekwah.advancedportals.bukkit.portals.AdvancedPortal;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Trigered whenever a player activates the warp after the tags are handled.
 *
 *
 * TODO Need to make a custom event handler to be able to register against or something similar like a general events data
 */
public class WarpEvent /*extends Event implements Cancellable*/ {

    /*private static HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    private final Player player;

    @SuppressWarnings("unused")
    private final AdvancedPortal portalData;

    private boolean hasWarped = false;

    *//*public WarpEvent(Player player, AdvancedPortal portalData) {
        this.player = player;
        this.portalData = portalData;
    }*//*

    public static HandlerList getHandlerList() {
        return handlers;
    }

    *//**
     * Returns if the event has been cancelled
     *
     * @return cancelled
     *//*
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    *//*public AdvancedPortal getPortalData() {
        return portalData;
    }*//*

    *//**
     * This will return true if another plugin has warped the player(and set this to true)
     *
     * @return hasWarped
     *//*
    public boolean getHasWarped() {
        return hasWarped;
    }

    *//**
     * If the
     *
     * @param warped
     *//*
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
