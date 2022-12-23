package es.angelillo15.mast.bukkit.config;

import es.angelillo15.mast.bukkit.MAStaffBukkitManager;
import ru.vyarus.yaml.updater.YamlUpdater;

import java.io.File;
import java.util.Objects;

public class ConfigUpdater {
    public void update() {
        YamlUpdater.create(
                        new File(MAStaffBukkitManager.getInstance().getDataFolder().toURI() + File.separator + "config.yml"),
                        Objects.requireNonNull(getClass().getResourceAsStream("/config.yml")))
                .update()
        ;

        YamlUpdater.create(
                        new File(MAStaffBukkitManager.getInstance().getDataFolder().toURI() + File.separator + "lang/spanish.yml"),
                        Objects.requireNonNull(getClass().getResourceAsStream("/lang/spanish.yml")))
                .update()
        ;

        YamlUpdater.create(
                        new File(MAStaffBukkitManager.getInstance().getDataFolder().toURI() + File.separator + "lang/english.yml"),
                        Objects.requireNonNull(getClass().getResourceAsStream("/lang/english.yml")))
                .update()
        ;

        YamlUpdater.create(
                        new File(MAStaffBukkitManager.getInstance().getDataFolder().toURI() + File.separator + "modules/internal.yml"),
                        Objects.requireNonNull(getClass().getResourceAsStream("/modules/internal.yml")))
                .update()
        ;

        YamlUpdater.create(
                        new File(MAStaffBukkitManager.getInstance().getDataFolder().toURI() + File.separator + "modules/glow.yml"),
                        Objects.requireNonNull(getClass().getResourceAsStream("/modules/glow.yml")))
                .update()
        ;

    }
}
