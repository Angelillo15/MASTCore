package es.angelillo15.mast.api.managers;

import es.angelillo15.mast.api.gui.Page;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PunishmentsGUILoader {
    @Getter
    private static HashMap<Integer, Page> punishmentsGUIS = new HashMap<>();

    public static void addPage(int pageNumber, Page page){
        punishmentsGUIS.put(pageNumber, page);
    }

    public static void removePage(int page){
        punishmentsGUIS.remove(page);
    }

    public static Page getPage(int page){
        return punishmentsGUIS.get(page);
    }

    public static boolean containsPage(int page){
        return punishmentsGUIS.containsKey(page);
    }

    public static void clearPages(){
        punishmentsGUIS.clear();
    }
}
