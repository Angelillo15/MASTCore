package es.angelillo15.mast.api.config.velocity

import com.google.inject.Inject
import com.google.inject.Singleton
import es.angelillo15.configmanager.ConfigManager
import es.angelillo15.mast.api.managers.ConfigMerge
import es.angelillo15.mast.api.utils.MAStaffInject
import java.io.File

@Singleton
class VelocityConfig {
    @Inject
    private lateinit var plugin: MAStaffInject;
    lateinit var config: ConfigManager
        private set
    lateinit var messages: ConfigManager
        private set

    fun load() {
        loadConfig()
        loadLanguages()
        loadMessage()
    }

    private fun loadConfig() {
        ConfigMerge.merge(
            File(plugin.pluginDataFolder.toPath().toString() + File.separator + "config.yml"),
            plugin.getPluginResource("Velocity/config.yml")
        )

        config = ConfigManager(plugin.pluginDataFolder.toPath(), "Velocity/config.yml", "config.yml")
        config.registerConfig()
    }

    private fun loadLanguages() {
        val file = File(plugin.pluginDataFolder.toPath().toString() + File.separator + "lang")

        if (!file.exists()) {
            file.mkdirs()
        }

        val es = ConfigManager(
            plugin.pluginDataFolder.toPath(),
            "BungeeCord/lang/spanish.yml",
            "/lang/spanish.yml"
        )

        val en = ConfigManager(
            plugin.pluginDataFolder.toPath(),
            "BungeeCord/lang/english.yml",
            "/lang/english.yml"
        )

        es.registerConfig()
        en.registerConfig()
    }

    private fun loadMessage() {
        val lang = config.config.getString("Config.language")

        ConfigMerge.merge(
            File(plugin.pluginDataFolder.toPath().toString() + File.separator + "/lang/$lang"),
            plugin.getPluginResource("BungeeCord/lang/$lang")
        )

        messages = ConfigManager(plugin.pluginDataFolder.toPath(), "BungeeCord/lang/$lang", "/lang/$lang")
        messages.registerConfig()
    }
}
