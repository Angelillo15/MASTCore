package es.angelillo15.mast.bukkit.utils.items;

import com.cryptomorin.xseries.XMaterial;
import es.angelillo15.mast.bukkit.api.item.CommandItem;
import es.angelillo15.mast.bukkit.api.item.ItemTypes;
import es.angelillo15.mast.bukkit.api.item.StaffItem;
import es.angelillo15.mast.bukkit.api.item.VanishItem;
import es.angelillo15.mast.bukkit.config.ConfigLoader;

import es.angelillo15.mast.bukkit.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.ArrayList;
import java.util.Objects;


public class InternalModules {
    private ArrayList<StaffItem> items = new ArrayList<>();
    private static ArrayList<String> itemsNames = new ArrayList<>();
    YamlFile config = ConfigLoader.getInternalStaffItems().getConfig();
    private Player player;

    public InternalModules(Player player) {
        this.player = player;
    }

    public ArrayList<StaffItem> getItems() {
        for (String s : config.getConfigurationSection("StaffItems").getKeys(false)) {
            if (config.getBoolean("StaffItems." + s + ".enabled")) {
                String material = config.getString("StaffItems." + s + ".material");
                XMaterial xMaterial = XMaterial.valueOf(material);
                int slot = config.getInt("StaffItems." + s + ".slot");
                Bukkit.getConsoleSender().sendMessage(String.valueOf(slot));
                ItemStack itemStack = new ItemStack(Objects.requireNonNull((xMaterial).parseMaterial()));
                ItemMeta meta = itemStack.getItemMeta();

                meta.setDisplayName(TextUtils.parseMessage(config.getString("StaffItems." + s + ".name")));
                ArrayList<String> lore = new ArrayList<>();
                for (String l : config.getStringList("StaffItems." + s + ".lore")) {
                    lore.add(TextUtils.parseMessage(l));
                }
                meta.setLore(lore);
                itemStack.setItemMeta(meta);


                switch (ItemTypes.valueOf(s)) {
                    case VANISH:
                        items.add(new VanishItem(player, itemStack, slot));
                        break;
                    case FREEZE:
                        items.add(new CommandItem(player, itemStack, slot, "freeze"));
                        break;
                    case RANDOM_PLAYER_TELEPORT:
                        items.add(new VanishItem(player, itemStack, slot));
                    default:
                        Bukkit.getConsoleSender().sendMessage("Not found: " + s);
                }
            }
        }
        return items;
    }

    public static ArrayList<StaffItem> getInternalModules(Player player){
        InternalModules modules = new InternalModules(player);
        return modules.getItems();
    }
}
