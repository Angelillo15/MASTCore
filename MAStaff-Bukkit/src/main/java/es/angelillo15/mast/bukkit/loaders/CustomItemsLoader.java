package es.angelillo15.mast.bukkit.loaders;

import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.items.ItemTypes;
import es.angelillo15.mast.api.managers.ItemManager;
import es.angelillo15.mast.api.material.XMaterial;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.bukkit.items.custom.CustomCommandInteractionItem;
import es.angelillo15.mast.bukkit.items.custom.CustomCommandItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.ArrayList;
import java.util.Objects;

public class CustomItemsLoader {
    private static ItemManager manager;

    public static void load() {
        manager = ItemsLoader.getManager();

        YamlFile items = ConfigLoader.getCustomItems().getConfig();

        for (String s : items.getConfigurationSection("StaffItems").getKeys(false)) {
            if (!(items.getBoolean("StaffItems." + s + ".enabled"))) {
                return;
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

            MAStaff.getPlugin().getPLogger().debug("Loading custom item: " + s);
            MAStaff.getPlugin().getPLogger().debug("Type: " + items.getString("StaffItems." + s +".type"));

            switch (ItemTypes.valueOf(items.getString("StaffItems." + s +".type"))){
                case COMMAND:
                    manager.addItem(new CustomCommandItem(s, itemStack, slot, permission, items.getString("StaffItems." + s + ".command")));
                    break;
                case COMMAND_TARGET:
                    manager.addItem(new CustomCommandInteractionItem(s, itemStack, slot, permission, items.getString("StaffItems." + s + ".command")));
                    break;
                default:
                    MAStaff.getPlugin().getPLogger().warn("Unknown item type: " + s + ".type");
                    break;
            }
        }

    }

}
