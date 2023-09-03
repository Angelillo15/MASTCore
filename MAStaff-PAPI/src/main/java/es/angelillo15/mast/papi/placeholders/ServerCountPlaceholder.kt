package es.angelillo15.mast.papi.placeholders

import com.google.inject.Inject
import es.angelillo15.mast.api.managers.StaffManager
import es.angelillo15.mast.papi.Placeholder
import es.angelillo15.mast.papi.PlaceholderData
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@PlaceholderData(key = "servercount")
class ServerCountPlaceholder : Placeholder() {
  @Inject
  private var manager: StaffManager? = null;
  override fun onPlaceholderRequest(player: Player?, params: String?): String {
    val serverCount = Bukkit.getOnlinePlayers().size

    var staffVanishedCount = 0

    manager!!.staffPlayers.forEach { (key, staff) ->
      if (!staff.player.isOnline) return@forEach

      if (staff.isVanished) staffVanishedCount++
    }

    return (serverCount - staffVanishedCount).toString()
  }
}