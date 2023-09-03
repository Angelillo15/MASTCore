package es.angelillo15.mast.api.addons.annotations;

public @interface Addon {
    String name();
    String version();
    String author();
    String description() default "";
    boolean loadOnScan() default true;
    AddonPlatform platform();

    enum AddonPlatform {
        BUKKIT,
        VELOCITY,
        BUNGEECORD,
    }
}
