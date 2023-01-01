package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.exceptions.AlreadyDisableException;
import es.angelillo15.mast.api.exceptions.AlreadyEnableException;
import es.angelillo15.mast.api.items.StaffItem;
import es.angelillo15.mast.bukkit.loaders.ItemsLoader;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StaffPlayer implements IStaffPlayer {
    public StaffPlayer(Player player){
        this.player = player;
    }
    private boolean staffMode;
    private Player player;
    private ArrayList<StaffItem> items = new ArrayList<>();

    @Override
    public void toggleStaffMode() {
        staffMode = !staffMode;
    }

    @Override
    public boolean isStaffMode() {
        return false;
    }

    @Override
    public void setStaffMode(@NonNull boolean staffMode) throws AlreadyEnableException, AlreadyDisableException {
        if(staffMode && this.staffMode) throw new AlreadyEnableException("The staff mode is already enable");
        if(!staffMode && !this.staffMode) throw new AlreadyDisableException("The staff mode is already disable");

    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void setItems() {
        if(items.isEmpty()) {
            ItemsLoader.getManager().getItems().forEach(item -> {

            });
        }
    }

    @Override
    public ArrayList<StaffItem> getItems() {
        return items;
    }

    @Override
    public void sendPluginMessage() {

    }
}
