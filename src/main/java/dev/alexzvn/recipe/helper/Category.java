package dev.alexzvn.recipe.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class Category {

    final protected static FileConfiguration config = Config.config();

    public static Map<String, ItemStack> items() {
        Map<String, ItemStack> items = new HashMap<String, ItemStack>();

        for (String category : keys()) {
            items.put(category, createItemCategory(category));
        }

        return items;
    }

    public static Set<String> keys() {
        return config.getConfigurationSection("categories").getKeys(false);
    }

    public static boolean hasAny() {
        return keys().size() > 0;
    }

    public static boolean isCategory(ItemStack item) {
        return getCategory(item) != null;
    }

    public static String getCategory(ItemStack item) {
        return Item.getString(item, "category");
    }

    protected static ItemStack createItemCategory(String key) {
        String category = "categories." + key + ".",
            name = config.getString(category + "name"),
            material = config.getString(category + "material");

        List<String> lore = config.getStringList(category + "lore");

        ItemStack item = new ItemStack(Material.getMaterial(material));

        Item.setName(item, name);
        Item.setLore(item, lore);
        Item.setString(item, "category", key);

        return item;
    }
}
