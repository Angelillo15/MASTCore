package es.angelillo15.mast.api.punishments

import es.angelillo15.mast.api.punishments.cmd.PunishTargetCommand

abstract class PunishTargetReasonCommand() : PunishTargetCommand() {
    private var startCount = 1
    private var defaultReason = "No reason provided"

    constructor(startCount: Int) : this() {
        this.startCount = startCount
    }

    constructor(startCount: Int, defaultReason: String) : this() {
        this.startCount = startCount
        this.defaultReason = defaultReason
    }

    constructor(defaultReason: String) : this() {
        this.defaultReason = defaultReason
    }

    override fun onCommand(sender: IPunishPlayer, target: String, label: String, args: Array<out String>) {
        val reason = StringBuilder()

        for (i in startCount until args.size) {
            reason.append(args[i]).append(" ")
        }

        if (reason.toString().isEmpty()) {
            reason.append(defaultReason)
        }

        if (reason.endsWith(" ")) {
            reason.deleteCharAt(reason.length - 1)
        }

        onCommand(sender, target, label, args, reason.toString())
    }

    abstract fun onCommand(sender: IPunishPlayer, target: String, label: String, args: Array<out String>, reason: String)
}