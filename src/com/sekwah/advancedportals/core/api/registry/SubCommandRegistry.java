package com.sekwah.advancedportals.core.api.registry;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Do not register to here. Register to the sprcific subcommand registry classes.
 * <p>
 * Designed to let addons add new command sections to access, edit or add new functonality.
 *
 * @author sekwah41
 */
public class SubCommandRegistry {

    private Map<String, SubCommand> subCommandMap = new HashMap<>();

    private static final SubCommandRegistry instance = new SubCommandRegistry();

    /**
     * List of subcommand names which should be in order alphabetically
     */
    private ArrayList<String> subCommands = new ArrayList<>();

    /**
     * @param arg argument needed to activate
     * @param subCommand
     * @return if the subcommand is registered or not
     */
    public static boolean registerSubCommand(String arg, SubCommand subCommand) {

        if (subCommand == null) {
            AdvancedPortalsCore.getInfoLogger().logWarning("The subcommand '" + arg + "' cannot be null.");
            return false;
        }

        if(!instance.subCommandMap.containsKey(arg)){
            AdvancedPortalsCore.getInfoLogger().logWarning("The subcommand '" + arg + "' already exists.");
            return false;
        }

        instance.subCommandMap.put(arg.toLowerCase(), subCommand);

        instance.subCommands.add(arg.toLowerCase());

        Collections.sort(instance.subCommands);

        return true;
    }

    /**
     * @return a list of arguments of registered subcommands
     */
    public static ArrayList<String> getSubCommands(){
        return instance.subCommands;
    }

    /**
     * I may be wrong but for larger lists containsKey is faster with a hashmap than arraylist.
     *
     * Though im not sure at what size it becomes more efficient.
     * @param arg
     * @return if the argument is registered
     */
    public static boolean isArgRegistered(String arg){
        return instance.subCommandMap.containsKey(arg.toLowerCase());
    }

    /**
     * Gets the subcommand corresponding to the string argument
     * @param arg
     * @return the subcommand linked to the arg
     */
    public static SubCommand getSubCommand(String arg){
        if(instance.subCommandMap.containsKey(arg.toLowerCase())){
            return instance.subCommandMap.get(arg.toLowerCase());
        }
        return null;
    }
}
