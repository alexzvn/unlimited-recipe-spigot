package dev.alexzvn.recipe.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.Util;

public class WorkbenchTable {

    final public static String name = "Bàn chế tạo";

    protected Chest chest;

    public WorkbenchTable() {
        chest = new Chest(Bukkit.createInventory(null, 6*9, name));

        createUI();
    }

    public Inventory getInventory() {
        return chest.getInventory();
    }

    protected void createUI() {
        ItemStack blackPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE),
            yellowPane = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE),
            arrow = new ItemStack(Material.ARROW),
            book = new ItemStack(Material.BOOK),
            air = Util.airItem();

        // Fill background
        chest.fill(blackPane, 1, 1, 9, 5);
        chest.fill(yellowPane, 1, 6, 9, 6);

        // Fill empty slot
        chest.fill(air, 2, 2, 4, 4);
        chest.fill(air, 8, 3);

        // Fill Special item
        chest.fill(arrow, 6, 3);
        chest.fill(book, 5, 6);
    }
}
