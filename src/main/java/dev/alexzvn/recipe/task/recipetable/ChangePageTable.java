package dev.alexzvn.recipe.task.recipetable;

import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.Item;
import dev.alexzvn.recipe.helper.Location;
import dev.alexzvn.recipe.helper.Util;

public class ChangePageTable implements Runnable {

    protected Chest chest;

    protected Location clicked;

    public ChangePageTable(Chest chest, Location clicked) {
        this.chest = chest;
        this.clicked = clicked;
    }

    @Override
    public void run() {
        ItemStack clickedItem = chest.slot(clicked);

        if (Util.isAirItem(clickedItem)) return;

        Integer page  = Item.getInt(clickedItem, "page");

        if (page == null) return;

        new PaginationItemTable(chest, page).run();
    }
}
