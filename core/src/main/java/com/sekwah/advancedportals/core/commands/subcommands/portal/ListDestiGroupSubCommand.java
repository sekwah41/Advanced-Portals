package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;
import java.util.*;
import java.util.stream.Collectors;

public class ListDestiGroupSubCommand implements SubCommand {
    @Inject
    PortalServices portalServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        Map<String, List<String>> groupMap = new HashMap<>();
        portalServices.getPortals().forEach(portal -> {
            String[] group = portal.getArgValues("group");
            String groupName = (group != null && group.length > 0) ? group[0] : "(none)";
            groupMap.computeIfAbsent(groupName, k -> new ArrayList<>()).add(portal.getName());
        });
        if (args.length > 1 && groupMap.containsKey(args[1])) {
            // List portals in the specified group
            List<String> portals = groupMap.get(args[1]);
            String groupHeader = Lang.translate("command.portal.group.list") + " " + args[1] + ": ";
            String portalList = String.join(", ", portals);
            sender.sendMessage(Lang.getPositivePrefix() + groupHeader + portalList);
        } else {
            // List all groups and their portals
            StringBuilder sb = new StringBuilder(Lang.getPositivePrefix() + Lang.translate("command.portal.group.header") + "\n");
            groupMap.forEach((group, portals) -> {
                sb.append("- ").append(group).append(": ")
                  .append(String.join(", ", portals)).append("\n");
            });
            sender.sendMessage(sb.toString());
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.LIST_PORTAL.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        Map<String, List<String>> groupMap = new HashMap<>();
        portalServices.getPortals().forEach(portal -> {
            String[] group = portal.getArgValues("group");
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
        return Lang.translate("command.portal.group.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.group.detailedhelp");
    }
}
