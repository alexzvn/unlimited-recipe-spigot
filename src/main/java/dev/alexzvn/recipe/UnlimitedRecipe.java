package dev.alexzvn.recipe;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import dev.alexzvn.recipe.commands.CommandHandler;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.listeners.InventoryListener;
import dev.alexzvn.recipe.recipe.RecipeManager;

/**
 * UnlimitedRecipe
 */
public class UnlimitedRecipe extends JavaPlugin {

    final public static String version = "1.0.0";

    protected static UnlimitedRecipe instance;

    protected RecipeManager recipeManager;

    @Override
    public void onEnable() {
        instance = this;

        this.registerCommands();
        this.registerListeners();
        this.saveDefaultConfig();
        this.createDefaultFolder();
        this.registerRecipeManager();
    }

    @Override
    public void onDisable() {
        instance = null;
        recipeManager = null;
    }

    protected void createDefaultFolder() {
        String[] folders = {"recipes"};

        for (String folder : folders) {
            Util.mkdir(folder);
        }
    }

    protected void registerRecipeManager() {
        Logger logger = this.getLogger();

        logger.info("Loading all recipes");

        this.recipeManager = new RecipeManager();

        logger.info("Success loaded " + recipeManager.count() + " recipes");
    }

    protected void registerCommands() {
        this.getCommand("recipe").setExecutor(new CommandHandler());
    }

    protected void registerListeners() {
        Listener[] listeners = {
            new InventoryListener()
        };

        for (Listener listener : listeners) {
            Util.registerListener(listener);
        }
    }

    public static UnlimitedRecipe getInstance() {
        return instance;
    }
}
