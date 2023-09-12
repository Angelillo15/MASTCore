package es.angelillo15.mast.papi.placeholders

import com.google.inject.Inject
import es.angelillo15.mast.api.managers.StaffManager
import es.angelillo15.mast.papi.Placeholder
import es.angelillo15.mast.papi.PlaceholderData
import org.bukkit.entity.Player

@PlaceholderData(key = "staffcount")
class StaffCountPlaceholder : Placeholder() {
  @Inject
  private lateinit var manager: StaffManager;
  override fun onPlaceholderRequest(player: Player?, params: String?): String {
    return manager.staffPlayers.count().toString()
  }
}