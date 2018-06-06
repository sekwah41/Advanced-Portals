package com.sekwah.advancedportals.core.util;

import com.sekwah.advancedportals.core.AdvancedPortalsCore;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author sekwah41
 *         <p>
 *         The language translation file for the game. Will always load english first
 *         so that if the translations are missing any then they are still readable and can then be translated.
 *         (Its better than a raw translate string)
 *         <p>
 *         TODO add a loaddefault where it only loads from the plugins version of the data rather than paying attention to any
 *         possible changed versions in the lang folder.
 */
public class Lang {

    private static final Lang instance = new Lang();
    private final HashMap<String, String> languageMap = new HashMap<>();
    //private final String DEFAULT_LANG = "en_GB";

    /*public Lang() {
        injectTranslations(this, DEFAULT_LANG);
    }*/

    public static void loadLanguage(String fileName) {
        instance.injectTranslations(instance, fileName);
    }

    public static String translate(String s) {
        if (instance.languageMap.containsKey(s)) {
            return instance.languageMap.get(s);
        } else {
            return s;
        }
    }

    public static String translateInsertVariables(String s, Object... args) {
        String translation = instance.translate(s);
        for (int i = 1; i <= args.length; i++) {
            translation = translation.replaceAll("%" + i + "\\$s", args[i-1].toString());
        }
        return translation;
    }

    public static String translateInsertVariablesColor(String s, Object... args) {
        String translation = instance.translateColor(s);
        for (int i = 1; i <= args.length; i++) {
            translation = translation.replaceAll("%" + i + "\\$s", args[i-1].toString());
        }
        return translation;
    }

    public static String translateColor(String s) {
        String translation = instance.translate(s);
        translation = translation.replaceAll("\\\\u00A7", "\u00A7");
        return translation;
    }

    private void injectTranslations(Lang lang, String fileName) {
        try {
            //URL url = lang.getClass().getClassLoader().getResource("lang/" + fileName + ".lang");
            //System.out.println(url);
            //Map<String, String> newLangMap = lang.parseLang(url.openStream());
            InputStream stream = AdvancedPortalsCore.getInstance().getDataStorage().loadResource("lang/" + fileName + ".lang");
            if (stream != null) {
                Map<String, String> newLangMap = lang.parseLang(stream);
                if (newLangMap != null) {
                    lang.languageMap.putAll(newLangMap);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            AdvancedPortalsCore.getInstance().getInfoLogger().logWarning("Could not load " + fileName + ".lang The file does" +
                    "not exist or there has been an error reading the file. Canceled loading language file.");
        }
    }

    private Map<String, String> parseLang(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String line = getNextLine(scanner);
        HashMap<String, String> newMap = new HashMap<>();
        while (scanner != null && line != null) {
            //System.out.println(line);
            if (!line.startsWith("#") && line.indexOf('=') > -1) {
                int split = line.indexOf('=');
                String key = line.substring(0, split);
                String value = line.substring(split + 1, line.length());
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

    private String getNextLine(Scanner scanner) {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }
}
