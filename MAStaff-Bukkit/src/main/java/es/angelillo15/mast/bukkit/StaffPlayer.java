package es.angelillo15.mast.bukkit;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.MAStaffInstance;
import es.angelillo15.mast.api.Permissions;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.database.sql.CommonQueries;
import es.angelillo15.mast.api.event.bukkit.freeze.FreezePlayerEvent;
import es.angelillo15.mast.api.event.bukkit.freeze.UnFreezePlayerEvent;
import es.angelillo15.mast.api.event.bukkit.staff.StaffDisableEvent;
import es.angelillo15.mast.api.event.bukkit.staff.StaffEnableEvent;
import es.angelillo15.mast.api.items.StaffItem;
import es.angelillo15.mast.api.managers.GlowManager;
import es.angelillo15.mast.api.managers.VanishedPlayers;
import es.angelillo15.mast.api.managers.freeze.FreezeManager;
import es.angelillo15.mast.bukkit.cmd.utils.CommandManager;
import es.angelillo15.mast.api.config.bukkit.Config;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.bukkit.gui.StaffVault;
import es.angelillo15.mast.bukkit.loaders.ItemsLoader;
import es.angelillo15.mast.bukkit.utils.PermsUtils;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import io.papermc.lib.PaperLib;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.MrGraycat.eGlow.API.Enum.EGlowColor;
import me.MrGraycat.eGlow.EGlow;
import me.MrGraycat.eGlow.Manager.Interface.IEGlowPlayer;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

@SuppressWarnings({"deprecation", "UnstableApiUsage", "unchecked"})
public class StaffPlayer implements IStaffPlayer {
    @Getter
    @Setter
    private boolean quit;
    @Getter
    private final File playerInventoryFile;
    @Getter
    private FileConfiguration playerInventoryConfig;
    private ChatColor glowColor = ChatColor.GREEN;
    private boolean staffMode;
    private final Player player;
    private boolean vanished;
    private final Map<String, StaffItem> items = new HashMap<>();

    public StaffPlayer(Player player) {
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
    public void setStaffMode(boolean staffMode, boolean saveInventory) {
        if (staffMode) disableStaffMode();
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

    public void setVanish(boolean vanish) {
        if (vanish) enableVanish();
        else disableVanish();
    }

    public void enableVanish() {
        VanishedPlayers.addPlayer(player);
        vanished = true;
        TextUtils.colorize(Messages.GET_VANISH_ENABLE_MESSAGE(), player);


        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p == player) continue;
            if (p.hasPermission(Permissions.STAFF_VANISH_SEE.getPermission())) continue;

            p.hidePlayer(player);
        }

    }

    public void disableVanish() {
        VanishedPlayers.removePlayer(player);
        vanished = false;
        TextUtils.colorize(Messages.GET_VANISH_DISABLE_MESSAGE(), player);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p == player) continue;
            if (p.hasPermission(Permissions.STAFF_VANISH_SEE.getPermission())) continue;
            p.showPlayer(player);
        }
    }

    public void disableStaffMode() {
        TextUtils.colorize(Messages.GET_STAFF_MODE_DISABLE_MESSAGE(), player);
        setModeData(false);
        clearInventory();
        CommonQueries.updateAsync(player.getUniqueId(), 0);
        staffMode = false;
        restoreInventory();
        disableVanish();
        changeGamemode(GameMode.SURVIVAL);
        if (!quit) StaffUtils.asyncBroadcastMessage(Messages.GET_VANISH_JOIN_MESSAGE()
                .replace("{player}", player.getName()));
        setGlowing(false);
        if (!restoreLocation()) {
            MAStaffInstance.getLogger().debug("Error restoring location for " + player.getName());
            // Todo send message of error to the player
        }
        Bukkit.getPluginManager().callEvent(new StaffDisableEvent(this));
    }

    public void enableStaffMode(boolean saveInventory) {
        TextUtils.colorize(Messages.GET_STAFF_MODE_ENABLE_MESSAGE(), player);
        setModeData(true);
        if (saveInventory) saveInventory();
        staffMode = true;
        enableVanish();
        clearInventory();
        setItems();
        CommonQueries.updateAsync(player.getUniqueId(), 1);
        changeGamemode(GameMode.CREATIVE);
        if (saveInventory) StaffUtils.asyncBroadcastMessage(Messages.GET_VANISH_LEAVE_MESSAGE()
                .replace("{player}", player.getName()));
        setGlowing(true);
        saveLocation();
        staffModeAsyncInventoryChecker();
        Bukkit.getPluginManager().callEvent(new StaffEnableEvent(this));
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void setItems() {
        if (items.isEmpty()) {
            ItemsLoader.getManager().getItems().forEach(item -> {
                if (player.hasPermission(item.getPermission())) {
                    item.setItem(player);
                    items.put(item.getItem().getItemMeta().getDisplayName(), item);
                }
            });
            return;
        }

        items.forEach((key, item) -> item.setItem(player));
    }

    @Override
    public Map<String, StaffItem> getItems() {
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
        ItemStack[] content = ((List<ItemStack>) Objects.requireNonNull(
                playerInventoryConfig.get("inventory.armor")
        )).toArray(new ItemStack[0]);
        player.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) Objects.requireNonNull(
                playerInventoryConfig.get("inventory.content"))
        ).toArray(new ItemStack[0]);
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
        if (status) enableGlowing();
        else disableGlowing();
    }

    public void enableGlowing() {
        if (!MAStaff.isGlowEnabled()) return;
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
        if (!MAStaff.isGlowEnabled()) return;
        IEGlowPlayer iegp = EGlow.getAPI().getEGlowPlayer(player);
        EGlow.getAPI().disableGlow(iegp);
    }

    @SneakyThrows
    public void setModeData(boolean staffMode) {
        this.playerInventoryConfig.set("Status.staffMode", staffMode);
        MAStaff.getPlugin().getPLogger().debug("Saving staff mode data for player " + player.getName() + " with value " + staffMode);
        this.playerInventoryConfig.save(playerInventoryFile);
    }

    @SneakyThrows
    public void saveLocation(){
        playerInventoryConfig.set("Location.world", player.getLocation().getWorld().getName());
        playerInventoryConfig.set("Location.x", player.getLocation().getX());
        playerInventoryConfig.set("Location.y", player.getLocation().getY());
        playerInventoryConfig.set("Location.z", player.getLocation().getZ());
        playerInventoryConfig.set("Location.yaw", player.getLocation().getYaw());
        playerInventoryConfig.set("Location.pitch", player.getLocation().getPitch());
        playerInventoryConfig.save(playerInventoryFile);
    }

    public boolean restoreLocation(){
        if(!(ConfigLoader.getConfig().getConfig().getBoolean("Config.teleportBack"))) return false;
        if(!playerInventoryConfig.contains("Location.world")) return false;
        World world = Bukkit.getWorld(Objects.requireNonNull(playerInventoryConfig.getString("Location.world")));

        if (world == null) return false;

        double x = playerInventoryConfig.getDouble("Location.x");
        double y = playerInventoryConfig.getDouble("Location.y");
        double z = playerInventoryConfig.getDouble("Location.z");
        float yaw = (float) playerInventoryConfig.getDouble("Location.yaw");
        float pitch = (float) playerInventoryConfig.getDouble("Location.pitch");

        Location location = new Location(world, x, y, z, yaw, pitch);

        if (world.getEnvironment() == World.Environment.NETHER) {
            PaperLib.teleportAsync(player, location);
            return true;
        }

        PaperLib.teleportAsync(player, world.getHighestBlockAt(location).getLocation().add(0, 1, 0));
        return true;
    }

    @Override
    public boolean wasInStaffMode() {
        return playerInventoryConfig.getBoolean("Status.staffMode");
    }

    @Override
    public void staffModeAsyncInventoryChecker() {
        if (!Config.StaffVault.enabled()) return;

        new Thread(() -> {
            MAStaffInstance.getLogger().debug("Starting staff mode inventory checker for " + player.getName());

            while (isStaffMode()) {
                try {
                    Thread.sleep(Config.StaffVault.checkTime() * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!player.isOnline() || !isStaffMode()) break;

                MAStaffInstance.getLogger().debug("Checking inventory for " + player.getName());

                player.getInventory().forEach(itemStack -> {
                    if (itemStack == null) return;
                    if (itemStack.getType() == Material.AIR) return;

                    if (itemStack.getItemMeta() == null) {
                        addItemToStaffVault(itemStack);
                        return;
                    }

                    if (!(itemStack.getItemMeta().hasDisplayName())) {
                        addItemToStaffVault(itemStack);
                        return;
                    }

                    if (!(items.containsKey(itemStack.getItemMeta().getDisplayName()))) {
                        addItemToStaffVault(itemStack);
                        return;
                    }
                });

                player.getInventory().clear();
                setItems();
            }
        }).start();
    }

    @SneakyThrows
    public void addItemToStaffVault(ItemStack item) {
        if (isStaffVaultFull()) {
            TextUtils.sendMessage(player, Messages.StaffVault.staffVaultIsFull());
            return;
        }

        MAStaffInstance.getLogger().debug("Added item " + item.getType().name() + " to staff vault for player " + player.getName());

        List<ItemStack> staffVault = new ArrayList<>();

        if (playerInventoryConfig.get("staffVault") != null) {
            staffVault = ((List<ItemStack>) Objects.requireNonNull(
                    playerInventoryConfig.get("staffVault")
            ));
        }

        staffVault.add(item);

        playerInventoryConfig.set("staffVault", staffVault);

        playerInventoryConfig.save(playerInventoryFile);

        TextUtils.sendMessage(player, Messages.StaffVault.itemSaved());
        TextUtils.colorize(Messages.StaffVault.itemSaved());
        MAStaffInstance.getLogger().debug("Saved staff vault for player " + player.getName());
    }

    public boolean isStaffVaultFull() {
        if (!Config.StaffVault.enabled()) return false;
        if (playerInventoryConfig.get("staffVault") == null) return false;
        return ((List<ItemStack>) playerInventoryConfig.get("staffVault")).size() >= 54;
    }

    @Override
    @Nullable
    public List<ItemStack> getStaffVault() {
        return ((List<ItemStack>) playerInventoryConfig.get("staffVault"));
    }

    @Override
    public void openStaffVault() {
        new StaffVault(player, 0).open();
    }

    @Override
    public void freezePlayer(Player player) {
        FreezeManager.freezePlayer(this, player);
        TextUtils.sendMessage(player, Messages.GET_FREEZE_FROZEN_MESSAGE());

        StaffUtils.asyncStaffBroadcastMessage(Messages.GET_FREEZE_FROZEN_BY_MESSAGE().replace("{player}",
                player.getName()).replace("{staff}",
                this.player.getName())
        );

        Bukkit.getPluginManager().callEvent(new FreezePlayerEvent(player, this.player));
    }

    @Override
    public void unfreezePlayer(String player) {
        FreezeManager.unfreezePlayer(player);


        StaffUtils.asyncStaffBroadcastMessage(Messages.GET_FREEZE_UNFROZEN_BY_MESSAGE()
                .replace("{player}", player)
                .replace("{staff}", this.player.getName())
        );

        if (Bukkit.getPlayer(player) != null && Objects.requireNonNull(Bukkit.getPlayer(player)).isOnline()) {
            TextUtils.sendMessage(Bukkit.getPlayer(player), Messages.GET_FREEZE_UNFROZEN_MESSAGE());
            Bukkit.getPluginManager().callEvent(new UnFreezePlayerEvent(Bukkit.getPlayer(player), this.player));
        }
    }

    @Override
    public void executeFreezedPunishments(String player) {
        if (!Config.Freeze.executeCommandOnExit()) return;
        if (Config.Freeze.commands().isEmpty()) return;

        Config.Freeze.commands().forEach(punishment -> {
            CommandManager.sendCommandToConsole(this.player, punishment
                    .replace("{player}", player)
                    .replace("{staff}", this.player.getName())
            );
        });

        FreezeManager.unfreezePlayer(player);
    }

    @Override
    public boolean isFreezed(Player player) {
        return FreezeManager.isFrozen(player);
    }
}
