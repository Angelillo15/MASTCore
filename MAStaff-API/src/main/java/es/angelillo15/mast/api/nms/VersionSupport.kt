package es.angelillo15.mast.api.nms

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class VersionSupport {
  /**
   * Sends a packet to a player.
   * @param player The player to send the packet to.
   * @param packet The packet to send.
   */
  abstract fun sendPacket(player: Player, packet: Any)

  /**
   * Sets the tag of an item.
   * @param item The item to set the tag of.
   * @param key The key to set.
   * @param value The value of the key.
   */
  abstract fun setTag(item: ItemStack, key: String, value: String): ItemStack

  /**
   * Gets the tag of an item.
   * @param item The item to get the tag of.
   * @param key The key to get.
   */
  abstract fun getTag(item: ItemStack, key: String): String?

  /**
   * Removes the tag of an item.
   * @param item The item to remove the tag of.
   * @param key The key to remove.
   */
  abstract fun removeTag(item: ItemStack, key: String): ItemStack

  /**
   * Send an action bar message to a player.
   * @param player The player to send the action bar message to.
   * @param message The message to send.
   */
  abstract fun sendActionBar(player: Player, message: Component)
}