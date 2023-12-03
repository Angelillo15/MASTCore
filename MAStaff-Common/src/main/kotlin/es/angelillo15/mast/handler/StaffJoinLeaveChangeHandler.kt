package es.angelillo15.mast.handler

import com.google.inject.Inject
import com.nookure.mast.api.event.EventManager
import com.nookure.mast.api.event.staff.StaffJoinEvent
import com.nookure.mast.api.event.staff.StaffLeaveEvent
import com.nookure.mast.api.event.staff.StaffServerSwitchEvent
import es.angelillo15.mast.api.IServerUtils
import es.angelillo15.mast.api.config.common.CommonMessages
import es.angelillo15.mast.api.managers.PreviousServerManager

open class StaffJoinLeaveChangeHandler {
  @Inject
  private lateinit var serverUtils: IServerUtils

  @Inject
  private lateinit var serverManager: PreviousServerManager

  @Inject
  private lateinit var eventManager: EventManager

  fun staffJoin(player: String) {
    serverUtils.broadcastMessage(
      CommonMessages.StaffChanges.staffJoin()
        .replace("{player}", player), "mast.staff.notify.join"
    )

    eventManager.fireEvent(StaffJoinEvent(player))
  }

  fun staffLeave(player: String) {
    serverUtils.broadcastMessage(
      CommonMessages.StaffChanges.staffQuit()
        .replace("{player}", player), "mast.staff.notify.leave"
    )

    eventManager.fireEvent(StaffLeaveEvent(player))
  }

  fun staffSwitch(player: String, server: String) {
    serverUtils.broadcastMessage(
      CommonMessages.StaffChanges.staffSwitch()
        .replace("{player}", player)
        .replace("{server}", server)
        .replace("{fromServer}", serverManager.getPreviousServer(player)), "mast.staff.notify.change"
    )

    eventManager.fireEvent(StaffServerSwitchEvent(player, serverManager.getPreviousServer(player), server))
  }
}