package es.angelillo15.mast.bukkit.utils.scheduler;

import es.angelillo15.mast.api.chat.api.chat.hover.content.Entity;
import es.angelillo15.mast.api.utils.IScheduler;

public class FoliaScheduler implements IScheduler {
  @Override
  public void execute(Runnable runnable) {
    runnable.run();
  }

  @Override
  public void executeAsync(Runnable runnable) {
    runnable.run();
  }

  @Override
  public void executeLater(Runnable runnable, long delay) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void executeLaterAsync(Runnable runnable, long delay) {
    new Thread(
            () -> {
              try {
                Thread.sleep(delay);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              runnable.run();
            })
        .start();
  }

  @Override
  public void executeTimer(Runnable runnable, long delay, long period) {
    new Thread(
            () -> {
              try {
                Thread.sleep(delay);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              while (true) {
                runnable.run();
                try {
                  Thread.sleep(period * 50);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            })
        .start();
  }

  @Override
  public void executeTimerAsync(Runnable runnable, long delay, long period) {
    executeTimer(runnable, delay, period);
  }

  @Override
  public void cancelTask(int taskId) {}

  @Override
  public void cancelAllTasks() {}

  @Override
  public void entityTaskAsync(Entity entity, Runnable runnable, long delay, long period) {}
}
