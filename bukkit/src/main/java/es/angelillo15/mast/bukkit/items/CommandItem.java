package es.angelillo15.mast.bukkit.items;

import es.angelillo15.mast.bukkit.api.item.ExecutableItem;
import es.angelillo15.mast.bukkit.api.item.ICommandItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandItem extends ExecutableItem implements ICommandItem {

    private int p_Slots;
    private Material p_material;
    private int p_sfid;
    private List<String> p_commands;

    @Override
    public int getSlot() {
        return p_Slots;
    }

    @Override
    public void click(Player player) {
        for (String s : p_commands){
            String type = s.split(":")[0];
            String command = "";
            for (int i = 1; i < s.length(); i++){
                command = command + s.split(":")[i];
            }

            switch (type){
                case "player":
                    player.performCommand(command);
                    return;
                case "console":
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command
                            .replace("{player}", player.getName())
                    );
                    return;

            }
            player.performCommand(command);
        }
    }

    @Override
    public Material getMaterial() {
        return null;
    }

    @Override
    public int getSfid() {
        return 0;
    }

    @Override
    public List<String> getCommands() {
        return p_commands;
    }


}
