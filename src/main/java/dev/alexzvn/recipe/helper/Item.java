package dev.alexzvn.recipe.helper;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class Item {

    public static void setName(ItemStack item, String title) {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Util.color(title));

        item.setItemMeta(meta);
    }

    public static void setInt(ItemStack item, String key, int value) {
        NamespacedKey namespace = Util.createNamespaceKey(key);

        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(namespace, PersistentDataType.INTEGER ,value);

        item.setItemMeta(meta);
    }

    public static void setString(@NotNull ItemStack item, @NotNull String key, @NotNull String value) {
        NamespacedKey namespace = Util.createNamespaceKey(key);

        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(namespace, PersistentDataType.STRING ,value);

        item.setItemMeta(meta);
    }

    public static Integer getInt(ItemStack item, String key) {
        return getData(item).get(
            Util.createNamespaceKey(key),
            PersistentDataType.INTEGER
        );
    }

    public static String getString(ItemStack item, String key) {
        return getData(item).get(
            Util.createNamespaceKey(key),
            PersistentDataType.STRING
        );
    }

    public static PersistentDataContainer getData(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer();
    }
}
