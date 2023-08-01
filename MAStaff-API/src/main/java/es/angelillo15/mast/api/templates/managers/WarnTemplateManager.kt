package es.angelillo15.mast.api.templates.managers

import com.google.inject.Singleton
import es.angelillo15.mast.api.templates.WarnTemplate

/**
 * Manages all the {@link WarnTemplate}s.
 * @since 2.4.0
 */
@Singleton
class WarnTemplateManager {
    private val warnTemplates: MutableMap<String, WarnTemplate> = HashMap()

    /**
     * Adds a {@link WarnTemplate} to the manager.
     */
    fun addWarnTemplate(warnTemplate: WarnTemplate) {
        warnTemplates[warnTemplate.id] = warnTemplate
    }

    /**
     * Gets a {@link WarnTemplate} from the manager.
     * @param id The id of the {@link WarnTemplate}.
     * @return The {@link WarnTemplate} with the specified id.
     */
    fun getWarnTemplate(id: String): WarnTemplate? {
        return warnTemplates[id]
    }

    /**
     * Removes a {@link WarnTemplate} from the manager.
     * @param id The id of the {@link WarnTemplate}.
     */
    fun removeWarnTemplate(id: String) {
        warnTemplates.remove(id)
    }

    /**
     * Returns all the {@link WarnTemplate}s in the manager.
     * @return All the {@link WarnTemplate}s in the manager.
     */
    fun getWarnTemplates(): Collection<WarnTemplate> {
        return warnTemplates.values
    }

    /**
     * Returns all the {@link WarnTemplate}s in the manager.
     * @return All the {@link WarnTemplate}s in the manager as a List.
     */
    fun getWarnTemplatesAsList(): List<WarnTemplate> {
        return ArrayList(warnTemplates.values)
    }
}