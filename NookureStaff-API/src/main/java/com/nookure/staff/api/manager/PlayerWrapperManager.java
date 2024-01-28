package com.nookure.staff.api.manager;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.inject.Singleton;
import com.nookure.staff.api.PlayerWrapper;
import com.nookure.staff.api.StaffPlayerWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Manages all the player wrappers.
 * <p>
 * You can get the instance of this class by injecting it,
 * see {@link com.google.inject.Injector#getInstance(Class)}
 * <p>
 *
 * @param <T> The player class of the player wrapper implementation
 *            (e.g. PlayerWrapper<Player> or PlayerWrapper<ProxiedPlayer>)
 *            This is used to get the player wrapper by its player class.
 *            But you can also use the unique id of the player wrapper.
 *            see {@link #getPlayerWrapper(UUID)}
 * @see PlayerWrapper for more information about player wrappers
 * @see StaffPlayerWrapper for more information about staff players
 * @since 1.0.0
 */
@Singleton
public class PlayerWrapperManager<T> {
  private final BiMap<T, PlayerWrapper> playerWrappersByPlayerClass = HashBiMap.create();
  private final LinkedHashMap<UUID, PlayerWrapper> playerWrappersByUUID = new LinkedHashMap<>();
  private final ArrayList<UUID> staffPlayers = new ArrayList<>();

  /**
   * Gets a player wrapper by its player class.
   *
   * @param player the player class of the player wrapper
   * @return an optional containing the player wrapper if it exists
   */
  @NotNull
  public Optional<PlayerWrapper> getPlayerWrapper(@NotNull T player) {
    Objects.requireNonNull(player, "Player cannot be null");
    return Optional.ofNullable(playerWrappersByPlayerClass.get(player));
  }

  /**
   * Gets a player wrapper by its unique id.
   *
   * @param uuid the unique id of the player wrapper
   * @return an optional containing the player wrapper if it exists
   */
  @NotNull
  public Optional<PlayerWrapper> getPlayerWrapper(@NotNull UUID uuid) {
    Objects.requireNonNull(uuid, "UUID cannot be null");
    return Optional.ofNullable(playerWrappersByUUID.get(uuid));
  }

  /**
   * Gets a staff player by its player class.
   *
   * @param uuid the unique id of the staff player
   * @return an optional containing the staff player if it exists
   */
  @NotNull
  public Optional<StaffPlayerWrapper> getStaffPlayer(@NotNull UUID uuid) {
    Objects.requireNonNull(uuid, "UUID cannot be null");
    Optional<PlayerWrapper> optionalPlayerWrapper = getPlayerWrapper(uuid);

    if (optionalPlayerWrapper.isPresent()) {
      PlayerWrapper playerWrapper = optionalPlayerWrapper.get();

      if (playerWrapper instanceof StaffPlayerWrapper) {
        return Optional.of((StaffPlayerWrapper) playerWrapper);
      }
    }

    return Optional.empty();
  }

  /**
   * Gets a player by its player wrapper.
   *
   * @param playerWrapper the player wrapper of the player
   * @return an optional containing the player if it exists
   */
  @NotNull
  public Optional<T> getPlayer(@NotNull PlayerWrapper playerWrapper) {
    Objects.requireNonNull(playerWrapper, "PlayerWrapper cannot be null");
    return Optional.ofNullable(playerWrappersByPlayerClass.inverse().get(playerWrapper));
  }

  /**
   * Adds a player wrapper to the manager.
   *
   * @param player        the player that will be used as a key
   * @param playerWrapper the player wrapper that will be used as a value
   * @param isStaff       whether the player is a staff player or not
   */
  public void addPlayerWrapper(@NotNull T player, @NotNull PlayerWrapper playerWrapper, boolean isStaff) {
    Objects.requireNonNull(player, "Player cannot be null");
    Objects.requireNonNull(playerWrapper, "PlayerWrapper cannot be null");

    playerWrappersByPlayerClass.put(player, playerWrapper);
    playerWrappersByUUID.put(playerWrapper.getUniqueId(), playerWrapper);

    if (isStaff) staffPlayers.add(playerWrapper.getUniqueId());
  }

  /**
   * Adds a player wrapper to the manager without being a staff player.
   *
   * @param player        the player that will be used as a key
   * @param playerWrapper the player wrapper that will be used as a value
   */
  public void addPlayerWrapper(@NotNull T player, @NotNull PlayerWrapper playerWrapper) {
    addPlayerWrapper(player, playerWrapper, false);
  }

  /**
   * Removes a player wrapper from the manager.
   *
   * @param player the player that will be used as a key
   */
  public void removePlayerWrapper(@NotNull T player) {
    Objects.requireNonNull(player, "Player cannot be null");

    PlayerWrapper playerWrapper = playerWrappersByPlayerClass.remove(player);
    playerWrappersByUUID.remove(playerWrapper.getUniqueId());

    if (playerWrapper instanceof StaffPlayerWrapper) staffPlayers.remove(playerWrapper.getUniqueId());
  }

  public boolean isStaffPlayer(@NotNull UUID uuid) {
    Objects.requireNonNull(uuid, "UUID cannot be null");
    return staffPlayers.contains(uuid);
  }

  /**
   * Clears all the mappings from the manager.
   */
  public void clear() {
    playerWrappersByPlayerClass.clear();
    playerWrappersByUUID.clear();
    staffPlayers.clear();
  }
}
