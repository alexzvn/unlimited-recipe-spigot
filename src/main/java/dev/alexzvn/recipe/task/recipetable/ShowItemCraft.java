package dev.alexzvn.recipe.task.recipetable;

import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.Item;
import dev.alexzvn.recipe.helper.Location;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.recipe.Recipe;
import dev.alexzvn.recipe.recipe.RecipeManager;
import dev.alexzvn.recipe.ui.ListRecipeTable;

public class ShowItemCraft implements Runnable {

    protected Chest chest;

    protected Location clicked;

    public ShowItemCraft(Chest chest, Location clicked) {
        this.chest = chest;
        this.clicked = clicked;
    }

    @Override
    public void run() {
        clear();

        ItemStack clickedItem = chest.slot(clicked);

        if (Util.isAirItem(clickedItem)) return;

        String checksum  = Item.getString(clickedItem, "checksum");

        if (checksum == null) return;

        Recipe recipe = RecipeManager.getInstance().get(checksum);

        if (recipe == null) return;

        chest.matrixFill(recipe.getCraft(), ListRecipeTable.craftSlots.a);

        chest.fill(recipe.getRecipe(), ListRecipeTable.recipeSlot);
    }

    protected void clear() {
        chest.clear(ListRecipeTable.craftSlots);
        chest.clear(ListRecipeTable.recipeSlot);
    }
}
