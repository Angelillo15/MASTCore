package es.angelillo15.mast.api.punishments.loader

import com.google.inject.Inject
import com.google.inject.Singleton
import es.angelillo15.mast.api.ILogger
import es.angelillo15.mast.api.config.punishments.ConfigLoader
import es.angelillo15.mast.api.templates.BanTemplate
import es.angelillo15.mast.api.templates.WarnAction
import es.angelillo15.mast.api.templates.WarnTemplate
import es.angelillo15.mast.api.templates.managers.BanTemplatesManager
import es.angelillo15.mast.api.templates.managers.WarnTemplateManager
import es.angelillo15.mast.api.utils.NumberUtils
import org.simpleyaml.configuration.file.YamlFile

@Singleton
class TemplateLoaders {
    @Inject
    private lateinit var banTemplateManager: BanTemplatesManager
    @Inject
    private lateinit var warnTemplateManager: WarnTemplateManager
    @Inject
    private lateinit var logger: ILogger

    fun loadAll() {
        logger.debug("Loading all templates...")
        logger.debug("Loading all ban templates...")
        loadBans()
        logger.debug("Loaded all ban templates.")
        logger.debug("Loading all warn templates...")
        loadWarns()
        logger.debug("Loaded all warn templates.")
    }

    private fun loadBans() {
        val banFile = ConfigLoader.getBanTemplate().config;

        banFile.getConfigurationSection("Templates").getKeys(false).forEach { key ->
            banTemplateManager.addBanTemplate(
                BanTemplate(
                    key,
                    NumberUtils.parseToMilis(banFile.getString("Templates.$key.banDuration")),
                    banFile.getString("Templates.$key.banMessage"),
                    banFile.getString("Templates.$key.permission"),
                    banFile.getBoolean("Templates.$key.ipBan")
                )
            )
        }
    }

    private fun loadWarns() {
        val warnFile = ConfigLoader.getWarnsTemplate().config;

        warnFile.getConfigurationSection("Templates").getKeys(false).forEach { key ->
            warnTemplateManager.addWarnTemplate(
                WarnTemplate(
                    key,
                    warnFile.getString("Templates.$key.warnReason"),
                    warnFile.getString("Templates.$key.warnMessage"),
                    warnFile.getInt("Templates.$key.maxWarnings"),
                    getWarningActions(warnFile, key),
                    warnFile.getBoolean("Templates.$key.deleteOnMax"),
                    warnFile.getString("Templates.$key.permission")
                )
            )
        }
    }

    private fun getWarningActions(warnFile: YamlFile, key: String): List<WarnAction> {
        val actions = ArrayList<WarnAction>()
        warnFile.getConfigurationSection("Templates.${key}.actions").getKeys(false).forEach { action ->
            actions.add(
                WarnAction(
                    action.toInt(),
                    warnFile.getString("Templates.${key}.actions.${action}")
                )
            )
        }

        return actions
    }
}