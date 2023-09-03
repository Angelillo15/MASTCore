package es.angelillo15.velocity.punishments

import es.angelillo15.mast.api.Constants
import es.angelillo15.mast.api.addons.annotations.Addon

@Addon(
        name = "MAStaff-Punishments",
        version = Constants.VERSION,
        author = "Angelillo15",
        description = "An addon for MAStaff that adds punishments to the plugin",
        loadOnScan = false,
        platform = Addon.AddonPlatform.VELOCITY
)
class PunishmentsLoader : MAStaffPunishments() {
}