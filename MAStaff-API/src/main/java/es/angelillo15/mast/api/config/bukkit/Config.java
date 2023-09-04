package es.angelillo15.mast.api.config.bukkit;


import java.util.List;

public class Config {
  public static String language() {
    return ConfigLoader.getConfig().getConfig().getString("Config.language");
  }

  public static boolean debug() {
    return ConfigLoader.getConfig().getConfig().getBoolean("Config.debug");
  }

  public static String teleportBack() {
    return ConfigLoader.getConfig().getConfig().getString("Config.teleportBack");
  }

  public static boolean disableStaffModeOnExit() {
    return ConfigLoader.getConfig().getConfig().getBoolean("Config.disableStaffModeOnExit");
  }

  public static boolean silentOpenChest() {
    return ConfigLoader.getConfig().getConfig().getBoolean("Config.silentChestOpening");
  }

  public static boolean executeCommandsOnEnter() {
    return ConfigLoader.getConfig().getConfig().getBoolean("Config.executeCommandsOnEnter");
  }

  public static boolean executeCommandsOnExit() {
    return ConfigLoader.getConfig().getConfig().getBoolean("Config.executeCommandsOnExit");
  }

  public static List<String> commandsOnEnter() {
    return ConfigLoader.getConfig().getConfig().getStringList("Config.commandsOnEnter");
  }

  public static List<String> commandsOnExit() {
    return ConfigLoader.getConfig().getConfig().getStringList("Config.commandsOnExit");
  }

  public static boolean customPotionEffects() {
    return ConfigLoader.getConfig().getConfig().getBoolean("Config.customPotionEffects");
  }

  public static List<String> potionEffects() {
    return ConfigLoader.getConfig().getConfig().getStringList("Config.potionEffects");
  }

  public static boolean nighVision() {
    return ConfigLoader.getConfig().getConfig().getBoolean("Config.nightVision");
  }

  public static class Database {
    public static String type() {
      return ConfigLoader.getConfig().getConfig().getString("Database.type");
    }

    public static String host() {
      return ConfigLoader.getConfig().getConfig().getString("Database.host");
    }

    public static int port() {
      return ConfigLoader.getConfig().getConfig().getInt("Database.port");
    }

    public static String database() {
      return ConfigLoader.getConfig().getConfig().getString("Database.database");
    }

    public static String username() {
      return ConfigLoader.getConfig().getConfig().getString("Database.user");
    }

    public static String password() {
      return ConfigLoader.getConfig().getConfig().getString("Database.password");
    }
  }

  public static class StaffVault {
    public static boolean enabled() {
      return ConfigLoader.getConfig().getConfig().getBoolean("StaffVault.enabled");
    }

    public static String name() {
      return ConfigLoader.getConfig().getConfig().getString("StaffVault.name");
    }

    public static int checkTime() {
      return ConfigLoader.getConfig().getConfig().getInt("StaffVault.checkTime");
    }
  }

  public static class Freeze {
    public static boolean enabled() {
      return ConfigLoader.getConfig().getConfig().getBoolean("Addons.freeze");
    }

    public static boolean executeCommandOnExit() {
      return ConfigLoader.getConfig().getConfig().getBoolean("Freeze.executeCommandOnExit");
    }

    public static boolean askToExecuteCommands() {
      return ConfigLoader.getConfig().getConfig().getBoolean("Freeze.askToExecuteCommands");
    }

    public static List<String> commands() {
      return ConfigLoader.getConfig().getConfig().getStringList("Freeze.commands");
    }
  }

  public static class Addons {
    public static boolean vanish() {
      return ConfigLoader.getConfig().getConfig().getBoolean("Addons.vanish");
    }

    public static boolean freeze() {
      return ConfigLoader.getConfig().getConfig().getBoolean("Addons.freeze");
    }

    public static boolean glow() {
      return ConfigLoader.getConfig().getConfig().getBoolean("Addons.glow");
    }
  }

  public static class Vanish {
    public static boolean appearAsSpectator() {
      return ConfigLoader.getConfig().getConfig().getBoolean("Vanish.appearAsSpectator");
    }
  }
}
