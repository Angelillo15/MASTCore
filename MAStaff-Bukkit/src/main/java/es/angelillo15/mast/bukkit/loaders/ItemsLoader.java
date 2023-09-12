package es.angelillo15.mast.bukkit.loaders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.api.items.ItemTypes;
import es.angelillo15.mast.api.managers.ItemsManager;
import es.angelillo15.mast.api.material.XMaterial;
import es.angelillo15.mast.api.nms.VersionSupport;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.items.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.ArrayList;
import java.util.Objects;

@Singleton
public class ItemsLoader {
  @Inject
  private ItemsManager manager;
  @Inject
  private VersionSupport versionSupport;
  @Inject
  private ILogger logger;

  public void load() {
    YamlFile items = ConfigLoader.getInternalStaffItems().getConfig();

    manager.clearItems();

    items.getConfigurationSection("StaffItems").getKeys(false).forEach(s -> {
      logger.debug("Loading item " + s);

      if (!(items.getBoolean("StaffItems." + s + ".enabled"))) {
        logger.debug("Item " + s + " is disabled, skipping...");
        return;
      }

      XMaterial material = XMaterial.valueOf(items.getString("StaffItems." + s + ".material"));
      int slot = items.getInt("StaffItems." + s + ".slot");
      ItemStack itemStack = new ItemStack(Objects.requireNonNull(material.parseMaterial()));
      itemStack = versionSupport.setTag(itemStack, "mast-staff-item", s);

      String permission = items.getString("StaffItems." + s + ".permission");

      ItemMeta meta = itemStack.getItemMeta();

      if (meta == null) {
        MAStaff.getPlugin().getPLogger().error("AIR item found in config, skipping...");
        return;
      }

      meta.setDisplayName(TextUtils.colorize(items.getString("StaffItems." + s + ".name")));

      ArrayList<String> lore = new ArrayList<>();
      for (String l : items.getStringList("StaffItems." + s + ".lore")) {
        lore.add(TextUtils.colorize(l));
      }

      meta.setLore(lore);

      itemStack.setItemMeta(meta);

      switch (ItemTypes.valueOf(s)) {
        case FREEZE -> manager.addItem(new FreezeItem(itemStack, slot, permission), s);
        case VANISH -> manager.addItem(new VanishItem(itemStack, slot, permission), s);
        case ENDER_CHEST -> manager.addItem(new EnderChestItem(itemStack, slot, permission), s);
        case CHEST -> manager.addItem(new ChestItem(itemStack, slot, permission), s);
        case THRU -> manager.addItem(new ThruItem(itemStack, slot, permission), s);
        case RANDOM_PLAYER_TELEPORT -> manager.addItem(new RTPItem(itemStack, slot, permission), s);
        case NIGHT_VISION -> manager.addItem(new NightVisionItem(itemStack, slot, permission), s);
        default -> throw new TypeNotPresentException("Item type not found", null);
      }
    });

    manager
        .getItems()
        .forEach((key, item) -> MAStaff.getPlugin().getPLogger().debug("Item loaded: " + key));
  }
}
