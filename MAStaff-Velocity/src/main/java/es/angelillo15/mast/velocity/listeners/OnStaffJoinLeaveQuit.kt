package es.angelillo15.mast.velocity.listeners

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.PostLoginEvent
import com.velocitypowered.api.event.player.ServerConnectedEvent
import es.angelillo15.mast.api.managers.PreviousServerManager
import es.angelillo15.mast.handler.StaffJoinLeaveChangeHandler

class OnStaffJoinLeaveQuit : StaffJoinLeaveChangeHandler() {
    @Inject
    private lateinit var serverManger: PreviousServerManager

    @Subscribe
    fun onStaffJoin(event: PostLoginEvent) {
        val player = event.player

        if (!player.hasPermission("mast.staff.join"))
            return

        staffJoin(player.username)
    }

    @Subscribe
    fun onStaffLeave(event: DisconnectEvent) {
        val player = event.player

        if (!player.hasPermission("mast.staff.leave"))
            return

        staffLeave(player.username)
    }

    @Subscribe
    fun onStaffSwitch(event: ServerConnectedEvent) {
        val player = event.player

        if (!player.hasPermission("mast.staff.change"))
            return

        staffSwitch(player.username, event.server.serverInfo.name)

        serverManger.setPreviousServer(player.username, event.server.serverInfo.name)
    }

}