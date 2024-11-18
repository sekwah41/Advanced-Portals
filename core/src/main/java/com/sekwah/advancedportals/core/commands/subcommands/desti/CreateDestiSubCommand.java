package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.subcommands.common.CreateTaggedSubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.destination.Destination;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.services.DestinationServices;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.TagReader;
import com.sekwah.advancedportals.core.warphandler.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateDestiSubCommand extends CreateTaggedSubCommand {
    @Inject
    TagRegistry tagRegistry;

    @Inject
    DestinationServices destinationServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length > 1) {
            PlayerContainer player = sender.getPlayerContainer();
            if (player == null) {
                sender.sendMessage(
                    Lang.getNegativePrefix()
                    + Lang.translate("command.create.destination.console"));
                return;
            }

            ArrayList<DataTag> destinationTags =
                TagReader.getTagsFromArgs(args);

            // Find the tag with the "name" NAME
            DataTag nameTag = destinationTags.stream()
                                  .filter(tag -> tag.NAME.equals("name"))
                                  .findFirst()
                                  .orElse(null);

            // If the tag is null, check if arg[1] has a : to check it's not a
            // tag.
            if (nameTag == null && !args[1].contains(":")) {
                nameTag = new DataTag("name", args[1]);
                destinationTags.add(nameTag);
            }

            if (nameTag == null) {
                sender.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translate("command.error.noname"));
                return;
            }
            sender.sendMessage(Lang.centeredTitle(
                Lang.translate("command.create.destination.prep")));
            sender.sendMessage("");
            sender.sendMessage(Lang.translate("command.create.tags"));

            if (!destinationTags.isEmpty()) {
                this.filterAndProcessTags(destinationTags);
                TagReader.printArgs(sender, destinationTags);
            }
            sender.sendMessage("");
            Destination destination = destinationServices.createDesti(
                player, player.getLoc(), destinationTags);
            if (destination != null) {
                sender.sendMessage(
                    Lang.getPositivePrefix()
                    + Lang.translate("command.create.destination.complete"));
            } else {
                sender.sendMessage("");
                sender.sendMessage(
                    Lang.getNegativePrefix()
                    + Lang.translate("command.create.destination.error"));
            }
        } else {
            sender.sendMessage(Lang.getNegativePrefix()
                               + Lang.translate("command.error.noname"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.CREATE_DESTI.hasPermission(sender);
    }

    @Override
    protected List<Tag> getRelatedTags() {
        var tags = tagRegistry.getTags();
        // Filter tags that support Destination
        return tags.stream()
            .filter(tag
                    -> Arrays.asList(tag.getTagTypes())
                           .contains(Tag.TagType.DESTINATION))
            .toList();
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.create.destination.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.create.destination.detailedhelp");
    }
}
