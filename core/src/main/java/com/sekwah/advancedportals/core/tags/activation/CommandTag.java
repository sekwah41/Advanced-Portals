package com.sekwah.advancedportals.core.tags.activation;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
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
        return true;
    }
    // TODO: Check if its worth autocompleting an existing command in the
    // command tag by
    //                           grabbing all commands in the server

    // TODO: Add a warning in console if op/* command is used and tell them to
    // use console instead
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
        return true;
    }

    @Override
    public boolean created(TagTarget target, PlayerContainer player,
                           String[] argData) {
        var commandPortals = configRepository.getCommandPortals();
        if (argData != null) {
            for (String command : argData) {
                char executionCommand = command.charAt(0);
                return switch (executionCommand) {
                    case '!' -> {
                        if (!player.hasPermission("advancedportals.createportal.commandlevel.op")) {
                            player.sendMessage(Lang.translateInsertVariables("tag.command.nopermission", "OP"));
                            yield false;
                        }
                        yield true;
                    }
                    case '#' -> {
                        if (!player.hasPermission("advancedportals.createportal.commandlevel.console")) {
                            player.sendMessage(Lang.translateInsertVariables("tag.command.nopermission","Console"));
                            yield false;
                        }
                        yield true;
                    }
                    case '^' -> {
                        if (!player.hasPermission("advancedportals.createportal.commandlevel.permswild")) {
                            player.sendMessage(Lang.translateInsertVariables("tag.command.nopermission", "*"));
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
    public void destroyed(TagTarget target, PlayerContainer player, String[] argData) {
        // Needs created but not destroyed
    }

    public enum CommandLevel{
        OP,
        PERMISSION_WILDCARD,
        CONSOLE,
        PLAYER
    }
}