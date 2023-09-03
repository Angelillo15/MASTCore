package es.angelillo15.mast.api.utils;

import es.angelillo15.mast.api.chat.api.chat.hover.content.Entity;

public interface IScheduler {
  public void execute(Runnable runnable);

  public void executeAsync(Runnable runnable);

  public void executeLater(Runnable runnable, long delay);

  public void executeLaterAsync(Runnable runnable, long delay);

  public void executeTimer(Runnable runnable, long delay, long period);

  public void executeTimerAsync(Runnable runnable, long delay, long period);

  public void cancelTask(int taskId);

  public void cancelAllTasks();

  public void entityTaskAsync(Entity entity, Runnable runnable, long delay, long period);
}
