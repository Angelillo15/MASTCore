package es.angelillo15.mast.papi.placeholders

import com.google.inject.Inject
import es.angelillo15.mast.api.managers.StaffManager
import es.angelillo15.mast.papi.Placeholder
import es.angelillo15.mast.papi.PlaceholderData
import org.bukkit.entity.Player

@PlaceholderData(key = "staffmode")
class StaffModePlaceholder : Placeholder() {
  @Inject
  private var manager: StaffManager? = null;

  override fun onPlaceholderRequest(player: Player?, params: String?): String {
    if (!manager!!.isStaffPlayer(player!!)) {
      return "false"
    }

    val staffPlayer = manager!!.getStaffPlayer(player)

    return if (staffPlayer!!.isStaffMode) "true" else "false"
  }
}