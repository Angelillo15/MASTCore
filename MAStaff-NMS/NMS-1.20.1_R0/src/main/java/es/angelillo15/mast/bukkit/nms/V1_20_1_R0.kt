package es.angelillo15.mast.bukkit.nms

import es.angelillo15.mast.api.nms.VersionSupport
import es.angelillo15.mast.api.utils.MAStaffInject
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.StringTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket
import net.minecraft.world.item.ItemStack
import net.minecraft.world.scores.PlayerTeam
import net.minecraft.world.scores.Team
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack
import org.bukkit.craftbukkit.v1_20_R1.scoreboard.CraftScoreboard
import org.bukkit.entity.Player


class V1_20_1_R0(val instance: MAStaffInject) : VersionSupport() {
  override fun sendPacket(player: Player, packet: Packet<*>) {
    val craftPlayer = player as CraftPlayer
    val connection = craftPlayer.handle.connection

    connection.send(packet)
  }

  override fun setTag(item: org.bukkit.inventory.ItemStack, key: String, value: String): org.bukkit.inventory.ItemStack {
    val tag = getCreateTag(item);
    tag!!.tags[key] = StringTag.valueOf(value);
    return applyTag(item, tag);
  }

  override fun getTag(item: org.bukkit.inventory.ItemStack, key: String): String? {
    val tag = getTag(item) ?: return null
    return tag.get(key)!!.asString
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
    return initializeTag(i)
  }

  private fun initializeTag(itemStack: ItemStack): CompoundTag {
    val tag = getTag(itemStack) ?: throw RuntimeException("Provided item already has a Tag")

    itemStack.tag = tag
    return tag
  }

  private fun getCreateTag(itemStack: ItemStack): CompoundTag? {
    val tag = getTag(itemStack)
    return tag ?: initializeTag(itemStack)
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