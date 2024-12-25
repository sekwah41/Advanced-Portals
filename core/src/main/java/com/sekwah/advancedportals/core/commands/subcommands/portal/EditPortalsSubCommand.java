package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.commands.subcommands.portal.edit.AddTagPortalEditSubCommand;
import com.sekwah.advancedportals.core.commands.subcommands.portal.edit.RemoveTagPortalEditSubCommand;
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
        if(args.length < 2) {
            sender.sendMessage(Lang.getNegativePrefix()
                    + Lang.translate("command.portal.edit.error.noportalname"));
            return;
        }
        var portalName = args[1];

        var portal = portalServices.getPortal(portalName);

        if(portal == null) {
            sender.sendMessage(Lang.getNegativePrefix()
                    + Lang.translateInsertVariables("command.portal.edit.error.notfound", portalName));
            return;
        }

        if(args.length < 3) {
            sender.sendMessage(Lang.getNegativePrefix()
                    + Lang.translate("command.portal.edit.error.nosubcommand"));
            return;
        }

        var argSubCommand = args[2];

        var subCommand = subCommandRegistry.getSubCommand(argSubCommand);

        if(subCommand == null) {
            sender.sendMessage(Lang.getNegativePrefix()
                    + Lang.translateInsertVariables("command.portal.edit.error.unrecognisedsubcommand", argSubCommand));
            return;
        }

        subCommand.onCommand(sender, args);
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.EDIT_PORTAL.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        if(args.length == 2) {
            return portalServices.getPortalNames();
        }
        if(args.length == 3) {
            return this.subCommandRegistry.getSubCommands();
        }
        var argSubCommand = args[2];

        var subCommand = subCommandRegistry.getSubCommand(argSubCommand);

        if(subCommand == null) {
            return Collections.emptyList();
        }

        return subCommand.onTabComplete(sender, args);
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.portal.edit.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.edit.detailedhelp");
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
        registerSubCommand("addtags", new AddTagPortalEditSubCommand());
        registerSubCommand("removetag", new RemoveTagPortalEditSubCommand());
    }
}
