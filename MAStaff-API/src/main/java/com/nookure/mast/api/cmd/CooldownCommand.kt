package com.nookure.mast.api.cmd

import com.nookure.mast.api.cmd.sender.CommandSender

abstract class CooldownCommand(
        private val cooldown: Int = 60,
        private val cooldownMessage: String = "&cYou must wait &a{cooldown} &cseconds to use the command again",
        private val enabled: Boolean = true
) : Command() {
  private var cooldowns: MutableMap<String, Long> = mutableMapOf()
  override fun onCommand(sender: CommandSender, label: String, args: Array<String>) {
    if (!enabled) {
      onCooldownCommand(sender, label, args)
      return
    }

    if (!cooldowns.containsKey(sender.uniqueId)) {
      cooldowns[sender.uniqueId] = System.currentTimeMillis() + (cooldown * 1000)

      onCooldownCommand(sender, label, args)

      return
    }

    if (cooldowns[sender.uniqueId]!! > System.currentTimeMillis()) {
      sender.sendMessage(
              cooldownMessage.replace(
                      "{cooldown}",
                      ((cooldowns[sender.uniqueId]!! - System.currentTimeMillis()) / 1000).toString()
              )
      )
      return
    }

    cooldowns.remove(sender.uniqueId)
  }

  fun invalidateCooldown(sender: CommandSender) {
    cooldowns.remove(sender.uniqueId)
  }

  abstract fun onCooldownCommand(sender: CommandSender, label: String, args: Array<String>)
}