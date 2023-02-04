package es.angelillo15.mast.api.addons.config;


import es.angelillo15.mast.api.addons.MAStaffAddon;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;


public class ConfigManager {

    private YamlFile yamlFile;

    private Path dataDirectoryPath;
    private String originalFileName;
    private String newFileName;
    private MAStaffAddon addon;

    public ConfigManager(Path dataDirectoryPath, String originalFileName, String newFileName, MAStaffAddon addon) {
        this.dataDirectoryPath = dataDirectoryPath;
        this.originalFileName = originalFileName;
        this.newFileName = newFileName;
        this.addon = addon;
    }

    public void registerConfig() {
        String path = dataDirectoryPath+File.separator+newFileName;
        createDefaultConfigFile();

        yamlFile = new YamlFile(path);
        try {
            if (!yamlFile.exists()) {
                yamlFile.createOrLoad();
            }
            yamlFile.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDefaultConfigFile(){
        File dataDirectoryFile = new File(dataDirectoryPath.toString());
        if (!dataDirectoryFile.exists()){
            dataDirectoryFile.mkdir();
        }
        File configFile = new File(dataDirectoryFile, newFileName);
        try {
            if (!configFile.exists()){
                configFile.mkdirs();
                Files.copy(Objects.requireNonNull(addon.getClass().getClassLoader()
                        .getResourceAsStream(originalFileName))
                        , configFile.toPath()
                        , StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlFile getConfig() {
        return yamlFile;
    }

}
