package com.nookure.mast.bukkit.nms

import es.angelillo15.mast.api.nms.VersionSupport
import es.angelillo15.mast.api.utils.MAStaffInject
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.world.item.ItemStack
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer
import org.bukkit.entity.Player
class V1_20_2_R0(val instance: MAStaffInject) : VersionSupport() {
  override fun sendPacket(player: Player, packet: Any) {
    val craftPlayer = player as CraftPlayer
    val connection = craftPlayer.handle.connection

    connection.send(packet as Packet<*>)
  }

  override fun setTag(item: org.bukkit.inventory.ItemStack, key: String, value: String): org.bukkit.inventory.ItemStack {
    val nmsItemStack = getNmsItemCopy(item)
    val tag = nmsItemStack.tag ?: initializeTag(nmsItemStack).tag!!
    tag.putString(key, value)

    return applyTag(item, tag)
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

  private fun getTag(itemStack: org.bukkit.inventory.ItemStack): CompoundTag? {
    val i = CraftItemStack.asNMSCopy(itemStack) ?: return null
    return i.tag
  }

  private fun getTag(itemStack: ItemStack): CompoundTag? {
    return itemStack.tag
  }

  private fun initializeTag(itemStack: org.bukkit.inventory.ItemStack): CompoundTag? {
    val i = CraftItemStack.asNMSCopy(itemStack)
            ?: throw RuntimeException("Cannot convert given item to a NMS item")
    return initializeTag(i).tag!!
  }

  private fun initializeTag(itemStack: ItemStack): ItemStack {
    val tag = CompoundTag()
    itemStack.tag = tag
    return itemStack
  }

  private fun getCreateTag(itemStack: ItemStack): CompoundTag {
    val tag = getTag(itemStack)

    if (tag != null) {
      return tag
    }

    return initializeTag(itemStack).tag!!
  }

  private fun getCreateTag(itemStack: org.bukkit.inventory.ItemStack?): CompoundTag? {
    val i = CraftItemStack.asNMSCopy(itemStack)
            ?: throw RuntimeException("Cannot convert given item to a NMS item")
    return getCreateTag(i)
  }

  private fun applyTag(itemStack: org.bukkit.inventory.ItemStack?, tag: CompoundTag): org.bukkit.inventory.ItemStack {
    return CraftItemStack.asBukkitCopy(applyTag(getNmsItemCopy(itemStack), tag))
  }

  private fun applyTag(itemStack: ItemStack, tag: CompoundTag): ItemStack {
    itemStack.tag = tag
    return itemStack
  }

  private fun getNmsItemCopy(itemStack: org.bukkit.inventory.ItemStack?): ItemStack {
    return CraftItemStack.asNMSCopy(itemStack)
  }

  private fun getCraftPlayer(player: Player): CraftPlayer {
    return player as CraftPlayer
  }
}