package com.sekwah.advancedportals.core.tags.activation;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import javax.annotation.Nullable;

public class CommandTag implements Tag.Activation, Tag.Split{

    public static String TAG_NAME = "command";

    private final TagType[] tagTypes = new TagType[]{ TagType.PORTAL };

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
    public boolean preActivated(TagTarget target, PlayerContainer player, ActivationData activeData, String[] argData) {
        return true;
    }
    //TODO: Check if its worth autocompleting an existing command in the command tag by
    //                          grabbing all commands in the server

    //TODO: Add a warning in console if op/* command is used and tell them to use console instead
    @Override
    public void postActivated(TagTarget target, PlayerContainer player, ActivationData activationData, String[] argData) {
        if(activationData.hasActivated()) {
            for (String command : argData) {

                String formattedCommand = command.replaceAll("@player", player.getName());
                char executionCommand = formattedCommand.charAt(0);

                switch (executionCommand) {
                    case '!':
                        player.performCommand(formattedCommand.substring(1),CommandLevel.OP);
                        break;
                    case '#':
                        player.performCommand(formattedCommand.substring(1), CommandLevel.CONSOLE);
                        break;
                    case '^':
                        player.performCommand(formattedCommand.substring(1), CommandLevel.STAR);
                        break;
                    default:
                        player.performCommand(formattedCommand, CommandLevel.PLAYER);
                        break;
                }
            }
        }
    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player, ActivationData activationData, String[] argData) {
        return true;
    }

    public enum CommandLevel{
        OP,
        STAR,
        CONSOLE,
        PLAYER
    }
}
