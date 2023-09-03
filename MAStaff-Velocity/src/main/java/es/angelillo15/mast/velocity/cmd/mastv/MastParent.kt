package es.angelillo15.mast.velocity.cmd.mastv

import com.google.inject.Inject
import es.angelillo15.mast.api.cmd.CommandData
import es.angelillo15.mast.api.cmd.CommandParent
import es.angelillo15.mast.api.utils.MAStaffInject

@CommandData(
        name = "mastv",
        aliases = ["mastaff-velocity"],
        permission = "mastaff.velocity",
        description = "MAStaff main command"
)
class MastParent : CommandParent() {
  @Inject
  private lateinit var instance: MAStaffInject
  override fun registerSubCommands() {
    registerSubCommand(instance.injector.getInstance(DumpArg::class.java))
    registerSubCommand(instance.injector.getInstance(ReloadArg::class.java))
    registerHelpSubCommand("/mastv")
  }
}