package es.angelillo15.mast.papi.placeholders

import es.angelillo15.mast.api.managers.StaffPlayersManagers
import es.angelillo15.mast.papi.Placeholder
import es.angelillo15.mast.papi.PlaceholderData
import org.bukkit.entity.Player

@PlaceholderData(key = "staffmode")
class StaffModePlaceholder : Placeholder() {
    override fun onPlaceholderRequest(player: Player?, params: String?): String {
        if (!StaffPlayersManagers.isStaffPlayer(player!!)) {
            return "false"
        }

        val staffPlayer = StaffPlayersManagers.getStaffPlayer(player)

        return if (staffPlayer!!.isStaffMode) "true" else "false"
    }
}