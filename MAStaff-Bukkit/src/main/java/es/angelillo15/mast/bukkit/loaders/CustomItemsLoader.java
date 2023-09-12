package es.angelillo15.mast.bukkit.loaders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.api.items.ItemTypes;
import es.angelillo15.mast.api.managers.ItemsManager;
import es.angelillo15.mast.api.material.XMaterial;
import es.angelillo15.mast.api.nms.VersionSupport;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.items.custom.CustomCommandInteractionItem;
import es.angelillo15.mast.bukkit.items.custom.CustomCommandItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.ArrayList;
import java.util.Objects;

@Singleton
public class CustomItemsLoader {
  @Inject
  private ItemsManager manager;
  @Inject
  private VersionSupport versionSupport;

  public void load() {
    YamlFile items = ConfigLoader.getCustomItems().getConfig();

    for (String s : items.getConfigurationSection("StaffItems").getKeys(false)) {
      if (!(items.getBoolean("StaffItems." + s + ".enabled"))) {
        continue;
      }

      XMaterial material = XMaterial.valueOf(items.getString("StaffItems." + s + ".material"));
      int slot = items.getInt("StaffItems." + s + ".slot");
      ItemStack itemStack = new ItemStack(Objects.requireNonNull(material.parseMaterial()));
      String permission = items.getString("StaffItems." + s + ".permission");

      ItemMeta meta = itemStack.getItemMeta();

      meta.setDisplayName(TextUtils.colorize(items.getString("StaffItems." + s + ".name")));

      ArrayList<String> lore = new ArrayList<>();
      for (String l : items.getStringList("StaffItems." + s + ".lore")) {
        lore.add(TextUtils.colorize(l));
      }

      meta.setLore(lore);

      itemStack.setItemMeta(meta);

      itemStack = versionSupport.setTag(itemStack, "mast-staff-item", s);

      MAStaff.getPlugin().getPLogger().debug("Loading custom item: " + s);
      MAStaff.getPlugin()
          .getPLogger()
          .debug("Type: " + items.getString("StaffItems." + s + ".type"));

      switch (ItemTypes.valueOf(items.getString("StaffItems." + s + ".type"))) {
        case COMMAND -> manager.addItem(
            new CustomCommandItem(
                s, itemStack, slot, permission, items.getString("StaffItems." + s + ".command")), s);
        case COMMAND_TARGET -> manager.addItem(
            new CustomCommandInteractionItem(
                s, itemStack, slot, permission, items.getString("StaffItems." + s + ".command")), s);
        default -> MAStaff.getPlugin().getPLogger().warn("Unknown item type: " + s + ".type");
      }
    }
  }
}
