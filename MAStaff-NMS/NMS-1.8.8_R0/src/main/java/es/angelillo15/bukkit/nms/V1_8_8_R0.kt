package es.angelillo15.bukkit.nms

import es.angelillo15.mast.api.nms.VersionSupport
import es.angelillo15.mast.api.utils.MAStaffInject
import net.minecraft.server.v1_8_R3.ItemStack
import net.minecraft.server.v1_8_R3.NBTTagCompound
import net.minecraft.server.v1_8_R3.NBTTagString
import net.minecraft.server.v1_8_R3.Packet
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
import org.bukkit.entity.Player

class V1_8_8_R0(val instance: MAStaffInject) : VersionSupport() {
  override fun sendPacket(player: Player, packet: Any) {
    val connection = getCraftPlayer(player).handle.playerConnection
    connection.sendPacket(packet as Packet<*>)
  }

  override fun setTag(
    item: org.bukkit.inventory.ItemStack,
    key: String,
    value: String
  ): org.bukkit.inventory.ItemStack {
    val nmsItemStack = getNmsItemCopy(item)
    val tag = nmsItemStack.tag ?: NBTTagCompound()
    tag.setString(key, value)
    nmsItemStack.tag = tag

    return CraftItemStack.asBukkitCopy(nmsItemStack)
  }

  override fun getTag(item: org.bukkit.inventory.ItemStack, key: String): String? {
    val tag = getTag(item) ?: return null
    return tag.getString(key)
  }

  override fun removeTag(item: org.bukkit.inventory.ItemStack, key: String): org.bukkit.inventory.ItemStack {
    val tag = getTag(item) ?: return item
    tag.remove(key)
    return applyTag(item, tag)
  }

  private fun getCreateTag(itemStack: ItemStack): NBTTagCompound {
    val tag = getTag(itemStack)
    return tag ?: initializeTag(itemStack).tag!!
  }

  private fun initializeTag(itemStack: org.bukkit.inventory.ItemStack): NBTTagCompound {
    val i = CraftItemStack.asNMSCopy(itemStack)
      ?: throw RuntimeException("Cannot convert given item to a NMS item")
    return initializeTag(i).tag!!
  }

  private fun initializeTag(itemStack: ItemStack): ItemStack {
    val tag = getTag(itemStack) ?: NBTTagCompound()

    itemStack.tag = tag
    return itemStack
  }

  private fun getTag(itemStack: org.bukkit.inventory.ItemStack): NBTTagCompound? {
    val i = CraftItemStack.asNMSCopy(itemStack) ?: return null
    return i.tag
  }

  private fun getTag(itemStack: ItemStack): NBTTagCompound? {
    return itemStack.tag
  }

  private fun applyTag(itemStack: org.bukkit.inventory.ItemStack, tag: NBTTagCompound): org.bukkit.inventory.ItemStack {
    return CraftItemStack.asBukkitCopy(applyTag(getNmsItemCopy(itemStack), tag))
  }

  private fun applyTag(itemStack: ItemStack, tag: NBTTagCompound): ItemStack {
    itemStack.tag = tag
    return itemStack
  }

  private fun getNmsItemCopy(itemStack: org.bukkit.inventory.ItemStack): ItemStack {
    return CraftItemStack.asNMSCopy(itemStack)
  }

  private fun getCraftPlayer(player: Player): CraftPlayer {
    return player as CraftPlayer
  }

}