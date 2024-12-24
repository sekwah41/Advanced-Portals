package com.sekwah.advancedportals.core.registry;

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
    void setArgValues(String argName, String[] argValues);

    /**
     * Add a new arg to the tag
     *
     * @param argName
     * @param argValues
     */
    void addArg(String argName, String argValues);

    /**
     * Remove the arg entirely from the target
     *
     * @param arg
     */
    void removeArg(String arg);

    boolean hasArg(String name);
}
