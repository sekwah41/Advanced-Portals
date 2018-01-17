package com.sekwah.advancedportals.core.util;

import com.google.gson.Gson;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;

import java.io.*;

public class DataStorage {

    private Gson gson;

    private File dataFolder;

    public DataStorage(File dataFolder) {
        this.dataFolder = dataFolder;
        gson = new Gson();
    }

    /**
     * Copies the default file, defaults to true to keep true to the name
     *
     * @param fileLoc
     * @return
     */
    public boolean copyDefaultFile(String fileLoc) {
        return this.copyDefaultFile(fileLoc, true);
    }

    public void copyDefaultFiles(boolean override, String... fileLocs) {
        for (String fileLoc : fileLocs) {
            this.copyDefaultFile(fileLoc, override);
        }
    }

    public <T> T loadJson(Class<T> dataHolder, String location) {
        // TODO get json
        return gson.fromJson("", dataHolder);
    }

    public void storeJson(Object dataHolder, String location) {
        // TODO do writing code
        gson.toJson(dataHolder);
    }

    /**
     * Copies the specified file out of the plugin and into the plugins folder.
     *
     * @param fileLoc
     * @return if the file is copied, will be false if override is false and the file already existed.
     */
    public boolean copyDefaultFile(String fileLoc, boolean overwrite) {
        File outFile = new File(this.dataFolder, fileLoc);
        if (!outFile.exists()) {
            outFile.getParentFile().mkdirs();
        }
        if (!outFile.exists() || overwrite) {
            try {
                InputStream inputStream = DataStorage.class.getClassLoader().getResourceAsStream(fileLoc);
                FileOutputStream outStream = new FileOutputStream(outFile);

                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    outStream.write(buf, 0, len);
                }
                inputStream.close();
                outStream.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                AdvancedPortalsCore.getInfoLogger().logWarning("Could not load " + fileLoc + ". The file does" +
                        "not exist or there has been an error reading the file.");
                return false;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                AdvancedPortalsCore.getInfoLogger().logWarning("Could not create " + fileLoc);
            } catch (IOException e) {
                e.printStackTrace();
                AdvancedPortalsCore.getInfoLogger().logWarning("File error reading " + fileLoc);
            }
        }
        return true;
    }

    /**
     * A method to try to grab the files from the plugin and if its in the plugin folder load from there instead.
     * <p>
     * TODO add loading from the plugin folder first rather than straight from the plugin.
     *
     * @param lang
     * @param location
     * @return
     */
    public InputStream loadResource(Lang lang, String location) {
        File inFile = new File(dataFolder, location);
        if (inFile.exists() && !inFile.isDirectory()) {
            try {
                return new FileInputStream(inFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try {
                copyDefaultFile(location, false);
                return lang.getClass().getClassLoader().getResourceAsStream(location);
            } catch (NullPointerException e) {
                e.printStackTrace();
                AdvancedPortalsCore.getInfoLogger().logWarning("Could not load " + location + ". The file does" +
                        "not exist or there has been an error reading the file.");
                return null;
            }
        }
    }

}
