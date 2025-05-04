package com.sekwah.advancedportals.spigot.tags;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import com.sekwah.advancedportals.shadowed.inject.Inject;
import com.sekwah.advancedportals.spigot.connector.container.SpigotPlayerContainer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionsTag implements Tag.Activation, Tag.Split, Tag.Creation {
    @Inject
    private InfoLogger infoLogger;

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player,
                                ActivationData activeData, String[] argData) {
        for (String condition : argData) {
            if (player instanceof SpigotPlayerContainer) {
                SpigotPlayerContainer spigotPlayer =
                    (SpigotPlayerContainer) player;

                if (!checkConditions(condition, spigotPlayer.getPlayer())) {
                    spigotPlayer.sendMessage(
                        Lang.getNegativePrefix()
                        + Lang.translate("tag.conditions.fail"));
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player,
                              ActivationData activationData, String[] argData) {
    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player,
                             ActivationData activationData, String[] argData) {
        return false;
    }

    @Override
    public TagType[] getTagTypes() {
        return new TagType[] {TagType.PORTAL};
    }

    @Override
    public String getName() {
        return "conditions";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String description() {
        return Lang.translate("tag.conditions.description");
    }

    private boolean checkConditions(String condition, Player player) {
        // Regular expression to find valid operators with optional surrounding whitespace
        Pattern operatorPattern = Pattern.compile("\\s*(<=|>=|<|>|==)\\s*");
        Matcher matcher = operatorPattern.matcher(condition);

        if (!matcher.find()) {
            // Log a warning for invalid operator
            infoLogger.warning("Invalid operator: " + condition);
            return false;
        }

        // Extract the operator position and parts
        int operatorStart = matcher.start();
        int operatorEnd = matcher.end();
        String placeholder = condition.substring(0, operatorStart).trim();
        String operator = matcher.group(1);
        String restOfCondition = condition.substring(operatorEnd).trim();

        // Get the actual value from the placeholder
        String actualValue = PlaceholderAPI.setPlaceholders(player, placeholder);

        return performComparison(actualValue, operator, restOfCondition);
    }

    private boolean performComparison(String actualValue, String operator,
                                      String expectedValue) {
        if (isNumeric(actualValue) && isNumeric(expectedValue)) {
            // Numeric comparison
            double actualNumeric = Double.parseDouble(actualValue);
            double expectedNumeric = Double.parseDouble(expectedValue);

            switch (operator) {
                case "==":
                    return actualNumeric == expectedNumeric;
                case "<":
                    return actualNumeric < expectedNumeric;
                case ">":
                    return actualNumeric > expectedNumeric;
                case "<=":
                    return actualNumeric <= expectedNumeric;
                case ">=":
                    return actualNumeric >= expectedNumeric;
                default:
                    return false; // Unsupported operator
            }
        } else if (isBoolean(actualValue) && isBoolean(expectedValue)) {
            // Boolean comparison
            boolean actualBoolean = Boolean.parseBoolean(actualValue);
            boolean expectedBoolean = Boolean.parseBoolean(expectedValue);

            return actualBoolean == expectedBoolean;
        } else {
            // String comparison
            return actualValue.equals(expectedValue);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isBoolean(String str) {
        return str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false");
    }

    @Override
    public boolean created(TagTarget target, PlayerContainer player,
                           String[] argData) {
        for (String condition : argData) {
            if (player instanceof SpigotPlayerContainer) {
                SpigotPlayerContainer spigotPlayer =
                    (SpigotPlayerContainer) player;
                if (!checkConditions(condition, spigotPlayer.getPlayer())) {
                    spigotPlayer.sendMessage(
                        Lang.getNegativePrefix()
                        + Lang.translate("tag.conditions.invalid"));
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void destroyed(TagTarget target, PlayerContainer player,
                          String[] argData) {
    }
}
