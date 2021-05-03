package dev.alexzvn.recipe.helper;

import java.util.Map;
import java.util.logging.Level;

import com.dumptruckman.bukkit.configuration.json.JsonConfiguration;

import org.bukkit.inventory.ItemStack;

public class JsonSerializer {

    public static String encodeItemStack(ItemStack item) {
        Map<String, Object> content = item.serialize();

        JsonConfiguration json = new JsonConfiguration();

        for (String key : content.keySet()) {
            json.set(key, content.get(key));
        }

        return json.saveToString();
    }

    public static ItemStack decodeItemStack(String contents) {
        JsonConfiguration json = new JsonConfiguration();

        try {
            json.loadFromString(contents);

            return (ItemStack) json.getValues(true);
        }

        catch (Exception e) {
            Util.logger().log(Level.WARNING, "Unable to unserialize item: " + contents, e);
        }

        return null;
    }

    public static String encodeItemStackMatrix(ItemStack[][] items) {


        return "a";
    }
}
