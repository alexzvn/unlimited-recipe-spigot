package dev.alexzvn.recipe.recipe;

import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.contracts.ItemCraftContract;

public class ItemCraft implements ItemCraftContract {

    protected ItemStack[][] items;

    public ItemCraft(ItemStack[][] items) {
        this.items = items;
    }

    public ItemStack[][] getItems() {
        return items;
    }
}
