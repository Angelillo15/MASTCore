package es.angelillo15.mast.papi.placeholders

import es.angelillo15.mast.api.managers.StaffPlayersManagers
import es.angelillo15.mast.papi.Placeholder
import es.angelillo15.mast.papi.PlaceholderData
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@PlaceholderData(key = "servercount")
class ServerCountPlaceholder : Placeholder() {
    override fun onPlaceholderRequest(player: Player?, params: String?): String {
        val serverCount = Bukkit.getOnlinePlayers().size

        var staffVanishedCount = 0

        StaffPlayersManagers.getStaffPlayers().forEach { (key, staff) ->
            if (!staff.player.isOnline) return@forEach

            if (staff.isVanished) staffVanishedCount++
        }

        return (serverCount - staffVanishedCount).toString()
    }
}