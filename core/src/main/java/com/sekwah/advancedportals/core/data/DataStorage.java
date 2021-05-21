package com.sekwah.advancedportals.core.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;

import java.io.*;
import java.lang.reflect.Type;

public class DataStorage {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private File dataFolder;

    @Inject
    private AdvancedPortalsCore portalsCore;

    public DataStorage(File dataStorageLoc) {
        this.dataFolder = dataStorageLoc;
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

    public <T> T loadJson(Type dataHolder, String location) {
        InputStream jsonResource = this.loadResource(location);
        if(jsonResource == null) {
            return null;
        }
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(jsonResource));
        T object = gson.fromJson(bufReader, dataHolder);
        return object;
    }
    public <T> T loadJson(Class<T> dataHolder, String location) {
        InputStream jsonResource = this.loadResource(location);
        if(jsonResource == null) {
            try {
                return dataHolder.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(jsonResource));
        return gson.fromJson(bufReader, dataHolder);
    }

    public void storeJson(Object dataHolder, String location) {
        String json = gson.toJson(dataHolder);
        try {
            FileWriter fileWriter = new FileWriter(new File(this.dataFolder, location));
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileLoc);
                if(inputStream == null) {
                    return false;
                }

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
                this.portalsCore.getInfoLogger().logWarning("Could not load " + fileLoc + ". The file does" +
                        "not exist or there has been an error reading the file.");
                return false;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                this.portalsCore.getInfoLogger().logWarning("Could not create " + fileLoc);
            } catch (IOException e) {
                e.printStackTrace();
                this.portalsCore.getInfoLogger().logWarning("File error reading " + fileLoc);
            }
        }
        return true;
    }

    /**
     * A method to try to grab the files from the plugin and if its in the plugin folder load from there instead.
     * <p>
     * @param location
     * @return
     */
    public InputStream loadResource(String location) {
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
                return this.getClass().getClassLoader().getResourceAsStream(location);
            } catch (NullPointerException e) {
                e.printStackTrace();
                this.portalsCore.getInfoLogger().logWarning("Could not load " + location + ". The file does" +
                        "not exist or there has been an error reading the file.");
                return null;
            }
        }
    }

}
