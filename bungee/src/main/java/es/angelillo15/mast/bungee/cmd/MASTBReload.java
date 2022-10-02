package es.angelillo15.mast.bungee.cmd;

import es.angelillo15.mast.bungee.MASTBungeeManager;
import es.angelillo15.mast.bungee.config.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MASTBReload extends Command {
    public MASTBReload() {
        super("MASTB", "mast.reload", "MASTBReload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            if(sender.hasPermission("mast.admin")){
                if(args.length < 1){
                    sender.sendMessage(new TextComponent("§cUsage: /MASTB <reload>"));
                    return;
                }

                if(args[0].equalsIgnoreCase("reload")){
                    MASTBungeeManager.getInstance().reload();
                    sender.sendMessage(new TextComponent(Messages.GET_RELOADED_MESSAGE()));
                    MASTBungeeManager.getInstance().getLogger().info(Messages.GET_RELOADED_MESSAGE());
                }
            }
        } else{

            if(args.length < 1){
                sender.sendMessage(new TextComponent("§cUsage: /MASTB <reload>"));
                return;
            }

            if(args[0].equalsIgnoreCase("reload")){
                MASTBungeeManager.getInstance().reload();
                MASTBungeeManager.getInstance().getLogger().info(Messages.GET_RELOADED_MESSAGE());
            }

        }

    }
}
