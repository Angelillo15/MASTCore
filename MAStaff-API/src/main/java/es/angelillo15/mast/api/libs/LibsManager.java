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
                .version("5.0.1")
                .id("hikariCP")
                .relocate("com{}zaxxer", "es{}angelillo15{}mast{}libs")
                .relocate("org{}slf4j", "es{}angelillo15{}mast{}libs{}slf4j")
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
                        .relocate("org{}slf4j", "es{}angelillo15{}mast{}libs{}slf4j")
                        .relocate("org{}yaml{}snakeyaml", "es{}angelillo15{}mast{}libs{}snakeyaml")
                        .build();

        libs.add(hikariCP);
        libs.add(guava);
        libs.add(yamlMerge);
        libs.add(getLib("org{}slf4j", "slf4j-api", "2.0.6", "es{}angelillo15{}mast{}libs{}slf4j"));
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