package com.sekwah.advancedportals.legacyspigot.tags;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import com.sekwah.advancedportals.shadowed.inject.Inject;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import com.sekwah.advancedportals.legacyspigot.connector.container.LegacySpigotPlayerContainer;

public class ConditionsTag implements Tag.Activation, Tag.Split, Tag.Creation {
    @Inject
    private InfoLogger infoLogger;

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player,
                                ActivationData activeData, String[] argData) {
        for (String condition : argData) {
            if (player instanceof LegacySpigotPlayerContainer) {
                LegacySpigotPlayerContainer spigotPlayer =
                    (LegacySpigotPlayerContainer) player;

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
        // Remove whitespaces before splitting the condition
        String trimmedCondition = condition.replaceAll("\\s+", "");

        // Check if the condition contains a valid operator
        if (!trimmedCondition.matches(".*(<=|>=|<|>|==).*")) {
            // Log a warning or handle the case where the condition format is
            // invalid
            infoLogger.warning("Invalid operator: " + condition);
            return false;
        }

        // Split the condition into placeholder and value parts
        String[] parts = trimmedCondition.split("<=|>=|<|>|==");

        if (parts.length == 2) {
            // Trim to remove any leading/trailing whitespaces
            String placeholder = parts[0].trim();
            String actualValue =
                PlaceholderAPI.setPlaceholders(player, placeholder);
            String restOfCondition = parts[1].trim();

            // Preserve the operator
            String operator =
                condition
                    .substring(placeholder.length(),
                               condition.length() - restOfCondition.length())
                    .trim();

            return performComparison(actualValue, operator, restOfCondition);
        } else {
            // Log a warning or handle the case where the condition format is
            // invalid
            infoLogger.warning("Invalid condition format: " + condition);
            return false;
        }
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
            if (player instanceof LegacySpigotPlayerContainer) {
                LegacySpigotPlayerContainer spigotPlayer =
                    (LegacySpigotPlayerContainer) player;
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
