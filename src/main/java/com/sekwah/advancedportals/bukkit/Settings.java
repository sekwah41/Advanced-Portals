package com.sekwah.advancedportals.bukkit;

/**
 * This contains generally used settings mostly
 */
public class Settings {

    private int currentWarpParticles = 0;

    private int currentWarpSound = 0;

    private String commandLevels = "n";

    public Settings(AdvancedPortalsPlugin plugin) {
        ConfigAccessor config = new ConfigAccessor(plugin, "config.yml");
        currentWarpParticles = config.getConfig().getInt("WarpParticles");
        currentWarpSound = config.getConfig().getInt("WarpSound");

        commandLevels = config.getConfig().getString("CommandLevels", "opchek");
        if(commandLevels.contains("n") || commandLevels.equals("")) {
            commandLevels = "n";
        }
    }

    public String getCommandLevels(){
        return this.commandLevels;
    }

    public boolean hasCommandLevel(String level){
        return this.commandLevels.contains(level);
    }

    public int getCurrentWarpSound() {
        return currentWarpSound;
    }

    public int getCurrentWarpParticles() {
        return currentWarpParticles;
    }
}
