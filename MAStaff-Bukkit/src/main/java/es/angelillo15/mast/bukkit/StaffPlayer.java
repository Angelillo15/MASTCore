package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.exceptions.AlreadyDisableException;
import es.angelillo15.mast.api.exceptions.AlreadyEnableException;
import lombok.NonNull;
import org.bukkit.entity.Player;

public class StaffPlayer implements IStaffPlayer {
    private boolean staffMode;
    private Player player;

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

    public StaffPlayer(Player player){
        this.player = player;
    }
}
