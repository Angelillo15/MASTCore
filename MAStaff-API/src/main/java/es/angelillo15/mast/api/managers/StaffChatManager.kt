package es.angelillo15.mast.api.managers

import com.google.inject.Singleton

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
}