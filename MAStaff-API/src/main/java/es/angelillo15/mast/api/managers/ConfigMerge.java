package es.angelillo15.mast.api.managers;

import lombok.SneakyThrows;
import ru.vyarus.yaml.updater.YamlUpdater;
import ru.vyarus.yaml.updater.report.UpdateReport;

import java.io.File;
import java.io.InputStream;

public class ConfigMerge {
    @SneakyThrows
    public static void merge(File file, InputStream Input) {
        YamlUpdater.create(file, Input)
                .update();
    }
}
