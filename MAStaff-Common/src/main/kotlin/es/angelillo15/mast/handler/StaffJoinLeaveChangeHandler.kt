package es.angelillo15.mast.handler

import com.google.inject.Inject
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.config.common.CommonMessages
import es.angelillo15.mast.api.managers.PreviousServerManager

open class StaffJoinLeaveChangeHandler {
    @Inject
    private lateinit var serverUtils: IServerUtils
    @Inject
    private lateinit var serverManager: PreviousServerManager
    fun staffJoin(player: String) {
        serverUtils.broadcastMessage(
            CommonMessages.StaffChanges.staffJoin()
                .replace("{player}", player)
        , "mast.staff.notify.join")
    }

    fun staffLeave(player: String) {
        serverUtils.broadcastMessage(
            CommonMessages.StaffChanges.staffQuit()
                .replace("{player}", player)
        , "mast.staff.notify.leave")
    }

    fun staffSwitch(player: String, server: String) {
        serverUtils.broadcastMessage(
            CommonMessages.StaffChanges.staffSwitch()
                .replace("{player}", player)
                .replace("{server}", server)
                .replace("{fromServer}", serverManager.getPreviousServer(player))
        , "mast.staff.notify.change")
    }
}