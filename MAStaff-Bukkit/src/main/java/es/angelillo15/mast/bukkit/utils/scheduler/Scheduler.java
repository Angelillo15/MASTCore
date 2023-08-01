package es.angelillo15.mast.bukkit.utils.scheduler;

import es.angelillo15.mast.api.utils.IScheduler;
import org.bukkit.Bukkit;

public class Scheduler {
    private static IScheduler scheduler;
    public static void init() {
        if (Bukkit.getServer().getName().equalsIgnoreCase("Folia")) {
            scheduler = new FoliaScheduler();
        } else {
            scheduler = new BukkitScheduler();
        }
    }
    public static void execute(Runnable runnable) {
        scheduler.execute(runnable);
    }

    public static void executeAsync(Runnable runnable) {
        scheduler.executeAsync(runnable);
    }

    public static void executeLater(Runnable runnable, long delay) {
        scheduler.executeLater(runnable, delay);
    }

    public static void executeLaterAsync(Runnable runnable, long delay) {
        scheduler.executeLaterAsync(runnable, delay);
    }

    public static void executeTimer(Runnable runnable, long delay, long period) {
        scheduler.executeTimer(runnable, delay, period);
    }

    public static void executeTimerAsync(Runnable runnable, long delay, long period) {
        scheduler.executeTimerAsync(runnable, delay, period);
    }

    public static void cancelTask(int taskId) {
        scheduler.cancelTask(taskId);
    }

    public static void cancelAllTasks() {
        scheduler.cancelAllTasks();
    }
}
