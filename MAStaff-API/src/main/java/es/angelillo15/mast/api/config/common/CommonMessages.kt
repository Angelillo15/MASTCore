package es.angelillo15.mast.api.config.common

import com.google.inject.Inject

object CommonMessages {
    @Inject
    private lateinit var commonConfig: CommonConfig;

    fun prefix(): String {
        return commonConfig.messages!!.config.getString("Prefix")
    }

    object StaffChat {
        fun format(): String {
            return get("StaffChat.format")
        }

        fun correctUse(): String {
            return get("StaffChat.correctUse")
        }
    }

    fun get(key: String): String {
        return commonConfig.messages!!.config.getString(key).replace("{prefix}", prefix())
    }
}