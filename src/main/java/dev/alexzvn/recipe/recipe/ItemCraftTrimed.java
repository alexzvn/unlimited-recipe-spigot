package dev.alexzvn.recipe.recipe;

import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.contracts.ItemCraftContract;
import dev.alexzvn.recipe.helper.Chest;

public class ItemCraftTrimed implements ItemCraftContract {
    
    protected ItemStack[][] items;

    public ItemCraftTrimed(ItemStack[][] items) {
        this.items = Chest.trimMatrix(items);
    }

    public ItemStack[][] getItems() {
        return items;
    }
}
