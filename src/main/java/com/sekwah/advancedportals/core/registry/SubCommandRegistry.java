package com.sekwah.advancedportals.core.registry;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.util.InfoLogger;

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

    protected Map<String, SubCommand> subCommandMap = new HashMap<>();

    /**
     * List of subcommand names which should be in order alphabetically
     */
    protected ArrayList<String> subCommands = new ArrayList<>();

    private InfoLogger infoLogger = AdvancedPortalsCore.getInstance().getInfoLogger();

    /**
     * @param arg argument needed to activate
     * @param subCommand
     * @return if the subcommand is registered or not
     */
    public boolean registerSubCommand(String arg, SubCommand subCommand) {

        if (subCommand == null) {
            this.infoLogger.logWarning("The subcommand '" + arg + "' cannot be null.");
            return false;
        }

        if(this.subCommandMap.containsKey(arg)){
            this.infoLogger.logWarning("The subcommand '" + arg + "' already exists.");
            return false;
        }

        this.subCommandMap.put(arg.toLowerCase(), subCommand);

        this.subCommands.add(arg.toLowerCase());

        Collections.sort(this.subCommands);

        return true;
    }

    /**
     * @return a list of arguments of registered subcommands
     */
    public ArrayList<String> getSubCommands(){
        return this.subCommands;
    }

    /**
     * I may be wrong but for larger lists containsKey is faster with a hashmap than arraylist.
     *
     * Though im not sure at what size it becomes more efficient.
     * @param arg
     * @return if the argument is registered
     */
    public boolean isArgRegistered(String arg){
        return this.subCommandMap.containsKey(arg.toLowerCase());
    }

    /**
     * Gets the subcommand corresponding to the string argument
     * @param arg
     * @return the subcommand linked to the arg
     */
    public SubCommand getSubCommand(String arg){
        if(this.subCommandMap.containsKey(arg.toLowerCase())){
            return this.subCommandMap.get(arg.toLowerCase());
        }
        return null;
    }
}
