package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.commands.subcommands.common.CreateTaggedSubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.registry.TagRegistry;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.serializeddata.DataTag;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.tags.NameTag;
import com.sekwah.advancedportals.core.tags.TriggerBlockTag;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.util.TagReader;
import com.sekwah.advancedportals.core.warphandler.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatePortalSubCommand extends CreateTaggedSubCommand {
    @Inject
    PortalServices portalServices;

    @Inject
    TagRegistry tagRegistry;

    @Inject
    ConfigRepository config;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length > 1) {
            PlayerContainer player = sender.getPlayerContainer();
            if (player == null) {
                sender.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translate("command.create.console"));
                return;
            }

            ArrayList<DataTag> portalTags = TagReader.getTagsFromArgs(args);

            // Find the tag with the "name" NAME
            DataTag nameTag =
                portalTags.stream()
                    .filter(tag -> tag.NAME.equals(NameTag.TAG_NAME))
                    .findFirst()
                    .orElse(null);

            // If the tag is null, check if arg[1] has a : to check it's not a
            // tag.
            if (nameTag == null && !args[1].contains(":")) {
                nameTag = new DataTag("name", args[1]);
                portalTags.add(nameTag);
            }

            if (nameTag == null) {
                sender.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translate("command.error.noname"));
                return;
            }

            if (!portalTags.isEmpty()) {
                this.filterAndProcessTags(portalTags);
                TagReader.printArgs(sender, portalTags);
            }
            sender.sendMessage("");

            var triggerBlockTag =
                portalTags.stream()
                    .filter(tag -> tag.NAME.equals(TriggerBlockTag.TAG_NAME))
                    .findFirst()
                    .orElse(null);

            if (triggerBlockTag == null) {
                portalTags.add(new DataTag(TriggerBlockTag.TAG_NAME,
                                           config.getDefaultTriggerBlock()));
            }

            AdvancedPortal portal =
                portalServices.createPortal(sender, portalTags);
            if (portal != null) {
                sender.sendMessage(Lang.getPositivePrefix()
                                   + Lang.translate("command.create.complete"));
                sender.sendMessage(Lang.translate("command.create.tags"));
                TagReader.printArgs(sender, portal.getArgs());
            } else {
                sender.sendMessage(Lang.getNegativePrefix()
                                   + Lang.translate("command.create.error"));
            }
        } else {
            sender.sendMessage(Lang.getNegativePrefix()
                               + Lang.translate("command.error.notags"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.CREATE_PORTAL.hasPermission(sender);
    }

    @Override
    protected List<Tag> getRelatedTags() {
        var tags = tagRegistry.getTags();
        // Filter tags that support Destination
        return tags.stream()
            .filter(tag
                    -> Arrays.asList(tag.getTagTypes())
                           .contains(Tag.TagType.PORTAL))
            .toList();
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.create.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.create.detailedhelp");
    }
}
