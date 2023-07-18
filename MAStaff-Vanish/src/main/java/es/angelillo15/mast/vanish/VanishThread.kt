package es.angelillo15.mast.vanish

import es.angelillo15.mast.api.MAStaffInstance
import es.angelillo15.mast.api.thread.execute
import es.angelillo15.mast.api.vanish.VanishDataManager
import es.angelillo15.mast.vanish.listeners.VanishListener

import org.bukkit.Bukkit

fun init() {
    execute ({
        val start = System.currentTimeMillis()
        Bukkit.getOnlinePlayers().forEach { player ->
            if (player.hasPermission("mast.vanish.see")) {
                return@forEach
            }

            Bukkit.getOnlinePlayers().forEach { p2 ->
                if (!VanishDataManager.isVanished(p2.name)) {
                    VanishListener.show(p2, player)
                };
            }

            VanishDataManager.getVanishedPlayers().forEach { vanish ->
                VanishListener.hide(vanish.player, player)
            }
        }

        val end = System.currentTimeMillis()

        MAStaffInstance.getLogger().debug("Vanish show/hide task in ${end - start}ms")

        return@execute
    }, 5000, true)
}

