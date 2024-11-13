package com.sekwah.advancedportals.velocity.connector;

import java.util.HashMap;
import java.util.Map;

/**
 * I believe later versions of velocity have some component handlers for this, and I could just send it to the server as components
 * but ive decided to throw this in for now just to use the plugin logger. Also, to try to clone the bungee behavior as close as possible.
 */
public class MinecraftToAnsi {

    private static final Map<Character, String> minecraftToAnsiMap = new HashMap<>();

    static {
        minecraftToAnsiMap.put('0', "\u001B[30m"); // Black
        minecraftToAnsiMap.put('1', "\u001B[34m"); // Dark Blue
        minecraftToAnsiMap.put('2', "\u001B[32m"); // Dark Green
        minecraftToAnsiMap.put('3', "\u001B[36m"); // Dark Aqua
        minecraftToAnsiMap.put('4', "\u001B[31m"); // Dark Red
        minecraftToAnsiMap.put('5', "\u001B[35m"); // Dark Purple
        minecraftToAnsiMap.put('6', "\u001B[33m"); // Gold
        minecraftToAnsiMap.put('7', "\u001B[37m"); // Gray
        minecraftToAnsiMap.put('8', "\u001B[90m"); // Dark Gray
        minecraftToAnsiMap.put('9', "\u001B[94m"); // Blue
        minecraftToAnsiMap.put('a', "\u001B[92m"); // Green
        minecraftToAnsiMap.put('b', "\u001B[96m"); // Aqua
        minecraftToAnsiMap.put('c', "\u001B[91m"); // Red
        minecraftToAnsiMap.put('d', "\u001B[95m"); // Light Purple
        minecraftToAnsiMap.put('e', "\u001B[93m"); // Yellow
        minecraftToAnsiMap.put('f', "\u001B[97m"); // White
        minecraftToAnsiMap.put('k', "\u001B[5m");  // Obfuscated (Blinking)
        minecraftToAnsiMap.put('l', "\u001B[1m");  // Bold
        minecraftToAnsiMap.put('m', "\u001B[9m");  // Strikethrough
        minecraftToAnsiMap.put('n', "\u001B[4m");  // Underline
        minecraftToAnsiMap.put('o', "\u001B[3m");  // Italic
        minecraftToAnsiMap.put('r', "\u001B[0m");  // Reset
    }

    public static String convert(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\u00A7' && i + 1 < text.length()) {
                char code = Character.toLowerCase(text.charAt(i + 1));
                String ansiCode = minecraftToAnsiMap.getOrDefault(code, "");
                result.append(ansiCode);
                i++;
            } else {
                result.append(text.charAt(i));
            }
        }

        result.append("\u001B[0m");
        return result.toString();
    }
}
