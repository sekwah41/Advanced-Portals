package com.sekwah.advancedportals.core.registry;

import com.google.common.collect.ImmutableList;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.connector.container.CommandSenderContainer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class Registrar<T extends CommandHandler> implements CommandExecutor, TabCompleter {
     //Parent Command, Sub Command, Object
     private boolean allowPermissionInheritance;
     private Map<Cmd, T> commandMap = new HashMap<>();

     protected Registrar(boolean allowPermissionInheritance, Map<Cmd, T> commandMap) {
          this.commandMap = commandMap;
     }

     public boolean isAllowPermissionInheritance() {
          return allowPermissionInheritance;
     }

     public Map<Cmd, T> getCommandMap() {
          return commandMap;
     }

     @Override
     public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
         Stream<Map.Entry<Cmd, T>> topLevelCommands = commandMap.entrySet().stream()
                  .filter(c->c.getKey().name().equals(args[0]) || c.getKey().parentCommand().equals(args[0]));

          Optional<Map.Entry<Cmd, T>> results = topLevelCommands.filter(c->c.getKey().name().equals(args[1])).findFirst();
          String[] commands = null;
          Map.Entry<Cmd, T> key = null;


          if (results.isPresent()) {
               args[0] = null;
               args[1] = null;
              commands = new String[]{args[0], args[1]};

              key = results.get();
          } else {
              commands = new String[]{args[0]};
              key = topLevelCommands
                       .filter(c->c.getKey().name().equals(args[0]) && c.getKey().parentCommand().equals(""))
                       .findFirst().get();
          }

          if (args[0].length() >= key.getKey().minArgs()) {

          } else {
              commands = new String[]{args[0], args[1]};
              //TODO ????
              key.getValue().onCommandFailure(commands, (CommandSenderContainer) sender, new CommandException(ErrorCode.INSUFFICIENT_ARGUMENTS, ""),
              ImmutableList.copyOf(Arrays.asList(args)));
          }
          return false;
     }

     @Override
     public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
          return null;
     }
}
