package dev.alexzvn.recipe.task.recipetable;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.Item;
import dev.alexzvn.recipe.helper.ItemPaginator;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.ui.ListRecipeTable;

public class PaginationItemTable implements Runnable {

    protected Chest chest;
    protected int page;

    public PaginationItemTable(Chest chest, int page) {
        this.chest = chest;
        this.page  = page;
    }

    @Override
    public void run() {
        ItemPaginator paginator = ListRecipeTable.paginateRecipes(page);

        chest.matrixFill(paginator.items, ListRecipeTable.itemSlots.a);

        chest.fill(getPrevItem(paginator.prev), ListRecipeTable.prevSlot);

        chest.fill(getNextItem(paginator.next), ListRecipeTable.nextSlot);
    }

    protected ItemStack getPrevItem(boolean shouldExists) {
        if (! shouldExists) return Util.airItem();

        ItemStack item = new ItemStack(Material.RED_SHULKER_BOX);

        
        Item.setName(item, "&lLùi");
        Item.setInt(item, "page", page - 1);

        return item;
    }

    protected ItemStack getNextItem(boolean shouldExists) {
        if (! shouldExists) return Util.airItem();

        ItemStack item = new ItemStack(Material.WHITE_SHULKER_BOX);

        Item.setName(item, "&lTiến");
        Item.setInt(item, "page", page + 1);

        return item;
    }
}
