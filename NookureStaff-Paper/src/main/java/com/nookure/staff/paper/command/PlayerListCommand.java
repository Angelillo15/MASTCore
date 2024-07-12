package com.nookure.staff.paper.command;

import com.google.inject.Inject;
import com.nookure.core.inv.paper.PaperNookureInventoryEngine;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.command.Command;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.CommandSender;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.paper.PaperPlayerWrapper;
import com.nookure.staff.paper.inventory.InventoryList;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@CommandData(
    name = "player-list",
    permission = Permissions.PLAYER_LIST_PERMISSION,
    description = "List all the players on the server."
)
public class PlayerListCommand extends Command {
  @Inject
  private AtomicReference<PaperNookureInventoryEngine> engine;
  @Inject
  private PlayerWrapperManager<Player> wrapper;

  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {
    if (sender instanceof PaperPlayerWrapper player) {
      engine.get().openAsync(player.getPlayer(), InventoryList.PLAYER_LIST, "players", wrapper.stream().toList(), "page", 1);
    }
  }
}
