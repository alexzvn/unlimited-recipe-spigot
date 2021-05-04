package dev.alexzvn.recipe.ui;

import org.bukkit.inventory.Inventory;

import dev.alexzvn.recipe.helper.Chest;

abstract public class Table {

    protected Chest chest;

    public Table(Inventory inv) {
        chest = new Chest(inv);
    }

    public Table() {
        createUI();
    }

    public Inventory getInventory() {
        return chest.getInventory();
    }

    abstract public void createUI();
}
