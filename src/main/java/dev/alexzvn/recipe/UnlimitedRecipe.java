package dev.alexzvn.recipe;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.listeners.PlayerSwitchItem;
import dev.alexzvn.recipe.recipe.RecipeManager;

/**
 * UnlimitedRecipe
 */
public class UnlimitedRecipe extends JavaPlugin {

    protected static UnlimitedRecipe instance;

    protected RecipeManager recipeManager;

    @Override
    public void onEnable() {
        instance = this;

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

    protected void registerListeners() {
        Util.registerListener(new PlayerSwitchItem(this));
    }

    public static UnlimitedRecipe getInstance() {
        return instance;
    }
}
