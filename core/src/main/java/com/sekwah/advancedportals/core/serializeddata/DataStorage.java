package com.sekwah.advancedportals.core.serializeddata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.util.InfoLogger;
import com.sekwah.advancedportals.core.serializeddata.config.Config;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {

    private final File dataFolder;

    @Inject
    private AdvancedPortalsCore portalsCore;

    @Inject
    private InfoLogger infoLogger;

    private ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));

    public DataStorage(File dataStorageLoc) {
        this.dataFolder = dataStorageLoc;
    }

    public boolean copyDefaultFile(String fileLoc) {
        return this.copyDefaultFile(fileLoc, true);
    }

    public void copyDefaultFiles(boolean override, String... fileLocs) {
        for (String fileLoc : fileLocs) {
            this.copyDefaultFile(fileLoc, override);
        }
    }

    public <T> T loadFile(Class<T> dataHolder, String location) {
        InputStream yamlResource = this.loadResource(location);
        if (yamlResource == null) {
            try {
                return dataHolder.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(yamlResource));
        try {
            T data = mapper.readValue(bufReader, dataHolder);
            bufReader.close();
            return data;
        } catch (IOException e) {
            infoLogger.warning("Failed to load file: " + location);
            infoLogger.error(e);
            return null;
        }
    }

    public boolean storeFile(Object dataHolder, String location) {
        File outFile = new File(this.dataFolder, location);
        if (!outFile.getParentFile().exists()) { // Check if parent folder exists
            if(!outFile.getParentFile().mkdirs()) {
                infoLogger.warning("Failed to create folder for file: " + location);
            }
        }

        try (FileWriter fileWriter = new FileWriter(outFile)) {
            String yamlFile = mapper.writeValueAsString(dataHolder);
            fileWriter.write(yamlFile);
            return true;
        } catch (IOException e) {
            infoLogger.error(e);
        }
        return false;
    }

    /**
     * Copies the specified file out of the plugin and into the plugins folder.
     *
     * @param fileLoc
     * @return if the file is copied, will be false if override is false and the file already existed.
     */
    public boolean copyDefaultFile(String fileLoc, boolean overwrite) {
        return this.copyDefaultFile(fileLoc, fileLoc, overwrite);
    }

    /**
     * Copies the specified file out of the plugin and into the plugins folder.
     *
     * @param sourceLoc - location of the file in the jar
     * @param fileLoc - location to save the file
     * @return if the file is copied, will be false if override is false and the file already existed.
     */
    public boolean copyDefaultFile(String sourceLoc, String fileLoc, boolean overwrite) {
        File outFile = new File(this.dataFolder, fileLoc);
        if (!outFile.exists()) {
            outFile.getParentFile().mkdirs();
        }
        if (!outFile.exists() || overwrite) {
            try {
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(sourceLoc);
                if(inputStream == null) {
                    return false;
                }

                writeToFile(inputStream, outFile);
            } catch (NullPointerException e) {
                e.printStackTrace();
                this.infoLogger.warning("Could not load " + sourceLoc + ". The file does" +
                        "not exist or there has been an error reading the file.");
                return false;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                this.infoLogger.warning("Could not create " + sourceLoc);
            } catch (IOException e) {
                e.printStackTrace();
                this.infoLogger.warning("File error reading " + sourceLoc);
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
                this.infoLogger.warning("Could not load " + location + ". The file does" +
                        "not exist or there has been an error reading the file.");
                return null;
            }
        }
    }

    public void writeResource(InputStream inputStream, String location) {
        File outFile = new File(dataFolder, location);
        try {
            writeToFile(inputStream, outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(InputStream inputStream, File outFile) throws IOException {
        try (FileOutputStream outStream = new FileOutputStream(outFile)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outStream.write(buf, 0, len);
            }
        }
        inputStream.close();
    }

    public boolean fileExists(String name) {
        return new File(this.dataFolder, name).exists();
    }

    /**
     * @param fileLocation
     * @param trimExtension
     * @return
     */
    public List<String> listAllFiles(String fileLocation, boolean trimExtension) {
        File directory = new File(dataFolder, fileLocation);
        var list = new ArrayList<String>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        if (trimExtension) {
                            int i = fileName.lastIndexOf('.');
                            if (i > 0) {
                                fileName = fileName.substring(0, i);
                            }
                        }
                        list.add(fileName);
                    }
                }
            }
        } else {
            infoLogger.warning("Directory does not exist or is not a directory: " + fileLocation);
        }
        return list;
    }

    public boolean deleteFile(String fileLocation) {
        try {
            Files.delete(Paths.get(dataFolder.getAbsolutePath(), fileLocation));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
