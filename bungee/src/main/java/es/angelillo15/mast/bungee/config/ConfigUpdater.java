package es.angelillo15.mast.bungee.config;

import es.angelillo15.mast.bungee.MASTBungeeManager;
import es.angelillo15.mast.config.ConfigManager;
import org.simpleyaml.configuration.file.YamlFile;
import ru.vyarus.yaml.updater.YamlUpdater;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigUpdater {
    public void update() {
        YamlUpdater.create(
                        new File(MASTBungeeManager.getInstance().getDataFolder() + File.separator + "config.yml"),
                        Objects.requireNonNull(getClass().getResourceAsStream("/BungeeCord/config.yml")))
                .update()
        ;

        YamlUpdater.create(
                new File(MASTBungeeManager.getInstance().getDataFolder() + File.separator + "BungeeCord/lang/spanish.yml"),
                Objects.requireNonNull(getClass().getResourceAsStream("/BungeeCord/lang/spanish.yml")))
                .update()
        ;

        YamlUpdater.create(
                new File(MASTBungeeManager.getInstance().getDataFolder() + File.separator + "BungeeCord/lang/english.yml"),
                Objects.requireNonNull(getClass().getResourceAsStream("/BungeeCord/lang/english.yml")))
                .update()
        ;
    }
}