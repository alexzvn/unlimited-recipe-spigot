package dev.alexzvn.recipe.recipe;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.alexzvn.recipe.helper.Util;

public class CategoryManager {

    protected Map<String, Set<String>> categoryRecipe = new HashMap<String, Set<String>>();

    protected YamlConfiguration config;

    final protected File storage = Util.file("categories.yaml");

    public CategoryManager() {
        this.load();
    }

    public void add(String category, String recipe) {
        if (! categoryRecipe.containsKey(category)) {
            categoryRecipe.put(category, new HashSet<String>());
        }

        categoryRecipe.get(category).add(recipe);
        this.save();
    }

    public void remove(String category, String recipe) {
        if (! categoryRecipe.containsKey(category)) {
            return;
        }

        categoryRecipe.get(category).remove(recipe);
        this.save();
    }

    protected void save() {
        for (String category : categoryRecipe.keySet()) {
            config.set(category, categoryRecipe.get(category));
        }

        try {
            config.save(storage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void load() {
        config = new YamlConfiguration();

        try {
            config.load(storage);
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        for (String category : config.getKeys(false)) {
            categoryRecipe.put(
                category,
                new HashSet<String>(config.getStringList(category))
            );
        }
    }
}
