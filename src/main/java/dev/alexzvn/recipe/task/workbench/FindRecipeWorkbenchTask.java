package dev.alexzvn.recipe.task.workbench;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.recipe.Recipe;
import dev.alexzvn.recipe.recipe.RecipeManager;
import dev.alexzvn.recipe.ui.WorkbenchTable;

public class FindRecipeWorkbenchTask implements Runnable {

    protected Chest chest;

    public FindRecipeWorkbenchTask(Chest chest) {
        this.chest = chest;
    }

    @Override
    public void run() {
        Recipe recipe = RecipeManager.getInstance().find(
            chest.rageMatrixItemStack(WorkbenchTable.craftSlot)
        );

        if (recipe != null) {
            chest.fill(recipe.getRecipe(), WorkbenchTable.recipeSlot);
        } else {
            chest.fill(Util.airItem(), WorkbenchTable.recipeSlot);
        }
    }
}
