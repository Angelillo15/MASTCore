package es.angelillo15.mast.velocity.listeners

import com.google.common.io.ByteStreams
import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import es.angelillo15.mast.api.ILogger
import es.angelillo15.mast.api.config.velocity.Messages

class OnStaffChange {
    @Inject
    private lateinit var logger: ILogger

    @Subscribe
    fun staffChange(event: PluginMessageEvent) {
        if (event.identifier.id != "mastaff:staff") return

        val input = ByteStreams.newDataInput(event.data)

        val subChannel = input.readUTF()

        if (subChannel != "mast") return

        val player = input.readUTF()
        val status = input.readBoolean()

        if (status) {
            logger.info(
                Messages.playerStaffModeEnabled().replace("{player}", player)
            )
        } else {
            logger.info(
                Messages.playerStaffModeDisabled().replace("{player}", player)
            )
        }
    }
}