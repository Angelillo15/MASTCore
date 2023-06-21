package es.angelillo15.mast.api.managers;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.yaml.updater.YamlUpdater;
import ru.vyarus.yaml.updater.report.UpdateReport;

import java.io.File;
import java.io.InputStream;

public class ConfigMerge {
    public static void loggerSetup() {
        System.setProperty("org.slf4j.simpleLogger.log.ru.vyarus", "none");
    }

    @SneakyThrows
    /**
     * Merges the InputStream into the File
     * @param file The file to merge
     * @param Input The InputStream to merge
     */
    public static void merge(File current, InputStream update) {
        YamlUpdater.create(current, update)
                .update();
    }
}
