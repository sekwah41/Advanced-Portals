package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;
import java.util.*;
import java.util.stream.Collectors;

public class ListPortalGroupSubCommand implements SubCommand {
    @Inject
    DestinationServices destinationServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        Map<String, List<String>> groupMap = new HashMap<>();
        destinationServices.getDestinations().forEach(desti -> {
            String[] group = desti.getArgValues("group");
            String groupName = (group != null && group.length > 0) ? group[0] : "(none)";
            groupMap.computeIfAbsent(groupName, k -> new ArrayList<>()).add(desti.getName());
        });
        if (args.length > 1 && groupMap.containsKey(args[1])) {
            List<String> destis = groupMap.get(args[1]);
            String groupHeader = Lang.translate("command.destination.group.list") + " " + args[1] + ": ";
            String destiList = String.join(", ", destis);
            sender.sendMessage(Lang.getPositivePrefix() + groupHeader + destiList);
        } else {
            StringBuilder sb = new StringBuilder(Lang.getPositivePrefix() + Lang.translate("command.portal.group.header") + "\n");
            groupMap.forEach((group, destis) -> {
                sb.append("- ").append(group).append(": ")
                  .append(String.join(", ", destis)).append("\n");
            });
            sender.sendMessage(sb.toString());
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.LIST_DESTI.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        Map<String, List<String>> groupMap = new HashMap<>();
        destinationServices.getDestinations().forEach(desti -> {
            String[] group = desti.getArgValues("group");
            String groupName = (group != null && group.length > 0) ? group[0] : "(none)";
            groupMap.put(groupName, null);
        });
        if (args.length == 2) {
            return groupMap.keySet().stream()
                .filter(g -> g != null && g.startsWith(args[1]))
                .sorted()
                .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.destination.group.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.destination.group.detailedhelp");
    }
}
