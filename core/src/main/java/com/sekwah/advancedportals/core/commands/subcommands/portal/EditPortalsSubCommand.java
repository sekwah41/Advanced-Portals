package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.commands.subcommands.portal.edit.AddTagPortalEditSubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.registry.SubCommandRegistry;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.Lang;
import java.util.Collections;

import java.util.List;

public class EditPortalsSubCommand implements SubCommand, SubCommand.SubCommandOnInit {
    @Inject
    PortalServices portalServices;

    @Inject
    AdvancedPortalsCore pluginCore;

    private final SubCommandRegistry subCommandRegistry = new SubCommandRegistry();

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {

    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.EDIT_PORTAL.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        System.out.println(args.length);
        if(args.length == 2) {
            return portalServices.getPortalNames();
        }
        return Collections.emptyList();
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.portal.addtags.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.addtags.detailedhelp");
    }

    private boolean registerSubCommand(String arg, SubCommand subCommand,
                                      String... aliasArgs) {
        pluginCore.getModule().getInjector().injectMembers(subCommand);
        boolean hasRegistered = false;
        for (String additionalArg : aliasArgs) {
            hasRegistered = this.subCommandRegistry.registerSubCommand(
                    additionalArg, subCommand)
                    || hasRegistered;
        }
        boolean result =
                this.subCommandRegistry.registerSubCommand(arg, subCommand)
                        || hasRegistered;
        if (subCommand instanceof SubCommand.SubCommandOnInit init) {
            init.registered();
        }
        return result;
    }

    @Override
    public void registered() {
        this.subCommandRegistry.registerSubCommand("addtags", new AddTagPortalEditSubCommand());
    }
}
