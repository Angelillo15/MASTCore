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
    public static void merge(File file, InputStream Input) {
        YamlUpdater.create(file, Input)
                .update();
    }
}
