package com.sekwah.advancedportals.core.registry;

import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;

/**
 * Something that a tag can be executed on.
 */
public interface TagTarget {
    /**
     * Get the values for the arg
     *
     * @param argName
     * @return
     */
    String[] getArgValues(String argName);

    /**
     * Set the values for the arg
     *
     * @param argName
     * @param argValues
     */
    void setArgValues(CommandSenderContainer player, String argName, String[] argValues);

    /**
     * Remove the arg entirely from the target
     *
     * @param arg
     */
    void removeArg(CommandSenderContainer player, String arg);

    boolean hasArg(String name);
}
