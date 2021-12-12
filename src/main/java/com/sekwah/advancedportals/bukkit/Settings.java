package com.sekwah.advancedportals.bukkit;

import com.sekwah.advancedportals.bukkit.config.ConfigAccessor;

import static com.sekwah.advancedportals.bukkit.Settings.PortalConfigOption.*;

/**
 * This contains generally used settings mostly
 */
public class Settings {

    private int currentWarpParticles = 0;

    private int currentWarpSound = 0;

    private String commandLevels = "n";

    private boolean worldEditEnabled = false;

    public enum PortalConfigOption {
        COMMAND_LEVELS("CommandLevels"),
        WARP_PARTICLES("WarpParticles"),
        WARP_SOUND("WarpSound"),
        WORLDEDIT_INTEGRATION("WorldEditIntegration");

        private final String target;

        PortalConfigOption(String target) {
            this.target = target;
        }

        public String value() {
            return this.target;
        }
    }

    public Settings(AdvancedPortalsPlugin plugin) {
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        currentWarpParticles = config.getConfig().getInt(WARP_PARTICLES.value());
        currentWarpSound = config.getConfig().getInt(WARP_SOUND.value());

        commandLevels = config.getConfig().getString(COMMAND_LEVELS.value(), "opcb");

        worldEditEnabled = config.getConfig().getBoolean(WORLDEDIT_INTEGRATION.value(), false);

        assert commandLevels != null;
        if(commandLevels.equals("opchek")) {
            commandLevels = "opcb";
            config.getConfig().set(COMMAND_LEVELS.value(), "opcb");
        }
        if(commandLevels.contains("n") || commandLevels.equals("")) {
            commandLevels = "n";
        }
    }

    public String getCommandLevels(){
        return this.commandLevels;
    }

    public boolean enabledCommandLevel(String level){
        return this.commandLevels.contains(level);
    }

    public int getCurrentWarpSound() {
        return currentWarpSound;
    }

    public int getCurrentWarpParticles() {
        return currentWarpParticles;
    }

    public boolean enabledWorldEditIntegration() {
        return worldEditEnabled;
    }
}
