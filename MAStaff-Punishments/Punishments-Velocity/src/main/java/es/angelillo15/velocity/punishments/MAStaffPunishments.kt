package es.angelillo15.velocity.punishments

import com.google.inject.Inject
import com.velocitypowered.api.proxy.ProxyServer
import es.angelillo15.mast.api.ILogger
import es.angelillo15.mast.api.addons.MAStaffAddon

open class MAStaffPunishments : MAStaffAddon<ProxyServer>() {
  @Inject
  private lateinit var logger: ILogger

  override fun onEnable() {
    logger.info("Addon loaded ✨")
  }

  override fun onDisable() {
    super.onDisable()
  }

  override fun reload() {
    super.reload()
  }
}