package com.nookure.staff.paper.bootstrap;

import com.google.gson.Gson;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("UnstableApiUsage")
public class StaffPaperPluginLoader implements PluginLoader {
  @Override
  public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
    MavenLibraryResolver resolver = new MavenLibraryResolver();
    resolver.addDependency(new Dependency(new DefaultArtifact("io.ebean:ebean:15.1.0"), null));
    resolver.addDependency(new Dependency(new DefaultArtifact("io.ebean:ebean-migration:14.0.0"), null));
    resolver.addDependency(new Dependency(new DefaultArtifact("io.ebean:ebean-ddl-generator:15.1.0"), null));

    resolver.addRepository(new RemoteRepository.Builder("paper", "default", "https://repo.papermc.io/repository/maven-public/").build());

    PluginLibraries pluginLibraries = load();
    pluginLibraries.asDependencies().forEach(resolver::addDependency);
    pluginLibraries.asRepositories().forEach(resolver::addRepository);

    classpathBuilder.addLibrary(resolver);
  }

  private PluginLibraries load() {
    try (InputStream in = getClass().getResourceAsStream("/paper-libraries.json")) {
      return new Gson().fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), PluginLibraries.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private record PluginLibraries(Map<String, String> repositories, List<String> dependencies) {
    public Stream<Dependency> asDependencies() {
      return dependencies.stream()
          .map(d -> new Dependency(new DefaultArtifact(d), null));
    }

    public Stream<RemoteRepository> asRepositories() {
      return repositories.entrySet().stream()
          .map(e -> new RemoteRepository.Builder(e.getKey(), "default", e.getValue()).build());
    }
  }
}
