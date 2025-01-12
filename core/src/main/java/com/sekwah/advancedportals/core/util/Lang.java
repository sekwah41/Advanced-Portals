package com.sekwah.advancedportals.core.util;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.serializeddata.DataStorage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author sekwah41
 *     <p>The language translation file for the game. Will always load english
 * first so that if the translations are missing any then they are still
 * readable and can then be translated. (It's better than a raw translate
 * string) <p>
 */
public class Lang {
    public static final Lang instance = new Lang();
    private final HashMap<String, String> languageMap = new HashMap<>();

    private String negativePrefix;

    private String positivePrefix;

    @Inject
    private DataStorage dataStorage;

    @Inject
    private InfoLogger infoLogger;

    public static final String DEFAULT_LANG = "en_GB";

    public static void loadLanguage(String fileName) {
        if (!DEFAULT_LANG.equals(fileName)) {
            instance.injectTranslations(DEFAULT_LANG);
        }
        instance.injectTranslations(fileName);

        instance.negativePrefix = translate("messageprefix.negative");
        instance.positivePrefix = translate("messageprefix.positive");
    }

    public static String getNegativePrefix() {
        return instance.negativePrefix;
    }

    public static String getPositivePrefix() {
        return instance.positivePrefix;
    }

    public static String translate(String s) {
        if (instance.languageMap.containsKey(s)) {
            String translation = instance.languageMap.get(s);
            // noinspection ALL (not sure what the specific warning is for
            // escaped unicode)
            translation = convertColors(translation);
            return translation;
        } else {
            return s;
        }
    }

    public static String convertColors(String s) {
        return s
            .replaceAll(
                "&#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])",
                "&x&$1&$2&$3&$4&$5&$6")
            .replaceAll("&([0-9a-fA-Fdwrk-ox#])", "\u00A7$1");
    }

    public static String translateInsertVariables(String s, Object... args) {
        String translation = translate(s);
        for (int i = 1; i <= args.length; i++) {
            translation = translation.replaceAll("%" + i + "\\$s",
                                                 args[i - 1].toString());
        }
        return translation;
    }

    public Map<String, String> getLanguageMap(String fileName) {
        InputStream stream =
            this.dataStorage.loadResource("lang/" + fileName + ".lang");
        if (stream != null) {
            return Lang.parseLang(stream);
        }
        return Collections.emptyMap();
    }

    public Map<String, String> getInternalLanguageMap(String fileName) {
        InputStream stream =
            this.getClass().getClassLoader().getResourceAsStream(
                "lang/" + fileName + ".lang");
        if (stream != null) {
            return Lang.parseLang(stream);
        }
        return Collections.emptyMap();
    }

    /**
     * Length of text excluding colour codes
     *
     * @param text
     * @return
     */
    public static int textLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\u00A7') {
                i++;
            } else {
                length++;
            }
        }
        return length;
    }

    /**
     * The default font is not monospaced, so this will likely be a little
     * thinner to be on the safe side
     *
     * @param title
     * @return
     */
    public static String centeredTitle(String title) {
        int titleLength = 54 - (Lang.textLength(title));

        int eachSide = titleLength / 2;

        StringBuilder repeatedEquals = new StringBuilder();
        for (int i = 0; i < eachSide; i++) {
            repeatedEquals.append("=");
        }

        return "\u00A7e" + repeatedEquals.toString() + " " + title + " \u00A7e" + repeatedEquals.toString();
    }

    private void injectTranslations(String fileName) {
        try {
            URL url = Lang.instance.getClass().getClassLoader().getResource(
                "lang/" + fileName + ".lang");
            if (url != null) {
                Map<String, String> initialMap =
                    Lang.parseLang(url.openStream());
                Lang.instance.languageMap.putAll(initialMap);
            } else {
                this.infoLogger.warning("Could not load " + fileName
                                        + (".lang from within Advanced Portals "
                                           + "as it doesn't exist."));
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.infoLogger.warning("Could not load " + fileName
                                    + ".lang from within Advanced Portals.");
        }

        Map<String, String> newLangMap = this.getLanguageMap(fileName);
        Lang.instance.languageMap.putAll(newLangMap);
    }

    public static Map<String, String> parseLang(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String line = getNextLine(scanner);
        HashMap<String, String> newMap = new HashMap<>();
        while (line != null) {
            if (!line.startsWith("#") && line.indexOf('=') > -1) {
                int split = line.indexOf('=');
                String key = line.substring(0, split);
                String value = line.substring(split + 1);
                newMap.put(key, value);
            }
            line = getNextLine(scanner);
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newMap;
    }

    private static String getNextLine(Scanner scanner) {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }
}
