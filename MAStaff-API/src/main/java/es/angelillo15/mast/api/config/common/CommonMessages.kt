package es.angelillo15.mast.api.config.common

import com.google.inject.Inject

object CommonMessages {
    @Inject
    private lateinit var commonConfigLoader: CommonConfigLoader;

    fun prefix(): String {
        return commonConfigLoader.messages!!.config.getString("Prefix")
    }

    object StaffChat {
        fun format(): String {
            return get("StaffChat.format")
        }

        fun correctUse(): String {
            return get("StaffChat.correctUse")
        }

        fun enabled(): String {
            return get("StaffChat.enabled")
        }

        fun disabled(): String {
            return get("StaffChat.disabled")
        }
    }

    object HelpOp {
        fun format(): String {
            return get("Helpop.format")
        }

        fun correctUse(): String {
            return get("Helpop.correctUse")
        }

        fun message(): String {
            return get("Helpop.message")
        }

        fun cooldown(): String {
            return get("Helpop.cooldown")
        }
    }

    object StaffChanges {
        fun staffJoin(): String {
            return get("StaffChanges.staffJoin")
        }

        fun staffQuit(): String {
            return get("StaffChanges.staffQuit")
        }

        fun staffSwitch(): String {
            return get("StaffChanges.staffSwitch")
        }
    }

    fun get(key: String): String {
        return commonConfigLoader.messages!!.config.getString(key).replace("{prefix}", prefix())
    }
}