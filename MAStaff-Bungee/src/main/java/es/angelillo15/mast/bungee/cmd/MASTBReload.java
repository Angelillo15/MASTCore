package es.angelillo15.mast.bungee.cmd;

import es.angelillo15.mast.bungee.MAStaff;
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
                    sender.sendMessage(new TextComponent("§7To reload the plugin, use §a/mastb reload"));
                    return;
                }

                if(args[0].equalsIgnoreCase("reload")){
                    MAStaff.getInstance().reload();
                    sender.sendMessage(new TextComponent(Messages.GET_RELOADED_MESSAGE()));
                    MAStaff.getInstance().getLogger().info(Messages.GET_RELOADED_MESSAGE());
                }
            }
        } else{

            if(args.length < 1){
                sender.sendMessage(new TextComponent("§7To reload the plugin, use §a/mastb reload"));
                return;
            }

            if(args[0].equalsIgnoreCase("reload")){
                MAStaff.getInstance().reload();
                MAStaff.getInstance().getLogger().info(Messages.GET_RELOADED_MESSAGE());
            }

        }

    }
}
