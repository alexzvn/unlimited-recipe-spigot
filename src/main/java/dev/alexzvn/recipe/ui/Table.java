package dev.alexzvn.recipe.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.recipe.RecipeManager;

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

    public void createUI() {};

    public static boolean isCurrentInventory(int slot, int rawSlot) {
        return slot == rawSlot;
    }

    public static boolean isCurrentInventory(InventoryClickEvent e) {
        return e.getSlot() == e.getRawSlot();
    }

    protected static ItemStack[] recipeItems() {
        return RecipeManager.getInstance().getNamedRecipes();
    }
}
