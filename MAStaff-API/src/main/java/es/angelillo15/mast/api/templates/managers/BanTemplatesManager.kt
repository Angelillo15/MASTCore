package es.angelillo15.mast.api.templates.managers

import com.google.inject.Singleton
import es.angelillo15.mast.api.templates.BanTemplate

/**
 * Manages all the {@link BanTemplate}s.
 * @since 2.4.0
 */
@Singleton
class BanTemplatesManager {
  private val banTemplates: MutableMap<String, BanTemplate> = HashMap()

  /**
   * Adds a {@link BanTemplate} to the manager.
   */
  fun addBanTemplate(banTemplate: BanTemplate) {
    banTemplates[banTemplate.id] = banTemplate
  }

  /**
   * Removes a {@link BanTemplate} from the manager.
   */
  fun getBanTemplate(id: String): BanTemplate? {
    return banTemplates[id]
  }

  /**
   * Removes a {@link BanTemplate} from the manager.
   */
  fun removeBanTemplate(id: String) {
    banTemplates.remove(id)
  }

  /**
   * Returns all the {@link BanTemplate}s in the manager.
   */
  fun getBanTemplates(): Collection<BanTemplate> {
    return banTemplates.values
  }

  /**
   * Returns all the {@link BanTemplate}s in the manager.
   */
  fun getBanTemplatesAsList(): List<BanTemplate> {
    return ArrayList(banTemplates.values)
  }
}
