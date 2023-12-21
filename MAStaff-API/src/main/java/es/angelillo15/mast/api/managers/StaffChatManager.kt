package es.angelillo15.mast.api.managers

import com.google.inject.Singleton
import com.nookure.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.config.common.CommonMessages.StaffChat.disabled
import es.angelillo15.mast.api.config.common.CommonMessages.StaffChat.enabled

@Singleton
class StaffChatManager {
  private val staffChatEnabled = ArrayList<String>()

  /**
   * @param player The player to check
   * @return If the player has the staff chat enabled
   */
  fun isStaffChatEnabled(player: String): Boolean {
    return staffChatEnabled.contains(player)
  }

  /**
   * @param player The player to set
   * @param enabled If the player has the staff chat enabled
   */
  fun setStaffChatEnabled(player: String, enabled: Boolean) {
    if (enabled) {
      staffChatEnabled.add(player)
    } else {
      staffChatEnabled.remove(player)
    }
  }

  fun toggleStaffChat(player: String) {
    val toggle = isStaffChatEnabled(player)

    setStaffChatEnabled(player, !toggle)
  }

  fun toggleAndAdvert(sender: CommandSender) {
    if (isStaffChatEnabled(sender.getName())) {
      setStaffChatEnabled(sender.getName(), false)
      sender.sendMessage(disabled())
    } else {
      setStaffChatEnabled(sender.getName(), true)
      sender.sendMessage(enabled())
    }
  }
}