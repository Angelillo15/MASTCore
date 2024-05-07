package com.nookure.staff.paper.task;

import com.google.inject.Inject;
import com.nookure.staff.api.config.ConfigurationContainer;
import com.nookure.staff.api.config.bukkit.BukkitMessages;
import com.nookure.staff.api.manager.FreezeManager;
import com.nookure.staff.api.manager.PlayerWrapperManager;
import com.nookure.staff.api.util.TextUtils;
import org.bukkit.entity.Player;

public class FreezeSpamMessage implements Runnable {
  @Inject
  private FreezeManager freezeManager;
  @Inject
  private PlayerWrapperManager<Player> playerWrapperManager;
  @Inject
  private ConfigurationContainer<BukkitMessages> messages;

  @Override
  public void run() {
    freezeManager.stream().forEach(container -> {
      if (container.hasTalked()) return;

      playerWrapperManager.getPlayerWrapper(container.target()).ifPresent(player -> {
        player.sendMiniMessage(TextUtils.toMM(messages.get().freeze.chatFrozenMessage()
            .replace("{time}",
                container.timeLeft() == -1 ? "∞" : TextUtils.formatTime(container.timeLeft() - System.currentTimeMillis()))
        ));
      });
    });
  }
}
