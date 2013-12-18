package com.sekwah.advancedportals.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
 
public final class WarpEvent extends Event implements Cancellable {
	
	/**
	 * Use listeners so you can add new triggers easier and also other plugins can listen for the event
	 *  and add their own triggers
	 */
	
	
    private static final HandlerList handlers = new HandlerList();
    private String message;
    private boolean cancelled;
 
    public WarpEvent(Player player, String portalName) {
    	// add code for the custom listener
    }
 
    public String getMessage() {
        return message;
    }
 
    public boolean isCancelled() {
        return cancelled;
    }
 
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}