package com.nookure.staff.lib;

import com.alessiodp.libby.Library;

import java.util.ArrayList;
import java.util.stream.Stream;

public class DefaultLibRepo {
  private static final DefaultLibRepo INSTANCE = new DefaultLibRepo();

  private final ArrayList<Library> libraries = new ArrayList<>();

  private DefaultLibRepo() {
    Library hikariCP = Library.builder()
        .groupId("com{}zaxxer")
        .artifactId("HikariCP")
        .version("4.0.3")
        .relocate("com{}zaxxer", "com{}nookure{}staff{}libs")
        .isolatedLoad(false)
        .build();

    Library jedis = Library.builder()
        .groupId("redis{}clients")
        .artifactId("jedis")
        .version("4.4.0-m2")
        .isolatedLoad(false)
        .build();

    Library storm = Library.builder()
        .groupId("com{}github{}Mindgamesnl")
        .artifactId("storm")
        .version("e1f961b")
        .isolatedLoad(false)
        .relocate("com{}craftmend{}storm", "com{}nookure{}staff{}libs{}storm")
        .relocate("com{}zaxxer", "com{}nookure{}staff{}libs")
        .build();

    Library caffeine = Library.builder()
        .groupId("com{}github{}ben-manes{}caffeine")
        .artifactId("caffeine")
        .version("3.1.8")
        .isolatedLoad(false)
        .relocate("com{}github{}benmanes{}caffeine", "com{}nookure{}staff{}libs{}caffeine")
        .resolveTransitiveDependencies(true)
        .build();

    Library sqlite = Library.builder()
        .groupId("org{}xerial")
        .artifactId("sqlite-jdbc")
        .version("3.42.0.0")
        .isolatedLoad(false)
        .build();

    Library commons = Library.builder()
        .groupId("org{}apache{}commons")
        .artifactId("commons-lang3")
        .version("3.14.0")
        .isolatedLoad(false)
        .build();

    Library commonsPool2 = Library.builder()
        .groupId("org{}apache{}commons")
        .artifactId("commons-pool2")
        .version("2.12.0")
        .isolatedLoad(false)
        .build();

    Library ebean = Library.builder()
        .groupId("io{}ebean")
        .artifactId("ebean")
        .version("15.1.0")
        .isolatedLoad(false)
        .resolveTransitiveDependencies(true)
        .build();

    Library ebeanMigration = Library.builder()
        .groupId("io{}ebean")
        .artifactId("ebean-migration")
        .version("14.0.0")
        .isolatedLoad(false)
        .resolveTransitiveDependencies(true)
        .build();

    Library ebeanDDLMigration = Library.builder()
        .groupId("io{}ebean")
        .artifactId("ebean-ddl-generator")
        .version("15.1.0")
        .isolatedLoad(false)
        .resolveTransitiveDependencies(true)
        .build();

    Library json = Library.builder()
        .groupId("org{}json")
        .artifactId("json")
        .version("20240303")
        .isolatedLoad(false)
        .build();


    Library obliviateInventories = Library.builder()
        .groupId("com{}github{}hamza-cskn{}obliviate-invs")
        .artifactId("core")
        .version("4.3.0")
        .isolatedLoad(false)
        .resolveTransitiveDependencies(true)
        .build();

    Library obliviateInventoriesPagination = Library.builder()
        .groupId("com{}github{}hamza-cskn{}obliviate-invs")
        .artifactId("pagination")
        .version("4.3.0")
        .isolatedLoad(false)
        .resolveTransitiveDependencies(true)
        .build();

    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      libraries.add(sqlite);
    }

    Stream.of(hikariCP, storm, caffeine, jedis, commons, commonsPool2, json, obliviateInventoriesPagination, obliviateInventories).forEach(libraries::add);

    try {
      Class.forName("io.ebean.Database");
    } catch (ClassNotFoundException e) {
      Stream.of(ebean, ebeanMigration, ebeanDDLMigration).forEach(libraries::add);
    }
  }

  public ArrayList<Library> getLibraries() {
    return libraries;
  }

  public static DefaultLibRepo getInstance() {
    return INSTANCE;
  }

}
