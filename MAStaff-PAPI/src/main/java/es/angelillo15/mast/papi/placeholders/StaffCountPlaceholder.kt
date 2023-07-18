package es.angelillo15.mast.papi.placeholders

import es.angelillo15.mast.api.managers.StaffPlayersManagers
import es.angelillo15.mast.papi.Placeholder
import es.angelillo15.mast.papi.PlaceholderData
import org.bukkit.entity.Player

@PlaceholderData(key = "staffcount")
class StaffCountPlaceholder : Placeholder() {
    override fun onPlaceholderRequest(player: Player?, params: String?): String {
        return StaffPlayersManagers.getStaffPlayers().size.toString()
    }
}