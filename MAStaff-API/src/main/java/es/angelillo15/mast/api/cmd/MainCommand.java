package es.angelillo15.mast.api.cmd;

import es.angelillo15.mast.api.player.IMastPlayer;

public abstract class MainCommand {
    public abstract void onCommand(IMastPlayer sender, Command cmd, String label, String[] args);

    void load(){

    }
}
