package es.angelillo15.mast.bukkit.cmd.staff;

import com.google.inject.Inject;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.cmd.LegacySubCommand;
import es.angelillo15.mast.api.config.bukkit.Config;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.api.config.bukkit.Messages;
import java.util.ArrayList;

import es.angelillo15.mast.api.managers.StaffManager;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCMD implements CommandExecutor {
  @Getter private static final ArrayList<LegacySubCommand> legacySubCommands = new ArrayList<>();
  @Inject private StaffManager staffManager;

  public StaffCMD() {
    legacySubCommands.clear();
    legacySubCommands.add(new StaffListArg());
    legacySubCommands.add(new StaffHelpArg());
    if (Config.StaffVault.enabled()) legacySubCommands.add(new StaffVaultCMD());

    try {
      if (ConfigLoader.getPunishmentsGUI().getConfig().getBoolean("Gui.enable"))
        legacySubCommands.add(new StaffPunishmentsGUIArg());
    } catch (Exception e) {
      MAStaffInstance.getLogger().debug("Using LiteVersion no punishments gui");
    }
  }

  public static void sendHelp(CommandSender sender) {
    sender.sendMessage(TextUtils.colorize("&6----------------Staff----------------"));
    sender.sendMessage(TextUtils.colorize("&bAvailable Commands:"));
    legacySubCommands.forEach(
        subCommand -> {
          if (sender.hasPermission(subCommand.getPermission())) {
            sender.sendMessage(
                TextUtils.colorize(
                    "&b" + subCommand.getSyntax() + " &7- &f" + subCommand.getDescription()));
          }
        });
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) return true;
    if (args.length == 0) {
      Player player = (Player) sender;

      IStaffPlayer staffPlayer = staffManager.getStaffPlayer(player);

      if (staffPlayer == null) {
        sender.sendMessage(TextUtils.colorize(Messages.GET_NO_PERMISSION_MESSAGE()));
        return false;
      }

      staffPlayer.toggleStaffMode();
      return true;
    }

    for (LegacySubCommand legacySubCommand : legacySubCommands) {
      if (args[0].equalsIgnoreCase(legacySubCommand.getName())) {
        if (!sender.hasPermission(legacySubCommand.getPermission())) {
          sender.sendMessage(TextUtils.colorize(Messages.GET_NO_PERMISSION_MESSAGE()));
          return true;
        }
        legacySubCommand.execute(sender, args);
        return true;
      }
    }
    sendHelp(sender);
    return true;
  }
}
