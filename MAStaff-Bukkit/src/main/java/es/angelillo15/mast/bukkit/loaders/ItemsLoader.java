package es.angelillo15.mast.bukkit.loaders;

import es.angelillo15.mast.api.items.ItemTypes;
import es.angelillo15.mast.api.managers.ItemManager;
import es.angelillo15.mast.api.material.XMaterial;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.bukkit.config.ConfigLoader;
import es.angelillo15.mast.bukkit.items.*;
import es.angelillo15.mast.api.TextUtils;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.ArrayList;
import java.util.Objects;

public class ItemsLoader {
    @Getter
    private static ItemManager manager;
    public static void load(){
        manager = new ItemManager();

        YamlFile items = ConfigLoader.getInternalStaffItems().getConfig();

        manager.clearItems();

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

            switch (ItemTypes.valueOf(s)){
                case FREEZE:
                    manager.addItem(new FreezeItem(itemStack, slot, permission));
                    break;
                case VANISH:
                    manager.addItem(new VanishItem(itemStack, slot, permission));
                    break;
                case ENDER_CHEST:
                    manager.addItem(new EnderChestItem(itemStack, slot, permission));
                    break;
                case CHEST:
                    manager.addItem(new ChestItem(itemStack, slot, permission));
                    break;
                case THRU:
                    manager.addItem(new ThruItem(itemStack, slot, permission));
                    break;
                case RANDOM_PLAYER_TELEPORT:
                    manager.addItem(new RTPItem(itemStack, slot, permission));
                    break;
                default:
                    throw new TypeNotPresentException("Item type not found", null);
            }
        }

        manager.getItems().forEach(item -> MAStaff.getPlugin().getPLogger().debug("Item loaded: " + item.getName()));
    }
}
