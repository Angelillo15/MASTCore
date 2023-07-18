package es.angelillo15.mast.bukkit.loaders;

import es.angelillo15.mast.api.ILogger;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.gui.Page;
import es.angelillo15.mast.api.managers.PunishmentsGUIManager;
import es.angelillo15.mast.api.material.XMaterial;
import es.angelillo15.mast.bukkit.MAStaff;
import es.angelillo15.mast.api.config.bukkit.ConfigLoader;
import es.angelillo15.mast.api.gui.CommandItem;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PunishmentGUILoader {
    public static void load() {
        PunishmentsGUIManager.clearPages();

        YamlFile gui = ConfigLoader.getPunishmentsGUI().getConfig();
        ILogger logger = MAStaff.getPlugin().getPLogger();

        int count = 0;


        for (String page : gui.getConfigurationSection("Gui.pages").getKeys(false)) {
            count++;
            logger.debug("Loading page " + count + " of punishments GUI with name " + page + "...");
            HashMap<Integer, CommandItem> items = new HashMap<>();

            for (String item : gui.getConfigurationSection("Gui.pages." + page + ".items").getKeys(false)) {
                int slot = gui.getInt("Gui.pages." + page + ".items." + item + ".slot");

                CommandItem itemStack = new CommandItem(XMaterial.valueOf(gui.getString("Gui.pages." + page + ".items." + item + ".material")).parseItem(),
                        gui.getString("Gui.pages." + page + ".items." + item + ".command")
                );

                logger.debug("Loading item " + item + "in slot: " + slot + "with command: "+ itemStack.getCommand() +"...");

                ItemMeta itemMeta = itemStack.getItemMeta();

                itemMeta.setDisplayName(TextUtils.colorize(gui.getString("Gui.pages." + page + ".items." + item + ".name")));
                List<String> lore = new ArrayList<>();

                for (String loreLine : gui.getStringList("Gui.pages." + page + ".items." + item + ".lore")) {
                    lore.add(TextUtils.colorize(loreLine));
                }

                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);

                items.put(slot, itemStack);
            }

            Page pageObject = new Page(items);
            pageObject.setTitle(TextUtils.colorize(gui.getString("Gui.pages." + page + ".title")));

            PunishmentsGUIManager.addPage(count, pageObject);
        }
    }
}
