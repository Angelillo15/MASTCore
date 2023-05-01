package es.angelillo15.mast.api.libs;

import lombok.Getter;
import net.byteflux.libby.Library;

import java.util.ArrayList;

public class LibsManager {
    @Getter
    public static ArrayList<Library> libs = new ArrayList<Library>();

    public static void load(){
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
                        .relocate("com{}google{}gson", "es{}angelillo15{}mast{}libs{}gson")
                        .build();


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
        libs.add(getLib("org{}slf4j", "slf4j-api", "2.0.6"));
    }

    /**
     * Get a library from Maven Central or JitPack
     * @param groupID The group ID of the library
     * @param artifact The artifact ID of the library
     * @param version The version of the library
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
     * @param groupID The groupID of the library
     * @param artifact The artifact of the library
     * @param version The version of the library
     * @return The library
     */
    public static Library getLib(String groupID, String artifact, String version) {
        Library lib = Library.builder()
                .groupId(groupID)
                .artifactId(artifact)
                .version(version)
                .isolatedLoad(false)
                .build();
        return lib;
    }

    /**
     * Get a library by its ID
     * @param lib The ID of the library
     * @param relocation The relocation of the library
     * @return The library
     */
    public static Library getLib(String lib, String relocation){
        String[] libInfo = lib.split(":");
        Library library = getLib(
                libInfo[0],
                libInfo[1],
                libInfo[2],
                relocation
        );
        return library;
    }
}