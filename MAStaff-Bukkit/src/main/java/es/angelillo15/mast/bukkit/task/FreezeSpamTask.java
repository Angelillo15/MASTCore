package es.angelillo15.mast.bukkit.task;

import com.google.inject.Inject;
import com.nookure.mast.api.manager.FreezeManager;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.bukkit.Messages;
import org.bukkit.entity.Player;

public class FreezeSpamTask implements Runnable {
  @Inject
  private FreezeManager freezeManager;

  @Override
  public void run() {
    freezeManager.getFreezeVectors().forEach(player -> {
      if (player.hasTalked()) return;

      Messages.spamMessage().forEach(message -> {
        if (message == null) return;

        message = message.replace(
            "{time}",
            player.getTimeLeft() == -1 ? "∞" : TextUtils.formatUptime(player.getTimeLeft() - System.currentTimeMillis())
        );

        if (player.getTarget() instanceof Player bp) {
          TextUtils.colorize(message, bp);
        }
      });

    });
  }
}
