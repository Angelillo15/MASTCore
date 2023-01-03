package es.angelillo15.mast.bukkit;

import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.exceptions.AlreadyDisableException;
import es.angelillo15.mast.api.exceptions.AlreadyEnableException;
import es.angelillo15.mast.api.items.StaffItem;
import es.angelillo15.mast.bukkit.config.Messages;
import es.angelillo15.mast.bukkit.loaders.ItemsLoader;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StaffPlayer implements IStaffPlayer {
    private File playerInventoryFile;
    private FileConfiguration playerInventoryConfig;
    public StaffPlayer(Player player){
        this.player = player;
        playerInventoryFile = new File(MAStaff.getPlugin().getDataFolder().getAbsoluteFile() + "/data/inventories/" + player.getUniqueId() + ".yml");
        playerInventoryConfig = YamlConfiguration.loadConfiguration(playerInventoryFile);
    }
    private boolean staffMode;
    private Player player;
    private ArrayList<StaffItem> items = new ArrayList<>();

    @SneakyThrows
    @Override
    public void toggleStaffMode() {
        setStaffMode(staffMode);
    }

    @Override
    public boolean isStaffMode() {
        return false;
    }

    @Override
    public void setStaffMode(@NonNull boolean staffMode){
        // if(staffMode && this.staffMode) throw new AlreadyEnableException("The staff mode is already enable");
        // if(!staffMode && !this.staffMode) throw new AlreadyDisableException("The staff mode is already disable");
        if(staffMode) disableStaffMode();
        else enableStaffMode();
    }

    public void disableStaffMode(){
        clearInventory();
        restoreInventory();
        player.sendMessage(Messages.GET_STAFF_MODE_DISABLE_MESSAGE());
        staffMode = false;
    }

    public void enableStaffMode(){
        saveInventory();
        clearInventory();
        setItems();
        player.sendMessage(Messages.GET_STAFF_MODE_ENABLE_MESSAGE());
        staffMode = true;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void setItems() {
        if(items.isEmpty()) {
            ItemsLoader.getManager().getItems().forEach(item -> {
                if(player.hasPermission(item.getPermission())) {
                    item.setItem(player);
                }
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

    @SneakyThrows
    @Override
    public void saveInventory() {
        playerInventoryConfig.set("inventory.content", player.getInventory().getContents());
        playerInventoryConfig.set("inventory.armor", player.getInventory().getArmorContents());
        playerInventoryConfig.save(playerInventoryFile);

        clearInventory();
    }

    @Override
    public void clearInventory() {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }

    @Override
    public void restoreInventory() {
        ItemStack[] content = ((List<ItemStack>) playerInventoryConfig.get("inventory.armor")).toArray(new ItemStack[0]);
        player.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) playerInventoryConfig.get("inventory.armor")).toArray(new ItemStack[0]);
        player.getInventory().setContents(content);
    }
}
