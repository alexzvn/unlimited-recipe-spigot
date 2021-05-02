package dev.alexzvn.recipe;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.alexzvn.recipe.listeners.PlayerSwitchItem;

/**
 * UnlimitedRecipe
 */
public class UnlimitedRecipe extends JavaPlugin {

    @Override
    public void onEnable() {
        this.registerListeners();
        this.getLogger().info("Plugin Recipe Enabled");
    }

    @Override
    public void onDisable() {
        
    }

    protected void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerSwitchItem(this), this);
    }
}
