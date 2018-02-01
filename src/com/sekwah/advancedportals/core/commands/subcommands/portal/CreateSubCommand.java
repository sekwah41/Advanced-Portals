package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.portal.PortalTag;
import com.sekwah.advancedportals.core.api.portal.PortalException;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.coreconnector.container.CommandSenderContainer;
import com.sekwah.advancedportals.coreconnector.container.PlayerContainer;

import java.util.ArrayList;
import java.util.List;

public class CreateSubCommand implements SubCommand {

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translate("command.create.console"));
                return;
            }
            ArrayList<PortalTag> portalTags = new ArrayList<>();
            boolean partingValueWithSpaces = false;
            String argBeingParsed = "";
            String currentParsedValue = "";
            for (int i = 1; i < args.length; i++) {
                if(partingValueWithSpaces) {
                    if(args[i].charAt(args[i].length() - 1) == '"') {
                        args[i] = args[i].substring(0, args[i].length() - 1);
                        partingValueWithSpaces = false;
                        portalTags.add(new PortalTag(argBeingParsed, currentParsedValue));
                    }
                    else {
                        currentParsedValue += " " + args[i];
                    }
                }
                else {
                    String detectedTag = this.getTag(args[i].toLowerCase());
                    if(detectedTag != null) {
                        String arg = args[i].substring(detectedTag.length());
                        if(arg.length() > 0 && arg.charAt(0) == '"') {
                            argBeingParsed = detectedTag;
                            currentParsedValue = arg;
                        }
                        else {
                            portalTags.add(new PortalTag(detectedTag, arg));
                        }
                    }
                }
            }
            try {
                AdvancedPortalsCore.getPortalManager().createPortal(player, portalTags);
            } catch (PortalException portalTagExeption) {
                sender.sendMessage(Lang.translateColor("messageprefix.negative") + Lang.translateColor("command.create.error") + " "
                        + portalTagExeption.getMessage());
            }
        }
        else {
            sender.sendMessage(Lang.translate("command.create.noargs"));
        }
    }

    private String getTag(String arg) {
        ArrayList<String> tags = AdvancedPortalsCore.getTagRegistry().getTags();
        for(String tag : tags) {
            if(arg.startsWith(tag + ":")) {
                return tag;
            }
        }
        return null;
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.createportal");
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
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
