package es.angelillo15.mast.bukkit.utils.scheduler;

import es.angelillo15.mast.api.utils.IScheduler;
import es.angelillo15.mast.bukkit.MAStaff;
import org.bukkit.Bukkit;

public class BukkitScheduler implements IScheduler {
    @Override
    public void execute(Runnable runnable) {
        Bukkit.getScheduler().runTask(MAStaff.getPlugin(), runnable);
    }

    @Override
    public void executeAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(MAStaff.getPlugin(), runnable);
    }

    @Override
    public void executeLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(MAStaff.getPlugin(), runnable, delay);
    }

    @Override
    public void executeLaterAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(MAStaff.getPlugin(), runnable, delay);
    }

    @Override
    public void executeTimer(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(MAStaff.getPlugin(), runnable, delay, period);
    }

    @Override
    public void executeTimerAsync(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(MAStaff.getPlugin(), runnable, delay, period);
    }

    @Override
    public void cancelTask(int taskId) {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public void cancelAllTasks() {
        Bukkit.getScheduler().cancelTasks(MAStaff.getPlugin());
    }
}
