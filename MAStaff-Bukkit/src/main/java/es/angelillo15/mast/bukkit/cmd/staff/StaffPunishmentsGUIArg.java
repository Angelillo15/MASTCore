package es.angelillo15.mast.bukkit.cmd.staff;

import es.angelillo15.mast.api.cmd.SubCommand;
import es.angelillo15.mast.api.gui.TargetGUI;
import es.angelillo15.mast.bukkit.gui.PunishmentsGUI;
import es.angelillo15.mast.bukkit.gui.SelectTargetGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StaffPunishmentsGUIArg extends SubCommand {
    @Override
    public String getName() {
        return "punishments";
    }

    @Override
    public String getDescription() {
        return "Opens the punishments GUI";
    }

    @Override
    public String getSyntax() {
        return "/staff punishments";
    }

    @Override
    public String getPermission() {
        return "mast.staff.gui.punishments";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if(!sender.hasPermission(getPermission())) return;
            Player player = (Player) sender;

            new SelectTargetGUI(player, (target) -> {
                new PunishmentsGUI(player, target, 1).open();
            }).open();
        }
    }
}
