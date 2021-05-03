package dev.alexzvn.recipe.recipe;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import dev.alexzvn.recipe.helper.Util;

public class RecipeManager {
    protected Map<String, Recipe> recipes;

    protected static RecipeManager instance;

    public RecipeManager() {
        instance = this;
        loadRecipes();
    }

    protected void loadRecipes() {
        File recipeFolder = Util.file("/recipes");

        for (File file : recipeFolder.listFiles()) {
            if (! file.getPath().endsWith(".json")) continue;

            add(Recipe.unserialize(
                Util.readFile(file)
            ));
        }
    }

    public int count() {
        return recipes.size();
    }

    public void add(Recipe recipe) {
        recipes.put(recipe.checksum(), recipe);
        saveRecipeFile(recipe);
    }

    public void remove(Recipe recipe) {
        recipes.remove(recipe.checksum());
        deleteRecipeFile(recipe);
    }

    public Set<Recipe> getRecipes() {
        return new HashSet<Recipe>(recipes.values());
    }

    protected void saveRecipeFile(Recipe recipe) {
        File file = Util.file("/recipes/" + recipe.checksum() + ".json");

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
        File file = Util.file("/recipes/" + recipe.checksum() + ".json");

        if (file.exists()) file.delete();
    }

    public static RecipeManager getInstance() {
        return instance;
    }
}