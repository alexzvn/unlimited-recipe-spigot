package dev.alexzvn.recipe.recipe;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public class ReturnItem {

    public ItemStack[][] craft = new ItemStack[0][0];

    public List<ItemStack> recipe = new ArrayList<ItemStack>();

    public ReturnItem(ItemStack[][] craft, List<ItemStack> recipe) {
        this.craft = craft;
        this.recipe = recipe;
    }

    public ReturnItem() {
        // default
    }
}
