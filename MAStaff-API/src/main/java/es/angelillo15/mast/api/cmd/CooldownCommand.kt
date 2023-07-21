package es.angelillo15.mast.api.cmd

import es.angelillo15.mast.api.cmd.sender.CommandSender

abstract class CooldownCommand(
    private val cooldown: Int = 60,
    private val cooldownMessage: String = "&cYou must wait &a{cooldown} &cseconds to use again the command"
) : Command() {
    private var cooldowns: MutableMap<CommandSender, Long> = mutableMapOf()
    override fun onCommand(sender: CommandSender?, label: String?, args: Array<String?>?) {
        if (cooldowns.containsKey(sender)) {
            sender!!.sendMessage(cooldownMessage.replace("{cooldown}", (cooldowns[sender]!! - System.currentTimeMillis() / 1000).toString()))
            return
        }

        cooldowns[sender!!] = System.currentTimeMillis() + ( cooldown * 1000 )

        onCooldownCommand(sender, label!!, args!!)
    }

    abstract fun onCooldownCommand(sender: CommandSender, label: String, args: Array<String?>)
}