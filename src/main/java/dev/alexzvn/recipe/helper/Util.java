package dev.alexzvn.recipe.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.dumptruckman.bukkit.configuration.json.JsonConfiguration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

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
        try {
            return readRawFile(file);
        }

        catch (Exception e) {
            logger().warning(e.getMessage());
        }

        return null;
    }

    public static String readRawFile(File file) throws Exception {
        String st, content = "";

        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((st = br.readLine()) != null) {
            content = content.concat(st);
        }

        br.close();

        return content;
    }

    public static void printError(String message, Exception e) {
        Util.logger().log(Level.WARNING, message, e);
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static boolean isAirItem(ItemStack item) {
        if (item == null) {
            return true;
        }

        return Material.AIR.equals(item.getType());
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

    public static String serialize(String key, Object object) {
        JsonConfiguration json = new JsonConfiguration();

        json.set(key, object);

        return json.saveToString();
    }

    public static void debug(Object object) {

        logger().info(serialize("debug", object));
    }

    public static ItemStack airItem() {
        return new ItemStack(Material.AIR);
    }

    public static Player humanToPlayer(HumanEntity human) {
        return Bukkit.getPlayer(human.getName());
    }

    public static void sendPlayerItem(ItemStack item, Player player) {
        if (isAirItem(item)) return;

        Inventory inv = player.getInventory();

        if (inv.firstEmpty() != -1) {
            inv.addItem(item); return;
        }

        player.getWorld().dropItemNaturally(player.getLocation(), item);
    }

    public static void sendPlayerItems(List<ItemStack> items, Player player) {
        items = Chest.removeAir(items);

        for (ItemStack item : items) {
            Util.sendPlayerItem(item, player);
        }
    }

    public static void sendPlayerItems(ItemStack[][] items, Player player) {
        sendPlayerItems(Chest.flatMatrix(items), player);
    }

    public static BukkitScheduler scheduler() {
        return plugin().getServer().getScheduler();
    }

    public static void dispatchDelay(Runnable task, int tick) {
        scheduler().runTaskLaterAsynchronously(plugin(), task, tick);
    }

    public static void dispatch(Runnable task) {
        scheduler().runTaskAsynchronously(plugin(), task);
    }

    public static NamespacedKey createNamespaceKey(String key) {
        return new NamespacedKey(plugin(), key);
    }
}
