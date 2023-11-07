package es.angelillo15.mast.bukkit;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.nookure.mast.api.event.EventManager;
import com.nookure.mast.api.event.staff.freeze.PlayerFreezeEvent;
import com.nookure.mast.api.event.staff.freeze.PlayerUnfreezeEvent;
import com.nookure.mast.api.event.staff.mode.StaffModeDisabledEvent;
import com.nookure.mast.api.event.staff.mode.StaffModeEnabledEvent;
import com.nookure.mast.api.staff.StaffFeatureManager;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.IStaffPlayer;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.bukkit.Config;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.api.config.bukkit.Messages;
import es.angelillo15.mast.api.database.sql.CommonQueries;
import es.angelillo15.mast.api.event.bukkit.freeze.FreezePlayerEvent;
import es.angelillo15.mast.api.event.bukkit.freeze.UnFreezePlayerEvent;
import es.angelillo15.mast.api.event.bukkit.staff.StaffDisableEvent;
import es.angelillo15.mast.api.event.bukkit.staff.StaffEnableEvent;
import es.angelillo15.mast.api.items.StaffItem;
import es.angelillo15.mast.api.managers.ItemsManager;
import es.angelillo15.mast.api.managers.freeze.FreezeManager;
import es.angelillo15.mast.api.nms.VersionSupport;
import es.angelillo15.mast.api.player.IGlowPlayer;
import es.angelillo15.mast.api.player.IVanishPlayer;
import es.angelillo15.mast.api.utils.VersionUtils;
import es.angelillo15.mast.bukkit.cmd.utils.CommandManager;
import es.angelillo15.mast.bukkit.gui.StaffVault;
import es.angelillo15.mast.bukkit.utils.StaffUtils;
import es.angelillo15.mast.glow.GlowPlayer;
import es.angelillo15.mast.vanish.VanishPlayer;
import io.papermc.lib.PaperLib;

import java.io.File;
import java.util.*;
import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings({"deprecation", "UnstableApiUsage", "unchecked"})
public class StaffPlayer implements IStaffPlayer {
  @Inject
  private MAStaff instance;
  @Inject
  private ItemsManager itemsManager;
  @Inject
  private VersionSupport versionSupport;
  @Inject
  private ILogger logger;
  @Inject
  private StaffFeatureManager featureManager;
  @Inject
  private EventManager eventManager;
  @Inject
  private FreezeManager freezeManager;

  private final Map<String, StaffItem> items = new HashMap<>();
  @Getter
  @Setter
  private boolean quit;
  @Getter
  private File playerInventoryFile;
  @Getter
  private FileConfiguration playerInventoryConfig;
  private boolean staffMode;
  private Player player;
  private IGlowPlayer glowPlayer;
  private boolean vanished;
  private IVanishPlayer vanishPlayer;

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
    logger.debug("Toggling staff mode for " + player.getName() + " with saveInventory = " + saveInventory);
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
    if (vanished) return;
    vanished = true;

    if (vanishPlayer == null) {
      player.performCommand("vanish");
      return;
    }

    vanishPlayer.enableVanish();
    TextUtils.colorize(Messages.GET_VANISH_ENABLE_MESSAGE(), player);
  }

  public void disableVanish() {
    vanished = false;

    if (vanishPlayer == null) {
      player.performCommand("vanish");
      return;
    }

    vanishPlayer.disableVanish();
    TextUtils.colorize(Messages.GET_VANISH_DISABLE_MESSAGE(), player);
  }

  public void disableStaffMode() {
    TextUtils.colorize(Messages.GET_STAFF_MODE_DISABLE_MESSAGE(), player);
    player.setAllowFlight(false);
    removeEffects();
    setModeData(false);
    clearInventory();
    CommonQueries.updateAsync(player.getUniqueId(), 0);
    staffMode = false;
    restoreInventory();
    restoreHealthAndFood();
    disableVanish();
    changeGamemode(GameMode.SURVIVAL);
    if (!quit)
      StaffUtils.asyncBroadcastMessage(
          Messages.GET_VANISH_JOIN_MESSAGE().replace("{player}", player.getName()));
    setGlowing(false);
    if (!restoreLocation()) {
      logger.debug("Error restoring location for " + player.getName());
      // Todo send message of error to the player
    }
    Bukkit.getPluginManager().callEvent(new StaffDisableEvent(this));
    disableStaffFeatures();
    eventManager.fireEvent(new StaffModeDisabledEvent(this));
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
    changeGamemode(GameMode.SURVIVAL);
    if (saveInventory)
      StaffUtils.asyncBroadcastMessage(
          Messages.GET_VANISH_LEAVE_MESSAGE().replace("{player}", player.getName()));
    setGlowing(true);
    addEffects();
    player.setAllowFlight(true);
    saveHealthAndFood();
    saveLocation();
    staffModeAsyncInventoryChecker();
    Bukkit.getPluginManager().callEvent(new StaffEnableEvent(this));
    enableStaffFeatures();
    eventManager.fireEvent(new StaffModeEnabledEvent(this));
  }

  @Override
  public Player getPlayer() {
    return this.player;
  }

  public StaffPlayer setPlayer(Player player) {
    this.player = player;
    if (Config.Addons.vanish())
      this.vanishPlayer = instance.getInjector().getInstance(VanishPlayer.class).createStaffPlayer(this);

    if (VersionUtils.getBukkitVersion() > 8) {
      if (Config.Addons.glow()
          && !(MAStaff.getPlugin().getDescription().getPrefix() != null
          && MAStaff.getPlugin().getDescription().getPrefix().toLowerCase().contains("lite")))
        this.glowPlayer = new GlowPlayer(this);
    }

    playerInventoryFile =
        new File(
            MAStaff.getPlugin().getDataFolder().getAbsoluteFile()
                + "/data/staffMode/"
                + player.getUniqueId()
                + ".yml");
    playerInventoryConfig = YamlConfiguration.loadConfiguration(playerInventoryFile);

    return this;
  }

  @Override
  public void setItems() {
    if (items.isEmpty()) {
      itemsManager.getItems()
          .values()
          .forEach(
              item -> {
                if (player.hasPermission(item.getPermission())) {
                  item.setItem(player);
                  items.put(item.getName(), item);
                }
              });
      return;
    }
    player.getInventory().clear();
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

    player.sendPluginMessage(MAStaff.getPlugin(), "mastaff:staff", out.toByteArray());
  }

  @SneakyThrows
  @Override
  public void saveInventory() {
    logger.debug("Saving inventory for " + player.getName());
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
    logger.debug("Restoring inventory for " + player.getName());
    playerInventoryConfig = YamlConfiguration.loadConfiguration(playerInventoryFile);
    ItemStack[] content =
        ((List<ItemStack>) Objects.requireNonNull(playerInventoryConfig.get("inventory.armor")))
            .toArray(new ItemStack[0]);
    player.getInventory().setArmorContents(content);
    content =
        ((List<ItemStack>) Objects.requireNonNull(playerInventoryConfig.get("inventory.content")))
            .toArray(new ItemStack[0]);
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
  public void setGlowing(boolean status) {
    if (glowPlayer == null) return;

    if (status) this.glowPlayer.enableGlow();
    else this.glowPlayer.disableGlow();
  }

  @SneakyThrows
  public void setModeData(boolean staffMode) {
    this.playerInventoryConfig.set("Status.staffMode", staffMode);
    MAStaff.getPlugin()
        .getPLogger()
        .debug(
            "Saving staff mode data for player " + player.getName() + " with value " + staffMode);
    this.playerInventoryConfig.save(playerInventoryFile);
  }

  @SneakyThrows
  public void saveLocation() {
    playerInventoryConfig.set("Location.world", player.getLocation().getWorld().getName());
    playerInventoryConfig.set("Location.x", player.getLocation().getX());
    playerInventoryConfig.set("Location.y", player.getLocation().getY());
    playerInventoryConfig.set("Location.z", player.getLocation().getZ());
    playerInventoryConfig.set("Location.yaw", player.getLocation().getYaw());
    playerInventoryConfig.set("Location.pitch", player.getLocation().getPitch());
    playerInventoryConfig.save(playerInventoryFile);
  }

  public boolean restoreLocation() {
    if (!(ConfigLoader.getConfig().getConfig().getBoolean("Config.teleportBack"))) return false;
    if (!playerInventoryConfig.contains("Location.world")) return false;
    World world =
        Bukkit.getWorld(Objects.requireNonNull(playerInventoryConfig.getString("Location.world")));

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
      logger.debug("Starting staff mode inventory checker for " + player.getName());

      while (isStaffMode()) {
        try {
          Thread.sleep(Config.StaffVault.checkTime() * 1000L);
        } catch (InterruptedException e) {
          logger.debug("Error while sleeping thread for " + player.getName());
        }
        if (!player.isOnline() || !isStaffMode()) break;
        player.getInventory().forEach(itemStack -> {
          if (itemStack == null) return;
          if (itemStack.getType() == Material.AIR) return;
          if (versionSupport.getTag(itemStack, "mast-staff-item") != null) return;

          addItemToStaffVault(itemStack);
          player.getInventory().clear();
          setItems();
        });
      }
    }).start();
  }

  @SneakyThrows
  public void addItemToStaffVault(ItemStack item) {
    if (isStaffVaultFull()) {
      TextUtils.sendMessage(player, Messages.StaffVault.staffVaultIsFull());
      return;
    }

    if (!this.isStaffMode() || !this.player.isOnline()) return;

    logger.debug("Added item " + item.getType().name() + " to staff vault for player " + player.getName());

    List<ItemStack> staffVault = new ArrayList<>();

    if (playerInventoryConfig.get("staffVault") != null) {
      staffVault =
          ((List<ItemStack>) Objects.requireNonNull(playerInventoryConfig.get("staffVault")));
    }

    staffVault.add(item);

    playerInventoryConfig.set("staffVault", staffVault);

    playerInventoryConfig.save(playerInventoryFile);

    TextUtils.sendMessage(player, Messages.StaffVault.itemSaved());
    TextUtils.colorize(Messages.StaffVault.itemSaved());
    logger.debug("Saved staff vault for player " + player.getName());
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
    freezeManager.freezePlayer(this, player);
    TextUtils.sendMessage(player, Messages.GET_FREEZE_FROZEN_MESSAGE());

    StaffUtils.asyncStaffBroadcastMessage(
        Messages.GET_FREEZE_FROZEN_BY_MESSAGE()
            .replace("{player}", player.getName())
            .replace("{staff}", this.player.getName()));

    Bukkit.getPluginManager().callEvent(new FreezePlayerEvent(player, this.player));
    eventManager.fireEvent(new PlayerFreezeEvent(this.player.getName(), player.getName()));
  }

  @Override
  public void unfreezePlayer(String player) {
    freezeManager.unfreezePlayer(player);

    StaffUtils.asyncStaffBroadcastMessage(
        Messages.GET_FREEZE_UNFROZEN_BY_MESSAGE()
            .replace("{player}", player)
            .replace("{staff}", this.player.getName()));

    if (Bukkit.getPlayer(player) != null
        && Objects.requireNonNull(Bukkit.getPlayer(player)).isOnline()) {
      TextUtils.sendMessage(Bukkit.getPlayer(player), Messages.GET_FREEZE_UNFROZEN_MESSAGE());
      Bukkit.getPluginManager()
          .callEvent(new UnFreezePlayerEvent(Bukkit.getPlayer(player), this.player));
    }

    eventManager.fireEvent(new PlayerUnfreezeEvent(this.player.getName(), player));
  }

  @Override
  public void executeFreezedPunishments(String player) {
    if (MAStaff.isFree()) return;
    if (!Config.Freeze.executeCommandOnExit()) return;
    if (Config.Freeze.commands().isEmpty()) return;

    Config.Freeze.commands()
        .forEach(
            punishment -> {
              CommandManager.sendCommandToConsole(
                  this.player,
                  punishment.replace("{player}", player).replace("{staff}", this.player.getName()));
            });

    freezeManager.unfreezePlayer(player);
  }

  @Override
  public void executeStaffModeCommands() {
    if (staffMode && Config.executeCommandsOnEnter()) {
      sendEnableCommands();
      return;
    }

    if (!staffMode && Config.executeCommandsOnExit()) sendQuitCommands();
  }

  public void sendEnableCommands() {
    Config.commandsOnEnter()
        .forEach(
            command -> {
              player.performCommand(command.replace("{player}", player.getName()));
            });
  }

  public void sendQuitCommands() {
    Config.commandsOnExit()
        .forEach(
            command -> {
              player.performCommand(command.replace("{player}", player.getName()));
            });
  }

  @Override
  public boolean isFreezed(Player player) {
    return freezeManager.isFrozen(player);
  }

  @Override
  public IVanishPlayer getVanishPlayer() {
    return vanishPlayer;
  }

  @Override
  public IGlowPlayer getGlowPlayer() {
    return glowPlayer;
  }

  public void removeEffects() {
    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    getPotionEffectType().forEach(potionEffectType -> player.removePotionEffect(potionEffectType));
  }

  public void addEffects() {
    if (!Config.nighVision()) return;

    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 1));

    if (!Config.customPotionEffects()) return;

    Config.potionEffects().forEach(potionEffect -> {
      String[] split = potionEffect.split(":");
      String potionEffectName = split[0];
      int amplifier = Integer.parseInt(split[1]);
      int duration = Integer.parseInt(split[2]);

      PotionEffectType potionEffectType = PotionEffectType.getByName(potionEffectName);

      if (potionEffectType == null) {
        logger.error("Potion effect " + potionEffectName + " not found");
        return;
      }

      PotionEffect effect = new PotionEffect(potionEffectType, duration, amplifier);
      player.addPotionEffect(effect);
    });
  }

  public List<PotionEffectType> getPotionEffectType() {
    List<PotionEffectType> potionEffectTypes = new ArrayList<>();

    Config.potionEffects().forEach(potionEffect -> {
      String[] split = potionEffect.split(":");
      String potionEffectName = split[0];

      PotionEffectType potionEffectType = PotionEffectType.getByName(potionEffectName);

      if (potionEffectType == null) {
        logger.error("Potion effect " + potionEffectName + " not found");
        return;
      }

      potionEffectTypes.add(potionEffectType);
    });

    return potionEffectTypes;
  }

  @Override
  public void saveHealthAndFood() {
    playerInventoryConfig.set("health", player.getHealth());
    playerInventoryConfig.set("food", player.getFoodLevel());
    player.setHealth(20);
    player.setFoodLevel(20);
  }

  @Override
  public void restoreHealthAndFood() {
    if (!playerInventoryConfig.contains("health")) return;
    player.setFoodLevel(playerInventoryConfig.getInt("food"));
    val health = playerInventoryConfig.getDouble("health");
    if (health > 20)
      player.setHealth(20);
    else
      player.setHealth(health);
  }

  public void enableStaffFeatures() {
    featureManager.getFeatures().forEach(featureContainer -> {
      try {
        if (featureContainer.permission() == null || player.hasPermission(featureContainer.permission()))
          featureContainer.feature().onStaffEnable(this);
      } catch (Exception e) {
        logger.error("Error while enabling feature " + featureContainer.feature().getClass().getSimpleName());
        if (featureContainer.hasAddon()) {
          assert featureContainer.addon() != null;
          logger.error("Error occurred in addon " + featureContainer.addon().getDescription().getID());
          logger.error("This error is not caused by MAStaff, please contact the addon developer");
        }
        logger.error("Error: " + e.getMessage());
      }
    });
  }

  public void disableStaffFeatures() {
    featureManager.getFeatures(this).forEach(featureContainer -> {
      try {
        featureContainer.feature().onStaffDisable(this);
      } catch (Exception e) {
        logger.error("Error while disabling feature " + featureContainer.feature().getClass().getSimpleName());
        if (featureContainer.hasAddon()) {
          assert featureContainer.addon() != null;
          logger.error("Error occurred in addon " + featureContainer.addon().getDescription().getID());
          logger.error("This error is not caused by MAStaff, please contact the addon developer");
        }
        logger.error("Error: " + e.getMessage());
      }
    });
  }
}
