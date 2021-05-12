package dev.alexzvn.recipe.recipe;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.helper.Item;
import dev.alexzvn.recipe.helper.Util;

public class RecipeManager {
    protected Map<String, Recipe> recipes = new HashMap<String, Recipe>();

    protected static RecipeManager instance;

    public RecipeManager() {
        instance = this;
        loadRecipes();
    }

    protected void loadRecipes() {
        File recipeFolder = Util.file("/recipes");

        File[] files = recipeFolder.listFiles();

        for (File file : files) {
            if (! file.getPath().endsWith(".json")) continue;

            Recipe recipe = Recipe.unserialize(
                Util.readFile(file),
                file.getName().replace(".json", "")
            );

            recipes.put(recipe.name(), recipe);
        }
    }

    public List<String> getNames() {
        return new ArrayList<String>(recipes.keySet());
    }

    public Recipe find(ItemStack[][] items) {

        for (Recipe recipe : recipes.values()) {
            if (recipe.canDeal(items)) return recipe;
        }

        return null;
    }

    public int count() {
        return recipes.size();
    }

    public void add(Recipe recipe) {
        recipes.put(recipe.name(), recipe);
        saveRecipeFile(recipe);
    }

    public void remove(Recipe recipe) {
        recipes.remove(recipe.name());
        deleteRecipeFile(recipe);
    }

    public void remove(String name) {
        remove(recipes.get(name));
    }

    public Recipe get(String name) {
        return recipes.get(name);
    }

    public Map<String, Recipe> getMapRecipe() {
        return recipes;
    }

    public Set<Recipe> getRecipes() {
        return new HashSet<Recipe>(recipes.values());
    }

    public ItemStack[] getNamedRecipes() {
        ItemStack[] recipeItems = new ItemStack[recipes.size()];

        int i = 0;
        for (String name : recipes.keySet()) {
            recipeItems[i] = recipes.get(name).getRecipe();
            recipeItems[i].setAmount(1);
            Item.setString(recipeItems[i], "name", name);
            i++;
        }

        return recipeItems;
    }

    protected void saveRecipeFile(Recipe recipe) {
        File file = Util.file("/recipes/" + recipe.name() + ".json");

        if (file.exists()) file.delete();

        try {
            file.createNewFile();
            PrintWriter writer =  new PrintWriter(file, "UTF-8");

            writer.write(recipe.serialize());

            writer.close();
        }

        catch (Exception e) {
            Util.logger().log(Level.WARNING, "Can't delete file: " + file.getPath(), e);
        }
    }

    protected void deleteRecipeFile(Recipe recipe) {
        File file = Util.file("/recipes/" + recipe.name() + ".json");

        if (file.exists()) file.delete();
    }

    public static RecipeManager getInstance() {
        return instance;
    }
}
