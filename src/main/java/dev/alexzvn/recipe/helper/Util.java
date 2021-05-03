package dev.alexzvn.recipe.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import dev.alexzvn.recipe.UnlimitedRecipe;

public class Util {

    public static JavaPlugin plugin() {
        return UnlimitedRecipe.getInstance();
    }

    public static java.util.logging.Logger logger() {
        return plugin().getLogger();
    }

    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin());
    }

    public static void bc(String message) {
        Bukkit.broadcastMessage(message);
    }

    /**
     * Create folder if not exists in current plugin data folder
     * 
     * @param name
     */
    public static void mkdir(String name) {
        File file = new File(plugin().getDataFolder().getPath() + '/' + name);

        if (file.exists() == false) {
            file.mkdirs();
        }
    }

    public static File file(String name) {
        return new File(plugin().getDataFolder().getPath() + '/' + name);
    }

    public static String readFile(File file) {
        String content = "";

        try {
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                content.concat(reader.nextLine());
            }

            reader.close();

            return content;
        }

        catch (FileNotFoundException e) {
            printError("File not found: " + file.getPath(), e);
        }

        return null;
    }

    public static void printError(String message, Exception e) {
        Util.logger().log(Level.WARNING, message, e);
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static boolean isAirItem(ItemStack item) {
        return item == null || item.getType().name().equals(Material.AIR.name());
    }

    public static boolean containItems(ItemStack[] items) {
        for (ItemStack item : items) {
            if (! isAirItem(item)) {
                return true;
            }
        }

        return false;
    }

    public static ItemStack createItem(Material material, int amount, String name, String ...lores) {
        ItemStack item = new ItemStack(material, amount);
        List<String> lore = new ArrayList<String>();
        ItemMeta meta = item.getItemMeta();

        for (String string : lores) {
            lore.add(color(string));
        }

        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack airItem() {
        return new ItemStack(Material.AIR);
    }
}
