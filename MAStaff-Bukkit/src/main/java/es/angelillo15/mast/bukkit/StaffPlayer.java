package es.angelillo15.mast.bukkit;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.database.sql.CommonQueries;
import es.angelillo15.mast.api.items.StaffItem;
import es.angelillo15.mast.api.managers.GlowManager;
import es.angelillo15.mast.api.managers.VanishedPlayers;
import es.angelillo15.mast.bukkit.config.Messages;
import es.angelillo15.mast.bukkit.loaders.ItemsLoader;
import es.angelillo15.mast.bukkit.utils.PermsUtils;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import lombok.SneakyThrows;
import me.MrGraycat.eGlow.API.Enum.EGlowColor;
import me.MrGraycat.eGlow.EGlow;
import me.MrGraycat.eGlow.Manager.Interface.IEGlowPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
    private ChatColor glowColor = ChatColor.GREEN;
    private boolean staffMode;
    private Player player;
    private boolean vanished;
    private ArrayList<StaffItem> items = new ArrayList<>();

    public StaffPlayer(Player player){
        this.player = player;
        playerInventoryFile = new File(MAStaff.getPlugin().getDataFolder().getAbsoluteFile() + "/data/staffMode/" + player.getUniqueId() + ".yml");
        playerInventoryConfig = YamlConfiguration.loadConfiguration(playerInventoryFile);
    }

    @SneakyThrows
    @Override
    public void toggleStaffMode() {
        setStaffMode(staffMode, true);
    }
    @Override
    public boolean isStaffMode() {
        return staffMode;
    }


    @Override
    public void setStaffMode(boolean staffMode, boolean saveInventory){
        if(staffMode) disableStaffMode();
        else enableStaffMode(saveInventory);
        sendPluginMessage();
    }

    @Override
    public void toggleStaffMode(boolean saveInventory) {
        setStaffMode(staffMode, saveInventory);
        MAStaff.getPlugin().getPLogger().debug("Toggling staff mode for " + player.getName() + " with saveInventory = " + saveInventory);
    }

    @Override
    public void toggleVanish() {
        setVanish(!isVanished());
    }

    @Override
    public boolean isVanished() {
        return vanished;
    }

    public void setVanish(boolean vanish){
        if(vanish) enableVanish();
        else disableVanish();
    }

    public void enableVanish(){
        VanishedPlayers.addPlayer(player);
        vanished = true;
        player.sendMessage(Messages.GET_VANISH_ENABLE_MESSAGE());
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(!p.hasPermission(Permissions.STAFF_VANISH_SEE.getPermission()))
                p.hidePlayer(player);
        });

    }

    public void disableVanish(){
        VanishedPlayers.removePlayer(player);
        vanished = false;
        player.sendMessage(Messages.GET_VANISH_DISABLE_MESSAGE());
        Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(player));
    }

    public void disableStaffMode(){
        player.sendMessage(Messages.GET_STAFF_MODE_DISABLE_MESSAGE());
        setModeData(false);
        clearInventory();
        CommonQueries.updateAsync(player.getUniqueId(), 0);
        staffMode = false;
        restoreInventory();
        disableVanish();
        changeGamemode(GameMode.SURVIVAL);
        StaffUtils.asyncBroadcastMessage(Messages.GET_VANISH_JOIN_MESSAGE()
                .replace("{player}", player.getName()));
        setGlowing(false);
    }

    public void enableStaffMode(boolean saveInventory){
        player.sendMessage(Messages.GET_STAFF_MODE_ENABLE_MESSAGE());
        setModeData(true);
        if(saveInventory) saveInventory();
        enableVanish();
        clearInventory();
        setItems();
        CommonQueries.updateAsync(player.getUniqueId(), 1);
        staffMode = true;
        changeGamemode(GameMode.CREATIVE);
        if(saveInventory) StaffUtils.asyncBroadcastMessage(Messages.GET_VANISH_LEAVE_MESSAGE()
                .replace("{player}", player.getName()));
        setGlowing(true);
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
                    items.add(item);
                }
            });
            return;
        }

        items.forEach(item -> {
            item.setItem(player);
        });
    }

    @Override
    public ArrayList<StaffItem> getItems() {
        return items;
    }

    @Override
    public void sendPluginMessage() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("mast");
        out.writeUTF(player.getName());
        out.writeUTF(String.valueOf(isStaffMode()));

        player.sendPluginMessage(MAStaff.getPlugin(), "BungeeCord", out.toByteArray());
    }

    @SneakyThrows
    @Override
    public void saveInventory() {
        MAStaff.getPlugin().getPLogger().debug("Saving inventory for " + player.getName());
        playerInventoryConfig.set("inventory", null);
        playerInventoryConfig.set("inventory.content", player.getInventory().getContents());
        playerInventoryConfig.set("inventory.armor", player.getInventory().getArmorContents());
        playerInventoryConfig.save(playerInventoryFile);
    }

    @Override
    public void clearInventory() {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }

    @Override
    public void restoreInventory() {
        MAStaff.getPlugin().getPLogger().debug("Restoring inventory for " + player.getName());
        playerInventoryConfig = YamlConfiguration.loadConfiguration(playerInventoryFile);
        ItemStack[] content = ((List<ItemStack>) playerInventoryConfig.get("inventory.armor")).toArray(new ItemStack[0]);
        player.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) playerInventoryConfig.get("inventory.content")).toArray(new ItemStack[0]);
        player.getInventory().setContents(content);
    }

    @Override
    public boolean existsData() {
        return playerInventoryFile.exists();
    }

    @Override
    public void changeGamemode(GameMode gamemode) {
        player.setGameMode(gamemode);
    }

    @Override
    public void setGlowColor(ChatColor color) {
        this.glowColor = color;
    }

    @Override
    public ChatColor getGlowColor() {
        return this.glowColor;
    }

    @Override
    public void setGlowing(boolean status) {
        if(status) enableGlowing();
        else disableGlowing();
    }

    public void enableGlowing() {
        if(!MAStaff.isGlowEnabled()) return;
        this.glowColor = GlowManager.getColor(PermsUtils.getGroup(player));
        this.glowColor = GlowManager.getColor(PermsUtils.getGroup(player));
        IEGlowPlayer iegp = EGlow.getAPI().getEGlowPlayer(player);
        EGlow.getAPI().enableGlow(
                iegp,
                EGlowColor.valueOf(glowColor.name())
        );

        EGlow.getAPI().resetCustomGlowReceivers(iegp);
    }

    public void disableGlowing() {
        if(!MAStaff.isGlowEnabled()) return;
        IEGlowPlayer iegp = EGlow.getAPI().getEGlowPlayer(player);
        EGlow.getAPI().disableGlow(iegp);
    }

    @SneakyThrows
    public void setModeData(boolean staffMode){
        this.playerInventoryConfig.set("Status.staffMode", staffMode);
        MAStaff.getPlugin().getPLogger().debug("Saving staff mode data for player " + player.getName() + " with value " + staffMode);
        this.playerInventoryConfig.save(playerInventoryFile);
    }

    @Override
    public boolean wasInStaffMode() {
        return playerInventoryConfig.getBoolean("Status.staffMode");
    }
}
