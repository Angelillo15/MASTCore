package com.nookure.staff.paper.pin;

import com.google.inject.Inject;
import com.nookure.core.inv.paper.PaperNookureInventoryEngine;
import com.nookure.staff.api.Permissions;
import com.nookure.staff.api.command.Command;
import com.nookure.staff.api.command.CommandData;
import com.nookure.staff.api.command.CommandSender;
import com.nookure.staff.api.state.PinState;
import com.nookure.staff.paper.PaperPlayerWrapper;
import com.nookure.staff.paper.inventory.InventoryList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@CommandData(
    name = "setpin",
    permission = Permissions.SET_PIN_PERMISSION,
    description = "Set your pin"
)
public class SetPinCommand extends Command {
  @Inject
  private AtomicReference<PaperNookureInventoryEngine> engine;

  @Override
  public void onCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull List<String> args) {
    if (sender instanceof PaperPlayerWrapper player) {
      PinState pinState = player.getState().getState(PinState.class);

      if (pinState == null) {
        return;
      }

      engine.get().openAsync(player.getPlayer(), InventoryList.ENTER_PIN, "player", player.getPlayer(), "pinSize", pinState.getPin().length());
    }
  }
}
