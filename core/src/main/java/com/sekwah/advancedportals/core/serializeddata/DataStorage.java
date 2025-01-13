package com.sekwah.advancedportals.core.serializeddata;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.util.InfoLogger;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.inspector.TagInspector;
import org.yaml.snakeyaml.nodes.Tag;

public class DataStorage {
    private final File dataFolder;

    @Inject
    private AdvancedPortalsCore portalsCore;

    @Inject
    private InfoLogger infoLogger;

    public DataStorage(File dataStorageLoc) {
        this.dataFolder = dataStorageLoc;
    }

    private Yaml getYaml(Class<? extends Object> clazz) {
        LoaderOptions loaderOptions = new LoaderOptions();

        TagInspector tagInspector =
            tag -> tag.getClassName().equals(clazz.getName());

        loaderOptions.setTagInspector(tagInspector);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        ReflectiveRepresenter representer = new ReflectiveRepresenter(options);
        representer.addClassTag(clazz, Tag.MAP);
        representer.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        ReflectiveConstructor constructor =
            new ReflectiveConstructor(clazz, loaderOptions);

        AdvancedPortalsCore.getInstance()
            .getModule()
            .getInjector()
            .injectMembers(constructor);

        return new Yaml(constructor, representer);
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
            } catch (InstantiationException | IllegalAccessException
                     | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
        Yaml yaml = getYaml(dataHolder);
        try (BufferedReader bufReader =
                 new BufferedReader(new InputStreamReader(yamlResource))) {
            return yaml.loadAs(bufReader, dataHolder);
        } catch (Exception e) {
            infoLogger.warning("Failed to load file: " + location);
            infoLogger.error(e);
            return null;
        }
    }

    public boolean storeFile(Object dataHolder, String location) {
        Yaml yaml = getYaml(dataHolder.getClass());
        File outFile = new File(this.dataFolder, location);
        if (!outFile.getParentFile().exists()
            && !outFile.getParentFile().mkdirs()) {
            infoLogger.warning("Failed to create folder for file: " + location);
        }

        String yamlFile = yaml.dump(dataHolder);
        try (FileWriter fileWriter = new FileWriter(outFile)) {
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
     * @return if the file is copied, will be false if override is false and the
     *     file already existed.
     */
    public boolean copyDefaultFile(String fileLoc, boolean overwrite) {
        return this.copyDefaultFile(fileLoc, fileLoc, overwrite);
    }

    /**
     * Copies the specified file out of the plugin and into the plugins folder.
     *
     * @param sourceLoc - location of the file in the jar
     * @param fileLoc - location to save the file
     * @return if the file is copied, will be false if override is false and the
     *     file already existed.
     */
    public boolean copyDefaultFile(String sourceLoc, String fileLoc,
                                   boolean overwrite) {
        File outFile = new File(this.dataFolder, fileLoc);
        if (!outFile.exists()) {
            outFile.getParentFile().mkdirs();
        }
        if (!outFile.exists() || overwrite) {
            try {
                InputStream inputStream =
                    this.getClass().getClassLoader().getResourceAsStream(
                        sourceLoc);
                if (inputStream == null) {
                    return false;
                }

                writeToFile(inputStream, outFile);
            } catch (NullPointerException e) {
                e.printStackTrace();
                this.infoLogger.warning("Could not load " + sourceLoc
                                        + ". The file does"
                                        + ("not exist or there has been an "
                                           + "error reading the file."));
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
     * A method to try to grab the files from the plugin and if its in the
     * plugin folder load from there instead.
     *
     * <p>
     *
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
                return this.getClass().getClassLoader().getResourceAsStream(
                    location);
            } catch (NullPointerException e) {
                e.printStackTrace();
                this.infoLogger.warning("Could not load " + location
                                        + ". The file does"
                                        + ("not exist or there has been an "
                                           + "error reading the file."));
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

    private void writeToFile(InputStream inputStream, File outFile)
        throws IOException {
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

    public List<String> listAllFiles(String fileLocation,
                                     boolean trimExtension) {
        return listAllFiles(fileLocation, trimExtension, null);
    }

    /**
     * @param fileLocation - location of the folder to list
     * @param trimExtension - if true will remove the file extension
     * @param extension - if null will not filter by extension
     * @return
     */
    public List<String> listAllFiles(String fileLocation, boolean trimExtension,
                                     String extension) {
        File directory = new File(dataFolder, fileLocation);
        List<String> list = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        boolean extensionMatches =
                            (extension == null
                             || fileName.endsWith("." + extension));

                        if (extensionMatches) {
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
            }
        }
        //        } else {
        //            infoLogger.warning(
        //                "Directory does not exist or is not a directory: "
        //                + fileLocation);
        //        }
        return list;
    }

    public boolean deleteFile(String fileLocation) {
        try {
            Files.delete(Paths.get(dataFolder.getAbsolutePath(), fileLocation));
            return true;
        } catch (IOException e) {
            infoLogger.error(e);
            return false;
        }
    }
}
