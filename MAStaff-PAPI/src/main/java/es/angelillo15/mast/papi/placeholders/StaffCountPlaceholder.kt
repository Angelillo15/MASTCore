package es.angelillo15.mast.papi.placeholders

import com.google.inject.Inject
import es.angelillo15.mast.api.managers.StaffManager
import es.angelillo15.mast.api.managers.StaffPlayersManagers
import es.angelillo15.mast.papi.Placeholder
import es.angelillo15.mast.papi.PlaceholderData
import org.bukkit.entity.Player

@PlaceholderData(key = "staffcount")
class StaffCountPlaceholder : Placeholder() {
    @Inject
    private var manager: StaffManager? = null;
    override fun onPlaceholderRequest(player: Player?, params: String?): String {
        return manager!!.getStaffPlayers().size.toString()
    }
}