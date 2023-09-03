package es.angelillo15.mast.api.config.common

import com.google.inject.Inject

object CommonConfig {
  @Inject
  private lateinit var commonConfigLoader: CommonConfigLoader;

  fun language(): String {
    return commonConfigLoader.config!!.config.getString("Config.language")
  }

  object StaffChat {
    fun enabled(): Boolean {
      return commonConfigLoader.config!!.config.getBoolean("StaffChat.enabled")
    }

    object Prefix {
      fun enabled(): Boolean {
        return commonConfigLoader.config!!.config.getBoolean("StaffChat.prefix.enabled")
      }

      fun key(): String {
        return commonConfigLoader.config!!.config.getString("StaffChat.prefix.key")
      }
    }
  }

  object Helpop {
    fun enabled(): Boolean {
      return commonConfigLoader.config!!.config.getBoolean("Helpop.enabled")
    }

    object Cooldown {
      fun enabled(): Boolean {
        return commonConfigLoader.config!!.config.getBoolean("Helpop.cooldown.enabled")
      }

      fun time(): Int {
        return commonConfigLoader.config!!.config.getInt("Helpop.cooldown.time")
      }
    }
  }
}
