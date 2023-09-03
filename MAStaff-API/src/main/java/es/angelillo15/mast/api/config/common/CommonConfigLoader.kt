package es.angelillo15.mast.api.config.common

import com.google.inject.Inject
import com.google.inject.Singleton
import es.angelillo15.configmanager.ConfigManager
import es.angelillo15.mast.api.managers.ConfigMerge
import es.angelillo15.mast.api.utils.MAStaffInject
import java.io.File

@Singleton
class CommonConfigLoader {
    @Inject
    private val plugin: MAStaffInject? = null
    var config: ConfigManager? = null
        private set
    var messages: ConfigManager? = null
        private set

    fun load() {
        loadConfig()
        loadLanguages()
        loadMessage()
    }

    private fun loadConfig() {
        ConfigMerge.merge(
            File(plugin!!.pluginDataFolder.toPath().toString() + File.separator + "common/config.yml"),
            plugin.getPluginResource("common/config.yml")
        )
        config = ConfigManager(plugin.pluginDataFolder.toPath(), "common/config.yml", "common/config.yml")
        config!!.registerConfig()
    }

    private fun loadLanguages() {
        val file = File(plugin!!.pluginDataFolder.toPath().toString() + File.separator + "lang")
        if (!file.exists()) {
            file.mkdirs()
        }
        val es = ConfigManager(
            plugin.pluginDataFolder.toPath(),
            "common/lang/es.yml",
            "/common/lang/es.yml"
        )

        val en = ConfigManager(
            plugin.pluginDataFolder.toPath(),
            "common/lang/en.yml",
            "/common/lang/en.yml"
        )
        es.registerConfig()
        en.registerConfig()
    }

    private fun loadMessage() {
        val lang = config!!.config.getString("Config.language")

        ConfigMerge.merge(
            File(plugin!!.pluginDataFolder.toPath().toString() + File.separator + "common/lang/$lang"),
            plugin.getPluginResource("common/lang/$lang")
        )

        messages = ConfigManager(plugin.pluginDataFolder.toPath(), "common/lang/$lang", "common/lang/$lang")
        messages!!.registerConfig()
    }
}
