package dev.alexzvn.recipe.task.workbench;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.recipe.Recipe;
import dev.alexzvn.recipe.ui.WorkbenchTable;

public class SetDealWorkbenchTask implements Runnable {
    protected Recipe recipe;
    protected Chest chest;
    protected Player player;
    protected boolean stack;

    public SetDealWorkbenchTask(Recipe recipe, Chest chest, Player player, boolean stack) {
        this.recipe = recipe;
        this.chest = chest;
        this.player = player;
        this.stack = stack;
    }

    @Override
    public void run() {

        ItemStack[][] craft = chest.rageMatrixItemStack(WorkbenchTable.craftSlot);

        craft = stack ? dealStack(craft) : dealSingle(craft);

        chest.matrixFill(craft, WorkbenchTable.craftSlot.a);
    }

    protected ItemStack[][] dealSingle(ItemStack[][] craft) {
        if (! recipe.canDeal(craft)) return craft;

        craft = recipe.deal(craft);

        if (! recipe.canDeal(craft)) {
            chest.clear(WorkbenchTable.recipeSlot);
        } else {
            chest.fill(recipe.getRecipe(), WorkbenchTable.recipeSlot);
        }

        return craft;
    }

    protected ItemStack[][] dealStack(ItemStack[][] craft) {

        while (recipe.canDeal(craft)) {
            craft = recipe.deal(craft);
            Util.sendPlayerItem(recipe.getRecipe(), player);
        }

        chest.clear(WorkbenchTable.recipeSlot);

        return craft;
    }
}
