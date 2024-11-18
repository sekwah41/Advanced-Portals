package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.commands.SubCommand;
import com.sekwah.advancedportals.core.connector.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.permissions.Permissions;
import com.sekwah.advancedportals.core.repository.ConfigRepository;
import com.sekwah.advancedportals.core.util.Lang;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LangUpdateSubCommand implements SubCommand {
    @Inject
    private AdvancedPortalsCore portalsCore;
    @Inject
    private ConfigRepository configRepository;

    public LangUpdateSubCommand() {
    }

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if (args.length > 1 && args[1].equalsIgnoreCase("overwrite")) {
            this.portalsCore.getDataStorage().copyDefaultFile(
                "lang/" + configRepository.getTranslation() + ".lang", true);
            sender.sendMessage(Lang.getPositivePrefix()
                               + Lang.translate("translatedata.replaced"));
            Lang.loadLanguage(configRepository.getTranslation());
        } else {
            // TODO check what keys are missing and append them to the end of
            // the file, check the translation first then GB
            Lang lang = Lang.instance;
            Map<String, String> internalTranslation =
                lang.getInternalLanguageMap(Lang.DEFAULT_LANG);
            internalTranslation.putAll(
                lang.getInternalLanguageMap(configRepository.getTranslation()));

            Map<String, String> currentTranslation =
                lang.getLanguageMap(configRepository.getTranslation());
            // Remove everything to leave just the missing keys
            for (Map.Entry<String, String> entry :
                 currentTranslation.entrySet()) {
                internalTranslation.remove(entry.getKey());
            }

            List<String> newTranslations = new ArrayList<>();
            for (Map.Entry<String, String> entry :
                 internalTranslation.entrySet()) {
                newTranslations.add(entry.getKey() + "=" + entry.getValue());
            }

            String appendText = String.join("\n", newTranslations);

            InputStream translationFile =
                this.portalsCore.getDataStorage().loadResource(
                    "lang/" + configRepository.getTranslation() + ".lang");
            String result =
                new BufferedReader(new InputStreamReader(translationFile))
                    .lines()
                    .collect(Collectors.joining("\n"));
            InputStream withExtras = new ByteArrayInputStream(
                result.concat("\n").concat(appendText).getBytes());
            this.portalsCore.getDataStorage().writeResource(
                withExtras,
                "lang/" + configRepository.getTranslation() + ".lang");

            Lang.loadLanguage(configRepository.getTranslation());

            sender.sendMessage(
                Lang.getPositivePrefix()
                + Lang.translateInsertVariables("translatedata.updated",
                                                newTranslations.size()));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return Permissions.LANG_UPDATE.hasPermission(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender,
                                      String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return Lang.translate("command.lang.help");
    }

    @Override
    public String getDetailedHelpText() {
        return Lang.translate("command.lang.help");
    }
}
