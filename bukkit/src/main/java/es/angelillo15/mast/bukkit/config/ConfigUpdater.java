package es.angelillo15.mast.bukkit.config;

import es.angelillo15.mast.bukkit.MASTBukkitManager;
import es.angelillo15.mast.config.ConfigManager;
import ru.vyarus.yaml.updater.YamlUpdater;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigUpdater {
    public void update() {
        YamlUpdater.create(
                        new File(MASTBukkitManager.getInstance().getDataFolder().toURI() + File.separator + "config.yml"),
                        Objects.requireNonNull(getClass().getResourceAsStream("/config.yml")))
                .update()
        ;

        YamlUpdater.create(
                        new File(MASTBukkitManager.getInstance().getDataFolder().toURI() + File.separator + "lang/spanish.yml"),
                        Objects.requireNonNull(getClass().getResourceAsStream("/lang/spanish.yml")))
                .update()
        ;

        YamlUpdater.create(
                        new File(MASTBukkitManager.getInstance().getDataFolder().toURI() + File.separator + "lang/english.yml"),
                        Objects.requireNonNull(getClass().getResourceAsStream("/lang/english.yml")))
                .update()
        ;

        YamlUpdater.create(
                        new File(MASTBukkitManager.getInstance().getDataFolder().toURI() + File.separator + "modules/internal.yml"),
                        Objects.requireNonNull(getClass().getResourceAsStream("/modules/internal.yml")))
                .update()
        ;

        YamlUpdater.create(
                        new File(MASTBukkitManager.getInstance().getDataFolder().toURI() + File.separator + "modules/glow.yml"),
                        Objects.requireNonNull(getClass().getResourceAsStream("/modules/glow.yml")))
                .update()
        ;

    }
}
