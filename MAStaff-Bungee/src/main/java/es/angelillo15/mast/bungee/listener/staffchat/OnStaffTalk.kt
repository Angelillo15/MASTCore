package es.angelillo15.mast.bungee.listener.staffchat

import es.angelillo15.mast.handler.OnStaffMessageEvent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class OnStaffTalk : Listener, OnStaffMessageEvent() {
    @EventHandler
    fun onStaffTalk(event: ChatEvent) {
        if (event.isCommand) return
        if (event.sender !is ProxiedPlayer) return
        val player = event.sender as ProxiedPlayer

        event.isCancelled = onStaffMessageEvent(player.name, event.message, player.server.info.name)
    }
}