package com.sekwah.advancedportals.core.tags;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.ProxyMessages;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.network.ProxyCommandPacket;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import javax.annotation.Nullable;

public class CommandTag implements Tag.Activation, Tag.Split, Tag.Creation {
    @Inject
    ConfigRepository configRepository;

    public static String TAG_NAME = "command";

    private final TagType[] tagTypes = new TagType[] {TagType.PORTAL};

    @Override
    public TagType[] getTagTypes() {
        return tagTypes;
    }

    @Override
    public String getName() {
        return TAG_NAME;
    }

    @Nullable
    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public String description() {
        return Lang.translate("tag.command.description");
    }

    @Nullable
    @Override
    public String splitString() {
        return ",";
    }

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player,
                                ActivationData activeData, String[] argData) {
        var commandPortals = configRepository.getCommandPortals();

        if (!commandPortals.enabled) {
            player.sendMessage(Lang.getNegativePrefix()
                               + Lang.translate("tag.command.disabled"));
            return false;
        }

        for (String command : argData) {
            char executionCommand = command.charAt(0);
            switch (executionCommand) {
                case '!':
                    if (!commandPortals.op) {
                        player.sendMessage(
                            Lang.getNegativePrefix()
                            + Lang.translate("tag.command.op.disabled"));
                        return false;
                    }
                    break;
                case '#':
                    if (!commandPortals.console) {
                        player.sendMessage(
                            Lang.getNegativePrefix()
                            + Lang.translate("tag.command.console.disabled"));
                        return false;
                    }
                    break;
                case '^':
                    if (!commandPortals.permsWildcard) {
                        player.sendMessage(
                            Lang.getNegativePrefix()
                            + Lang.translate(
                                "tag.command.permswildcard.disabled"));
                        return false;
                    }
                    break;
                case '%':
                    if (!commandPortals.proxy
                        || !configRepository.getEnableProxySupport()) {
                        player.sendMessage(
                            Lang.getNegativePrefix()
                            + Lang.translate("tag.command.proxy.disabled"));
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }

        return true;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player,
                              ActivationData activationData, String[] argData) {
        for (String command : argData) {
            char executionCommand = command.charAt(0);
            String formattedCommand =
                command.replaceAll("@player", player.getName());

            switch (executionCommand) {
                case '!':
                    player.getServer().dispatchCommand(
                        player.getUUID(), formattedCommand.substring(1),
                        CommandLevel.OP);
                    break;
                case '#':
                    player.getServer().dispatchCommand(
                        player.getUUID(), formattedCommand.substring(1),
                        CommandLevel.CONSOLE);
                    break;
                case '^':
                    player.getServer().dispatchCommand(
                        player.getUUID(), formattedCommand.substring(1),
                        CommandLevel.PERMISSION_WILDCARD);
                    break;
                case '%':
                    var packet =
                        new ProxyCommandPacket(formattedCommand.substring(1));
                    player.sendPacket(ProxyMessages.CHANNEL_NAME,
                                      packet.encode());
                    break;
                default:
                    player.getServer().dispatchCommand(player.getUUID(),
                                                       formattedCommand,
                                                       CommandLevel.PLAYER);
                    break;
            }
        }
    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player,
                             ActivationData activationData, String[] argData) {
        // Will trigger in the post activation stage to make sure the command
        // triggers after any teleportation
        activationData.setWarpStatus(ActivationData.WarpedStatus.ACTIVATED);
        return true;
    }

    @Override
    public boolean created(TagTarget target, CommandSenderContainer sender,
                           String[] argData) {
        if (argData != null) {
            var commandPortals = configRepository.getCommandPortals();
            if (!commandPortals.enabled) {
                sender.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translate("tag.command.disabled"));
                return false;
            }
            for (String command : argData) {
                char executionCommand = command.charAt(0);
                return switch (executionCommand) {
                    case '!' -> {
                        if (!commandPortals.op) {
                            sender.sendMessage(Lang.getNegativePrefix()
                                    + Lang.translate("tag.command.op.disabled"));
                            yield false;
                        }
                        if (!Permissions.CREATE_COMMAND_OP.hasPermission(sender)) {
                            sender.sendMessage(Lang.getNegativePrefix()
                                    + Lang.translateInsertVariables("tag.command.nopermission", "OP"));
                            yield false;
                        }
                        yield true;
                    }
                    case '#' -> {
                        if (!commandPortals.console) {
                            sender.sendMessage(Lang.getNegativePrefix()
                                    + Lang.translate("tag.command.console.disabled"));
                            yield false;
                        }
                        if (!Permissions.CREATE_COMMAND_CONSOLE.hasPermission(sender)) {
                            sender.sendMessage(Lang.getNegativePrefix()
                                    + Lang.translateInsertVariables("tag.command.nopermission","Console"));
                            yield false;
                        }
                        yield true;
                    }
                    case '^' -> {
                        if (!commandPortals.permsWildcard) {
                            sender.sendMessage(Lang.getNegativePrefix()
                                    + Lang.translate("tag.command.permswildcard.disabled"));
                            yield false;
                        }
                        if (!Permissions.CREATE_COMMAND_PERMS.hasPermission(sender)) {
                            sender.sendMessage(Lang.getNegativePrefix()
                                    + Lang.translateInsertVariables("tag.command.nopermission", "*"));
                            yield false;
                        }
                        yield true;
                    }
                    default -> true;
                };
            }
        }
        return false;
    }

    @Override
    public void destroyed(TagTarget target, CommandSenderContainer sender, String[] argData) {
        // Needs created but not destroyed
    }

    public enum CommandLevel{
        OP,
        PERMISSION_WILDCARD,
        CONSOLE,
        PLAYER
    }
}
