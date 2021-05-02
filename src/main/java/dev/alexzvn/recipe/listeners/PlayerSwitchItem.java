package dev.alexzvn.recipe.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dev.alexzvn.recipe.helper.JsonSerializer;

public class PlayerSwitchItem implements Listener {

    protected JavaPlugin plugin;

    public PlayerSwitchItem(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void logItem(PlayerDropItemEvent event) {
        String item = JsonSerializer.encodeItemStack(
            event.getItemDrop().getItemStack()
        );

        plugin.getLogger().info(item);
    }
}
