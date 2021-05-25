package dev.alexzvn.recipe.helper;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import de.tr7zw.nbtapi.NBTItem;

public class Item {
    final public static String NAMESPACE = "unlimited-recipe";

    public static void setName(ItemStack item, String title) {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Util.color(title));

        item.setItemMeta(meta);
    }

    public static ItemStack setInt(ItemStack item, String key, int value) {
        NBTItem nbti = new NBTItem(item);

        nbti.setInteger(build(key), value);

        return nbti.getItem();
    }

    public static ItemStack setString(@NotNull ItemStack item, @NotNull String key, @NotNull String value) {
        NBTItem nbti = new NBTItem(item);

        nbti.setString(build(key), value);

        return nbti.getItem();
    }

    public static Integer getInt(ItemStack item, String key) {
        return new NBTItem(item).getInteger(build(key));
    }

    public static String getString(ItemStack item, String key) {
        return new NBTItem(item).getString(build(key));
    }

    protected static String build(String key) {
        return NAMESPACE + "." + key;
    }
}
