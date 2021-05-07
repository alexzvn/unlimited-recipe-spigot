package dev.alexzvn.recipe.task.admin.recipetable;

import org.bukkit.entity.Player;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.Item;
import dev.alexzvn.recipe.helper.Location;
import dev.alexzvn.recipe.ui.EditRecipeTable;

public class DisplayEditRecipeTask implements Runnable {

    protected Chest chest;

    protected Location clicked;

    protected Player player;

    public DisplayEditRecipeTask(Chest chest, Location clicked, Player player) {
        this.chest = chest;
        this.clicked = clicked;
        this.player = player;
    }

    @Override
    public void run() {
        if (! chest.hasItemAt(clicked)) return;

        String name = Item.getString(chest.slot(clicked), "name");

        player.openInventory(new EditRecipeTable(name).getInventory());
    }

}
