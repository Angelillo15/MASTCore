package es.angelillo15.mast.api.addons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddonDescription {
    /**
     * Friendly name of the plugin.
     */
    private String name;
    /**
     * Addon main class. Needs to extend {@link MAStaffAddon}.
     */
    private String main;
    /**
     * Plugin version.
     */
    private String version;
    /**
     * Plugin author.
     */
    private String author;
}
