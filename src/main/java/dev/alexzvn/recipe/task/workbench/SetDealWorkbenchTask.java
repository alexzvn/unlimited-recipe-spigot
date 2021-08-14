package dev.alexzvn.recipe.task.workbench;

import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.recipe.Recipe;
import dev.alexzvn.recipe.recipe.ReturnItem;

public class SetDealWorkbenchTask {
    protected Recipe recipe;

    public SetDealWorkbenchTask(Recipe recipe) {
        this.recipe = recipe;
    }

    public ReturnItem dealSingle(ItemStack[][] craft) {
        ReturnItem returnItem = new ReturnItem();

        if (! recipe.canDeal(craft)) return returnItem;

        if (recipe.canDeal(craft)) {
            returnItem.craft = recipe.deal(craft);
            returnItem.recipe.add(recipe.getRecipe());
        }

        return returnItem;
    }

    public ReturnItem dealStack(ItemStack[][] craft) {
        ReturnItem returnItem = new ReturnItem();

        while (recipe.canDeal(craft)) {
            returnItem.craft = recipe.deal(craft);
            returnItem.recipe.add(recipe.getRecipe());
        }

        return returnItem;
    }
}
