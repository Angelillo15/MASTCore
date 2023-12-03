package com.nookure.mast.api.webhook.config;

import com.nookure.mast.api.MAStaff;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class JsonConfig {
  private final InputStream inputStream;
  private final File file;
  private final GsonConfigurationLoader loader;
  private final BasicConfigurationNode node;
  private final String originalJson;

  public JsonConfig(@NotNull String original, @NotNull String target, MAStaff plugin) throws IOException {

    inputStream = plugin.getPluginResource(original);

    file = new File(target);

    if (!file.exists()) {
      file.getParentFile().mkdirs();
      if (file.createNewFile()) {
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(inputStream.readAllBytes());

        outputStream.close();
      } else {
        throw new RuntimeException("Error creating file " + file.getName());
      }
    }

    try {
      inputStream.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    loader = GsonConfigurationLoader.builder()
        .file(file)
        .build();

    node = loader.load();
    originalJson = Files.readString(file.toPath(), StandardCharsets.UTF_8);
  }

  @NotNull
  public InputStream getInputStream() {
    return inputStream;
  }

  @NotNull
  public File getFile() {
    return file;
  }

  @NotNull
  public GsonConfigurationLoader getLoader() {
    return loader;
  }

  @NotNull
  public BasicConfigurationNode getNode() {
    return node;
  }

  @NotNull
  public String toString() {
    return originalJson;
  }
}
