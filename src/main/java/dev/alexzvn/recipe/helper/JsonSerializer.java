package dev.alexzvn.recipe.helper;

import java.util.Map;

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
}
