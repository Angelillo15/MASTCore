package es.angelillo15.mast.api.config.common

import com.google.inject.Inject

object Config {
    @Inject
    private lateinit var commonConfig: CommonConfig;

    fun language(): String {
        return commonConfig.config!!.config.getString("Config.language")
    }

    object StaffChat {
        fun enabled(): Boolean {
            return commonConfig.config!!.config.getBoolean("StaffChat.enabled")
        }

        object Prefix {
            fun enabled(): Boolean {
                return commonConfig.config!!.config.getBoolean("StaffChat.prefix.enabled")
            }

            fun key(): String {
                return commonConfig.config!!.config.getString("StaffChat.prefix.key")
            }
        }
    }
}
