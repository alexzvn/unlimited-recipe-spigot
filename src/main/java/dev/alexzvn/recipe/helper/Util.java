package dev.alexzvn.recipe.helper;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;

import com.dumptruckman.bukkit.configuration.json.JsonConfiguration;
import com.google.common.io.Files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
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
        Bukkit.broadcastMessage(color(message));
    }

    public static void tell(Entity entity, String message) {
        message = color(Config.config().getString("message_prefix") + message);

        entity.sendMessage(message);
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
            List<String> list = Files.readLines(file, StandardCharsets.UTF_8);

            for (String text : list) {
                content += text;
            }

            return content;
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeFile(File file, String content) {
        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");

            writer.write(content);

            writer.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void dropItem(ItemStack item, Location location) {
        if (isAirItem(item)) return;

        location.getWorld().dropItemNaturally(location, item);
    }

    public static void sendPlayerItem(ItemStack item, Player player) {
        if (isAirItem(item)) return;

        Inventory inv = player.getInventory();

        if (inv.firstEmpty() != -1) {
            inv.addItem(item); return;
        }

        dropItem(item, player.getLocation());
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

    public static String base64_encode(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public static String base64_decode(String text) {
        return new String(Base64.getDecoder().decode(text.getBytes()));
    }
}
