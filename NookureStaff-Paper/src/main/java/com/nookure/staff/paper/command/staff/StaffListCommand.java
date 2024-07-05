package com.nookure.staff.paper.command.staff;

import com.google.inject.Inject;
import com.nookure.core.inv.paper.PaperNookureInventoryEngine;
import com.nookure.staff.api.StaffPlayerWrapper;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.StaffCommand;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.paper.StaffPaperPlayerWrapper;
import com.nookure.staff.paper.inventory.InventoryList;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@CommandData(
    name = "list",
    description = "List all staff members",
    permission = "nookurestaff.staff.list"
)
public class StaffListCommand extends StaffCommand {
  @Inject
  private AtomicReference<PaperNookureInventoryEngine> engine;
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;

  @Override
  protected void onStaffCommand(@NotNull StaffPlayerWrapper sender, @NotNull String label, @NotNull List<String> args) {
    if (sender instanceof StaffPaperPlayerWrapper staff) {
      engine.get().openAsync(
          staff.getPlayer(),
          InventoryList.STAFF_LIST,
          "player",
          staff,
          "players",
          playerWrapperManager.stream().filter(player -> player instanceof StaffPaperPlayerWrapper).toList(),
          "page",
          1
      );
    }
  }
}
