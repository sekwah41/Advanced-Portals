package com.sekwah.advancedportals.spigot.tags;

import com.sekwah.advancedportals.core.connector.containers.PlayerContainer;
import com.sekwah.advancedportals.core.registry.TagTarget;
import com.sekwah.advancedportals.core.util.Lang;
import com.sekwah.advancedportals.core.warphandler.ActivationData;
import com.sekwah.advancedportals.core.warphandler.Tag;
import com.sekwah.advancedportals.spigot.connector.container.SpigotPlayerContainer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.List;

public class CostTag implements Tag.Activation, Tag.AutoComplete, Tag.Creation {

    Economy economy;

    public CostTag(Economy economy) {
        this.economy = economy;
    }

    @Override
    public boolean preActivated(TagTarget target, PlayerContainer player, ActivationData activeData, String[] argData) {
        double cost;
        try {
            cost = Double.parseDouble(argData[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(Lang.getNegativePrefix() + Lang.translate("tag.cost.error"));
            return false;
        }

        if (!(player instanceof SpigotPlayerContainer)) return false;

        SpigotPlayerContainer spigotPlayer = (SpigotPlayerContainer) player;

        if (economy.has(spigotPlayer.getPlayer(), cost)) {
            economy.withdrawPlayer(spigotPlayer.getPlayer(), cost);
        } else {
            player.sendMessage(Lang.getNegativePrefix() + Lang.translateInsertVariables("tag.cost.fail", cost));
            return false;
        }

        return true;
    }

    @Override
    public void postActivated(TagTarget target, PlayerContainer player, ActivationData activationData, String[] argData) {

    }

    @Override
    public boolean activated(TagTarget target, PlayerContainer player, ActivationData activationData, String[] argData) {
        return false;
    }

    @Override
    public TagType[] getTagTypes() {
        return new TagType[]{TagType.PORTAL, TagType.DESTINATION};
    }

    @Override
    public String getName() {
        return "cost";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String description() {
        return Lang.translate("tag.cost.description");
    }

    @Override
    public List<String> autoComplete(String argData) {
        List<String> suggestions = new ArrayList<>();
        suggestions.add("10");
        suggestions.add("100");
        suggestions.add("1000");
        suggestions.add("10000");
        suggestions.add("100000");
        return suggestions;
    }

    @Override
    public boolean created(TagTarget target, PlayerContainer player, String[] argData) {
        try {
            Double.parseDouble(argData[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(Lang.getNegativePrefix() + Lang.translate("tag.cost.number"));
            return false;
        }
        if (Double.parseDouble(argData[0]) <= 0) {
            player.sendMessage(Lang.getNegativePrefix() + Lang.translate("tag.cost.negative"));
            return false;
        }
        return true;
    }

    @Override
    public void destroyed(TagTarget target, PlayerContainer player, String[] argData) {

    }
}
