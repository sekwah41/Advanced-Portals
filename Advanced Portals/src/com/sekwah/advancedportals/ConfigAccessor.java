package com.sekwah.advancedportals;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
 
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
 
public class ConfigAccessor {
 
    private final String fileName;
    private final JavaPlugin plugin;
 
    private File configFile;
    private FileConfiguration fileConfiguration;
 
    public ConfigAccessor(JavaPlugin plugin, String fileName) {
        if (!plugin.isInitialized())
            throw new IllegalArgumentException("plugin must be initiaized");
        this.plugin = plugin;
        this.fileName = fileName;
    }
    
    // gets all of the 
    public void reloadConfig() {
        if (configFile == null) {
            File dataFolder = plugin.getDataFolder();
            if (dataFolder == null)
                throw new IllegalStateException();
            configFile = new File(dataFolder, fileName);
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
 
        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource(fileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            fileConfiguration.setDefaults(defConfig);
        }
    }
 
    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            this.reloadConfig();
        }
        return fileConfiguration;
    }
    
    // Saves all the data to the selected yml file
    public void saveConfig() {
        if (fileConfiguration == null || configFile == null) {
            return;
        } else {
            try {
                getConfig().save(configFile);
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
            }
        }
    }
    
    // Saves  
    public void saveDefaultConfig() {
        if (!configFile.exists()) {       
            this.plugin.saveResource(fileName, false);
        }
    }
 
}