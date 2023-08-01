package es.angelillo15.mast.api.libs;

import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.utils.ServerUtils;
import es.angelillo15.mast.api.utils.VersionUtils;
import lombok.Getter;
import net.byteflux.libby.Library;

import java.util.ArrayList;

public class LibsManager {
    @Getter
    public static ArrayList<Library> libs = new ArrayList<Library>();

    public static String ADVENTURE_VERSION = "4.14.0";

    public static void load() {
        Library hikariCP = Library.builder()
                .groupId("com{}zaxxer")
                .artifactId("HikariCP")
                .version("4.0.3")
                .id("hikariCP")
                .relocate("com{}zaxxer", "es{}angelillo15{}mast{}libs")
                .isolatedLoad(false)
                .build();

        Library guava = Library.builder()
                .groupId("com{}google{}guava")
                .artifactId("guava")
                .version("31.1-jre")
                .isolatedLoad(false)
                .relocate("com{}google{}common", "es{}angelillo15{}mast{}libs{}google{}common")
                .relocate("com{}google{}errorprone", "es{}angelillo15{}mast{}libs{}google{}errorprone")
                .relocate("com{}google{}thirdparty", "es{}angelillo15{}mast{}libs{}google{}thirdparty")
                .build();

        Library yamlMerge = Library.builder()
                .groupId("ru{}vyarus")
                .artifactId("yaml-config-updater")
                .version("1.4.2")
                .isolatedLoad(false)
                .relocate("ru{}vyarus{}yaml{}updater", "es{}angelillo15{}mast{}libs{}yaml-config-updater")
                //.relocate("org{}slf4j", "es{}angelillo15{}mast{}libs{}slf4j")
                .relocate("org{}yaml{}snakeyaml", "es{}angelillo15{}mast{}libs{}snakeyaml")
                .build();

        Library unirest = Library.builder()
                .groupId("com.konghq")
                .artifactId("unirest-java")
                .version("3.14.1")
                .isolatedLoad(false)
                .relocate("kong{}unirest", "es{}angelillo15{}mast{}libs{}unirest")
                .relocate("org{}apache{}http", "es{}angelillo15{}mast{}libs{}http")
                //.relocate("org{}apache{}commons{}logging", "es{}angelillo15{}mast{}libs{}commons-logging")
                .build();

        Library apacheHttp = Library.builder()
                .groupId("org{}apache{}httpcomponents")
                .artifactId("httpcore")
                .version("4.4.16")
                .isolatedLoad(false)
                .relocate("org{}apache{}http", "es{}angelillo15{}mast{}libs{}http")
                //.relocate("org{}apache{}commons{}logging", "es{}angelillo15{}mast{}libs{}commons-logging")
                .build();

        Library apacheClient = Library.builder()
                .groupId("org{}apache{}httpcomponents")
                .artifactId("httpclient")
                .version("4.5.14")
                .isolatedLoad(false)
                .relocate("org{}apache{}http", "es{}angelillo15{}mast{}libs{}http")
                //.relocate("org{}apache{}commons{}logging", "es{}angelillo15{}mast{}libs{}commons-logging")
                .build();

        Library apacheHttpNio = Library.builder()
                .groupId("org{}apache{}httpcomponents")
                .artifactId("httpcore-nio")
                .version("4.4.16")
                .isolatedLoad(false)
                .relocate("org{}apache{}http", "es{}angelillo15{}mast{}libs{}http")
                //.relocate("org{}apache{}commons{}logging", "es{}angelillo15{}mast{}libs{}commons-logging")
                .build();

        Library apacheHttpMime = Library.builder()
                .groupId("org{}apache{}httpcomponents")
                .artifactId("httpmime")
                .version("4.5.14")
                .isolatedLoad(false)
                .relocate("org{}apache{}http", "es{}angelillo15{}mast{}libs{}http")
                //.relocate("org{}apache{}commons{}logging", "es{}angelillo15{}mast{}libs{}commons-logging")
                .build();

        Library apacheLogger = Library.builder()
                .groupId("commons-logging")
                .artifactId("commons-logging")
                .version("1.2")
                .isolatedLoad(false)
                //.relocate("org{}apache{}commons{}logging", "es{}angelillo15{}mast{}libs{}commons-logging")
                .build();
        Library jedis = Library.builder()
                .groupId("redis{}clients")
                .artifactId("jedis")
                .version("4.4.0-m2")
                .isolatedLoad(false)
                .relocate("redis{}clients{}jedis", "es{}angelillo15{}mast{}libs{}jedis")
                .build();

        Library storm = Library.builder()
                .groupId("com{}github{}Mindgamesnl")
                .artifactId("storm")
                .version("prod125")
                .isolatedLoad(false)
                .relocate("com{}craftmend{}storm", "es{}angelillo15{}mast{}libs{}storm")
                .relocate("com{}google{}gson", "es{}angelillo15{}mast{}libs{}google{}gson")
                .build();

        Library miniMessage = Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-text-minimessage")
                .version(ADVENTURE_VERSION)
                .isolatedLoad(false)
                .build();

        Library adventureAPI = Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-api")
                .version(ADVENTURE_VERSION)
                .isolatedLoad(false)
                .build();

        Library adventureBukkitPlatform = Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-platform-bukkit")
                .version("4.3.0")
                .isolatedLoad(false)
                .build();

        Library adventureBungeePlatform = Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-platform-bungeecord")
                .version("4.3.0")
                .isolatedLoad(false)
                .build();

        Library examination = Library.builder()
                .groupId("net{}kyori")
                .artifactId("examination-api")
                .version("1.3.0")
                .isolatedLoad(false)
                .build();

        Library adventurePlatformApi = Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-platform-api")
                .version("4.3.0")
                .isolatedLoad(false)
                .build();

        Library adventurePlatformFacet = Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-platform-facet")
                .version("4.3.0")
                .isolatedLoad(false)
                .build();

        Library adventureKey = Library.builder()
                .groupId("net{}kyori")
                .artifactId("adventure-key")
                .version(ADVENTURE_VERSION)
                .isolatedLoad(false)
                .build();

        Library caffeine = Library.builder()
                .groupId("com{}github{}ben-manes{}caffeine")
                .artifactId("caffeine")
                .version("2.9.3")
                .isolatedLoad(false)
                .relocate("com{}github{}benmanes{}caffeine", "es{}angelillo15{}mast{}libs{}caffeine")
                .build();

        Library gson = Library.builder()
                .groupId("com{}google{}code{}gson")
                .artifactId("gson")
                .version("2.10.1")
                .isolatedLoad(false)
                .relocate("com{}google{}gson", "es{}angelillo15{}mast{}libs{}google{}gson")
                .relocate("com{}google{}common", "es{}angelillo15{}mast{}libs{}google{}common")
                .relocate("com{}google{}errorprone", "es{}angelillo15{}mast{}libs{}google{}errorprone")
                .relocate("com{}google{}thirdparty", "es{}angelillo15{}mast{}libs{}google{}thirdparty")
                .build();

        Library snakeYAML = Library.builder()
                .groupId("org{}yaml")
                .artifactId("snakeyaml")
                .version("2.0")
                .isolatedLoad(false)
                .relocate("org{}yaml{}snakeyaml", "es{}angelillo15{}mast{}libs{}snakeyaml")
                .build();

        Library simpleYAML = Library.builder()
                .groupId("com{}github{}Carleslc{}Simple-YAML")
                .artifactId("Simple-Yaml")
                .version("1.8.4")
                .isolatedLoad(false)
                .relocate("org{}yaml{}snakeyaml", "es{}angelillo15{}mast{}libs{}snakeyaml")
                .relocate("org{}simpleyaml", "es{}angelillo15{}mast{}libs{}simpleyaml")
                .build();

        Library configManger = Library.builder()
                .groupId("com{}github{}Angelillo15")
                .artifactId("ConfigManager")
                .version("1.4")
                .isolatedLoad(false)
                .relocate("org{}yaml{}snakeyaml", "es{}angelillo15{}mast{}libs{}snakeyaml")
                .relocate("org{}simpleyaml", "es{}angelillo15{}mast{}libs{}simpleyaml")
                .relocate("es{}angelillo15{}configmanager", "es{}angelillo15{}mast{}libs{}config{}manager")
                .build();

        Library sqlite = Library.builder()
                .groupId("org{}xerial")
                .artifactId("sqlite-jdbc")
                .version("3.42.0.0")
                .isolatedLoad(false)
                .build();

        Library googleGuice = Library.builder()
                .groupId("com{}google{}inject")
                .artifactId("guice")
                .version("7.0.0")
                .relocate("com{}google{}common", "es{}angelillo15{}mast{}libs{}google{}common")
                .relocate("com{}google{}errorprone", "es{}angelillo15{}mast{}libs{}google{}errorprone")
                .relocate("com{}google{}thirdparty", "es{}angelillo15{}mast{}libs{}google{}thirdparty")
                .isolatedLoad(false)
                .build();

        Library jakartaInjectTck = Library.builder()
                .groupId("jakarta{}inject")
                .artifactId("jakarta.inject-tck")
                .version("2.0.1")
                .isolatedLoad(false)
                .build();

        Library jakartaInjectApi = Library.builder()
                .groupId("jakarta{}inject")
                .artifactId("jakarta.inject-api")
                .version("2.0.1")
                .isolatedLoad(false)
                .build();

        Library aopalliance = Library.builder()
                .groupId("aopalliance")
                .artifactId("aopalliance")
                .version("1.0")
                .isolatedLoad(false)
                .build();

        Library guavaFailureAccess = Library.builder()
                .groupId("com{}google{}guava")
                .artifactId("failureaccess")
                .version("1.0")
                .relocate("com{}google{}common", "es{}angelillo15{}mast{}libs{}google{}common")
                .relocate("com{}google{}errorprone", "es{}angelillo15{}mast{}libs{}google{}errorprone")
                .relocate("com{}google{}thirdparty", "es{}angelillo15{}mast{}libs{}google{}thirdparty")
                .isolatedLoad(false)
                .build();


        libs.add(getKotlinLib("kotlin-stdlib-jdk8"));
        libs.add(getKotlinLib("kotlin-stdlib-jdk7"));
        libs.add(getKotlinLib("kotlin-stdlib"));
        libs.add(getKotlinLib("kotlin-stdlib-common"));
        libs.add(getKotlinLib("kotlin-stdlib-js"));
        libs.add(getKotlinLib("kotlin-reflect"));

        libs.add(getAdventureLib("adventure-text-serializer-legacy"));
        libs.add(getAdventureLib("adventure-text-serializer-plain"));
        libs.add(getAdventureLib("adventure-text-serializer-json"));
        libs.add(getAdventureLib("adventure-text-serializer-ansi"));
        libs.add(getAdventureLib("adventure-text-serializer-gson"));
        libs.add(getAdventureLib("adventure-nbt"));
        libs.add(getAdventureLib("adventure-text-serializer-json-legacy-impl"));
        libs.add(getAdventureLib("adventure-text-serializer-gson-legacy-impl"));


        libs.add(miniMessage);
        libs.add(adventureAPI);
        libs.add(examination);
        libs.add(adventurePlatformApi);
        libs.add(adventurePlatformFacet);
        libs.add(adventureKey);

        if (ServerUtils.getServerType() == ServerUtils.ServerType.BUKKIT) {
            libs.add(adventureBukkitPlatform);
        } else if (ServerUtils.getServerType() == ServerUtils.ServerType.BUNGEE) {
            libs.add(adventureBungeePlatform);
        }

        libs.add(apacheHttp);
        libs.add(hikariCP);
        libs.add(guava);
        libs.add(yamlMerge);
        libs.add(unirest);
        libs.add(apacheHttpNio);
        libs.add(apacheClient);
        libs.add(apacheHttpMime);
        libs.add(apacheLogger);
        libs.add(jedis);
        libs.add(storm);
        libs.add(caffeine);
        libs.add(gson);
        libs.add(snakeYAML);
        libs.add(simpleYAML);
        libs.add(configManger);
        libs.add(getLib("org{}slf4j", "slf4j-api", "2.0.6"));

        if (ServerUtils.getServerType() == ServerUtils.ServerType.VELOCITY) {
            libs.add(sqlite);
        } else {
            libs.add(aopalliance);
            libs.add(googleGuice);
            libs.add(jakartaInjectApi);
            libs.add(jakartaInjectTck);
            libs.add(guavaFailureAccess);
        }
    }

    /**
     * Get a library from Maven Central or JitPack
     *
     * @param groupID  The group ID of the library
     * @param artifact The artifact ID of the library
     * @param version  The version of the library
     * @param relocate The package to relocate the library to
     * @return The library
     */
    public static Library getLib(String groupID, String artifact, String version, String relocate) {
        Library lib = Library.builder()
                .groupId(groupID)
                .artifactId(artifact)
                .version(version)
                .isolatedLoad(false)
                .relocate(groupID, relocate)
                .build();
        return lib;
    }

    /**
     * @param groupID  The groupID of the library
     * @param artifact The artifact of the library
     * @param version  The version of the library
     * @return The library
     */
    public static Library getLib(String groupID, String artifact, String version) {
        return Library.builder()
                .groupId(groupID)
                .artifactId(artifact)
                .version(version)
                .isolatedLoad(false)
                .build();
    }

    /**
     * Get a library by its ID
     *
     * @param lib        The ID of the library
     * @param relocation The relocation of the library
     * @return The library
     */
    public static Library getLib(String lib, String relocation) {
        String[] libInfo = lib.split(":");
        return getLib(
                libInfo[0],
                libInfo[1],
                libInfo[2],
                relocation
        );
    }

    public static Library getAdventureLib(String artifact) {
        return Library.builder()
                .groupId("net{}kyori")
                .artifactId(artifact)
                .version(ADVENTURE_VERSION)
                .isolatedLoad(false)
                .build();
    }

    public static Library getKotlinLib(String artifact) {
        return Library.builder()
                .groupId("org{}jetbrains{}kotlin")
                .artifactId(artifact)
                .version("1.9.0")
                .isolatedLoad(false)
                .build();
    }
}