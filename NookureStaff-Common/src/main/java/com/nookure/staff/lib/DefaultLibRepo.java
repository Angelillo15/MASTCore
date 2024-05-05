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

    Library jboss = Library.builder()
        .groupId("org{}jboss{}logging")
        .artifactId("jboss-logging")
        .version("3.5.3.Final")
        .isolatedLoad(false)
        .build();

    Library jakartaPersistence = Library.builder()
        .groupId("jakarta{}persistence")
        .artifactId("jakarta.persistence-api")
        .version("3.1.0")
        .isolatedLoad(false)
        .build();

    Library jakartaTransaction = Library.builder()
        .groupId("jakarta{}transaction")
        .artifactId("jakarta.transaction-api")
        .version("2.0.1")
        .isolatedLoad(false)
        .build();

    Library jakartaXML = Library.builder()
        .groupId("jakarta{}xml.bind")
        .artifactId("jakarta.xml.bind-api")
        .version("4.0.2")
        .isolatedLoad(false)
        .build();

    Library hibernateCommonsAnnotations = Library.builder()
        .groupId("org{}hibernate{}common")
        .artifactId("hibernate-commons-annotations")
        .version("6.0.6.Final")
        .relocate("org{}hibernate", "com{}nookure{}staff{}libs{}hibernate")
        .isolatedLoad(false)
        .build();

    Library hibernate = Library.builder()
        .groupId("org{}hibernate{}orm")
        .artifactId("hibernate-core")
        .version("6.5.0.Final")
        .relocate("org{}hibernate", "com{}nookure{}staff{}libs{}hibernate")
        .isolatedLoad(false)
        .build();

    Library jackson = Library.builder()
        .groupId("com{}fasterxml.jackson.core")
        .artifactId("jackson-databind")
        .version("2.17.0")
        .isolatedLoad(false)
        .build();

    Library classmate = Library.builder()
        .groupId("com{}fasterxml")
        .artifactId("classmate")
        .version("1.7.0")
        .isolatedLoad(false)
        .build();

    Library hibernateCommunityDialects = Library.builder()
        .groupId("org{}hibernate{}orm")
        .artifactId("hibernate-community-dialects")
        .relocate("org{}hibernate", "com{}nookure{}staff{}libs{}hibernate")
        .version("6.5.0.Final")
        .isolatedLoad(false)
        .build();

    Library bytebuddy = Library.builder()
        .groupId("net{}bytebuddy")
        .artifactId("byte-buddy")
        .version("1.14.14")
        .isolatedLoad(false)
        .build();

    Library antlr = Library.builder()
        .groupId("org{}antlr")
        .artifactId("antlr4")
        .version("4.13.1")
        .isolatedLoad(false)
        .build();

    Library antlrRuntime = Library.builder()
        .groupId("org{}antlr")
        .artifactId("antlr4-runtime")
        .version("4.13.1")
        .isolatedLoad(false)
        .build();

    Library jandex = Library.builder()
        .groupId("io{}smallrye")
        .artifactId("jandex")
        .version("3.1.7")
        .isolatedLoad(false)
        .build();

    Library hibernateHikariCP = Library.builder()
        .groupId("org{}hibernate{}orm")
        .artifactId("hibernate-hikaricp")
        .relocate("org{}hibernate", "com{}nookure{}staff{}libs{}hibernate")
        .relocate("com{}zaxxer", "com{}nookure{}staff{}libs")
        .version("6.5.0.Final")
        .isolatedLoad(false)
        .build();

    Library mariadb = Library.builder()
        .groupId("org{}mariadb{}jdbc")
        .artifactId("mariadb-java-client")
        .version("3.3.3")
        .isolatedLoad(false)
        .build();

    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      libraries.add(sqlite);
    }

    Stream.of(
        jakartaPersistence,
        jakartaTransaction,
        jakartaXML,
        classmate,
        jackson,
        hibernateCommonsAnnotations,
        jboss,
        hikariCP,
        storm,
        caffeine,
        jedis,
        commons,
        commonsPool2,
        hibernateCommunityDialects,
        hibernate,
        bytebuddy,
        antlr,
        antlrRuntime,
        jandex,
        hibernateHikariCP,
        mariadb
    ).forEach(libraries::add);
  }

  public ArrayList<Library> getLibraries() {
    return libraries;
  }

  public static DefaultLibRepo getInstance() {
    return INSTANCE;
  }

}
