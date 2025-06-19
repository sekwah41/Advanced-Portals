package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.util.Lang;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PortalLangSubCommand implements SubCommand {

    @Inject
    private ConfigRepository configRepository;

    @Inject
    private AdvancedPortalsCore portalsCore;


    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length < 2) {
            List<String> langs = getAvailableLanguages();
            sender.sendMessage(Lang.getPositivePrefix() + Lang.translate("command.portal.lang.list") + String.join(", ", langs));
            sender.sendMessage(Lang.getPositivePrefix() + Lang.translate("command.portal.lang.usage"));
        } else {
            String langCode = args[1];
            List<String> langs = getAvailableLanguages();
            if (!langs.contains(langCode)) {
                sender.sendMessage(Lang.getNegativePrefix() + Lang.translateInsertVariables("command.portal.lang.notfound", langCode));
                return;
            }
            configRepository.setTranslation(langCode);
            configRepository.storeConfig();
            Lang.loadLanguage(langCode);
            sender.sendMessage(Lang.getPositivePrefix() + Lang.translateInsertVariables("command.portal.lang.set", langCode));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.PORTAL_LANG.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        if (args.length == 2) {
            return getAvailableLanguages();
        }
        return Collections.emptyList();
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.portal.lang.basic");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.portal.lang.detailed");
    }

    private List<String> getAvailableLanguages() {
        List<String> langs = new ArrayList<>();
        langs.addAll(portalsCore.getDataStorage().listAllFiles("lang", true, "lang"));
        langs.addAll(portalsCore.getDataStorage().listAllFiles("lang", true, "yml"));
        return langs;
    }
}
