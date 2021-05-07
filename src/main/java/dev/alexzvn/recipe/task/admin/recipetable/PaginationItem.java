package dev.alexzvn.recipe.task.admin.recipetable;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.ItemPaginator;
import dev.alexzvn.recipe.task.recipetable.PaginationItemTable;
import dev.alexzvn.recipe.ui.ShowRecipeEditTable;

public class PaginationItem extends PaginationItemTable {

    public PaginationItem(Chest chest, int page) {
        super(chest, page);
    }

    @Override
    public void run() {
        ItemPaginator paginator = ShowRecipeEditTable.paginateRecipes(page);

        chest.matrixFill(paginator.items, ShowRecipeEditTable.recipeSlots.a);

        chest.fill(getPrevItem(paginator.prev), ShowRecipeEditTable.prevSlot);

        chest.fill(getNextItem(paginator.next), ShowRecipeEditTable.nextSlot);
    }
}
