package es.angelillo15.mast.api;

import es.angelillo15.mast.api.exceptions.AlreadyDisableException;
import es.angelillo15.mast.api.exceptions.AlreadyEnableException;
import es.angelillo15.mast.api.items.StaffItem;
import es.angelillo15.mast.api.player.IGlowPlayer;
import es.angelillo15.mast.api.player.IVanishPlayer;

import java.util.List;
import java.util.Map;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This interface allows to you to interact with the staff players
 */
public interface IStaffPlayer {

  /**
   * toggle the staff mode
   */
  void toggleStaffMode();

  /**
   * toggle the staff mode
   */
  void toggleStaffMode(boolean saveInventory);

  /**
   * @return boolean
   * Returns true if the player is in StaffMode
   */
  boolean isStaffMode();

  /**
   * @param staffMode enable or disable the staff mode
   *                  Method to enable or disable the StaffMode,
   *                  ths method can throw exceptions
   */
  void setStaffMode(boolean staffMode, boolean saveInventory) throws AlreadyEnableException, AlreadyDisableException;

  /**
   * Toggle the vanish mode
   */
  void toggleVanish();

  /**
   * @return boolean
   * Returns true if the p
   */
  boolean isVanished();

  /**
   * @return the player
   * Method to get the Player under StaffPlayer
   */
  Player getPlayer();

  /**
   * Set staff items to the player
   */
  void setItems();

  /**
   * Get staff items
   */
  Map<String, StaffItem> getItems();

  /**
   * Send plugin message to the bungee
   */
  void sendPluginMessage();

  /**
   * Save inventory
   */
  void saveInventory();

  /**
   * Clear inventory
   */
  void clearInventory();

  /**
   * Restore inventory
   */
  void restoreInventory();

  /**
   * Exists data
   *
   * @return true if exists or false if not
   */
  boolean existsData();

  /**
   * Change gamemode
   *
   * @param gamemode gamemode to change
   */
  void changeGamemode(GameMode gamemode);

  /**
   * enable / disable glow
   *
   * @param status status to set
   */
  void setGlowing(boolean status);

  /**
   * Get the staff mode previus status
   *
   * @return the staff mode previus status
   */
  boolean wasInStaffMode();

  /**
   * Is a checker that if there is a new item that isn't
   * of the staff mode inventory, it will be removed
   * and added to a staff mode vault
   */
  void staffModeAsyncInventoryChecker();

  /**
   * Get staff vault
   *
   * @return the staff vault
   */
  List<ItemStack> getStaffVault();

  /**
   * Open staff vault
   */
  void openStaffVault();

  /**
   * Freeze a player
   *
   * @param player - player to freeze
   */
  void freezePlayer(Player player);

  /**
   * Unfreeze a player
   *
   * @param player - player to unfreeze
   */
  void unfreezePlayer(String player);

  /**
   * Execute punishs for a freezed player that has left the server
   *
   * @param player - player name to execute the punishs
   */
  void executeFreezedPunishments(String player);

  /**
   * Check if a player is freezed
   *
   * @param player - player to check
   */
  boolean isFreezed(Player player);

  /**
   * Execute staff mode commands
   */
  void executeStaffModeCommands();

  /**
   * Get the vanish player
   *
   * @return the vanish player or null if is disabled
   */
  IVanishPlayer getVanishPlayer();

  /**
   * Get the glow player
   *
   * @return the glow player or null if is disabled
   */
  IGlowPlayer getGlowPlayer();

  /**
   * Save health and food
   */
  void saveHealthAndFood();

  /**
   * Restore health and food
   */
  void restoreHealthAndFood();

  /**
   * Get the player name
   *
   * @return the player name
   */
  default String getName() {
    return getPlayer().getName();
  }

  /**
   * Send a message to the player
   * @param message - message to send
   */
  default void sendMessage(String message) {
    TextUtils.sendMessage(getPlayer(), TextUtils.toMM(message));
  }

  /**
   * Send a message to the player
   * @param component - message to send
   */
  default void sendMessage(Component component) {
    TextUtils.getAudience(getPlayer()).sendMessage(component);
  }
}
