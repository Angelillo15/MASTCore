package es.angelillo15.mast.bungee.listener

import com.google.inject.Inject
import es.angelillo15.mast.api.managers.PreviousServerManager
import es.angelillo15.mast.handler.StaffJoinLeaveChangeHandler
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.event.ServerSwitchEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class OnStaffJoinLeaveQuit : Listener, StaffJoinLeaveChangeHandler() {
    @Inject
    private lateinit var previousServer: PreviousServerManager

    @EventHandler
    fun onStaffJoin(event: PostLoginEvent) {
        val player = event.player

        if (!player.hasPermission("mast.staff.join")) {
            return;
        }

        staffJoin(player.name)
    }

    @EventHandler
    fun onStaffLeave(event: PlayerDisconnectEvent) {
        val player = event.player

        if (!player.hasPermission("mast.staff.leave")) {
            return;
        }

        staffLeave(player.name)
    }

    @EventHandler
    fun onServerSwitch(event: ServerSwitchEvent) {
        val player = event.player

        if (!player.hasPermission("mast.staff.change")) {
            return;
        }

        staffSwitch(player.name, event.player.server.info.name)

        previousServer.setPreviousServer(player.name, event.player.server.info.name)
    }
}