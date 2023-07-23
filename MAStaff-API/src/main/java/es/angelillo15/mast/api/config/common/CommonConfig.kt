package es.angelillo15.mast.api.config.common

import com.google.inject.Inject
import es.angelillo15.configmanager.ConfigManager
import es.angelillo15.mast.api.managers.ConfigMerge
import es.angelillo15.mast.api.utils.MAStaffInject
import java.io.File

class CommonConfig {
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
            File(plugin!!.pluginDataFolder.toPath().toString() + File.separator + "Common/config.yml"),
            plugin.getPluginResource("common/config.yml")
        )
        config = ConfigManager(plugin.pluginDataFolder.toPath(), "common/config.yml", "Common/config.yml")
        config!!.registerConfig()
    }

    private fun loadLanguages() {
        val file = File(plugin!!.pluginDataFolder.toPath().toString() + File.separator + "lang")
        if (!file.exists()) {
            file.mkdirs()
        }
        val es = ConfigManager(
            plugin.pluginDataFolder.toPath(),
            "common/lang/spanish.yml",
            "/Common/lang/spanish.yml"
        )

        val en = ConfigManager(
            plugin.pluginDataFolder.toPath(),
            "common/lang/english.yml",
            "/Common/lang/english.yml"
        )
        es.registerConfig()
        en.registerConfig()
    }

    private fun loadMessage() {
        val lang = config!!.config.getString("lang")

        ConfigMerge.merge(
            File(plugin!!.pluginDataFolder.toPath().toString() + File.separator + "Common/lang/$lang.yml"),
            plugin.getPluginResource("common/lang/$lang.yml")
        )

        messages = ConfigManager(plugin.pluginDataFolder.toPath(), "Common/lang/$lang.yml", "Common/lang/$lang.yml")
        messages!!.registerConfig()
    }
}
