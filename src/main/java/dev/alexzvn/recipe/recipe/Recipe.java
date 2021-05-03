package dev.alexzvn.recipe.recipe;

import java.util.logging.Level;

import com.dumptruckman.bukkit.configuration.json.JsonConfiguration;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.contracts.ItemCraftContract;
import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.Hash;
import dev.alexzvn.recipe.helper.Util;

public class Recipe {
    protected ItemCraftContract payload;

    protected ItemStack recipe;

    /**
     * if shaped, we only compare ignore empty space such as air slot
     */
    protected boolean shaped = true;

    public Recipe(ItemStack recipe, ItemCraftContract payload, boolean sharped) {
        this.recipe = recipe;
        this.payload = payload;
        this.shaped = sharped;
    }

    public Recipe(ItemStack recipe, ItemCraftContract payload) {
        this.recipe = recipe;
        this.payload = payload;
        this.shaped = true;
    }

    /**
     * Generate SHA-1 hash for current recipe
     */
    public String checksum() {
        return Hash.sha1(serialize());
    }

    public boolean canDeal(ItemStack[][] items) {
        ItemStack[][] craft = this.payload.getItems();

        if (shaped) items = Chest.trimMatrix(items);

        if (items.length != craft.length) return false;

        for (int y = 0; y < craft.length; y++) {
            ItemStack[] craftRow = craft[y], itemRow = items[y];

            if (craftRow.length != itemRow.length) return false;

            for (int x = 0; x < craftRow.length; x++) {
                ItemStack craftItem = craftRow[x].clone(),
                    item = itemRow[x].clone();

                boolean isEnoughAmount = item.getAmount() >= craftItem.getAmount();

                craftItem.setAmount(1);
                item.setAmount(1);

                boolean isSameItem = item.equals(craftItem);

                if (isEnoughAmount == false || isSameItem == false) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 
     * @param items
     * @return itemstack matrix after trade
     */
    public ItemStack[][] deal(ItemStack[][] items) {
        ItemStack[][] crafts = payload.getItems(),
            deal = new ItemStack[crafts.length][];

        for (int y = 0; y < crafts.length; y++) {
            for (int x = 0; x < crafts[y].length; x++) {
                ItemStack craft = crafts[y][x].clone(),
                    item = items[y][x];

                if (item.getAmount() - craft.getAmount() < 1) {
                    deal[y][x] = new ItemStack(Material.AIR);
                } else {
                    item.setAmount(item.getAmount() - craft.getAmount());
                    deal[y][x] = item;
                }
            }
        }

        return deal;
    }

    public ItemStack getRecipe() {
        return recipe;
    }

    public String serialize() {
        JsonConfiguration json = new JsonConfiguration();

        json.set("recipe", this.recipe);
        json.getBoolean("shaped", shaped);
        json.set("craft", this.payload.getItems());

        return json.saveToString();
    }

    public static Recipe unserialize(String contents) {
        JsonConfiguration json = new JsonConfiguration();

        try {
            json.loadFromString(contents);
        }

        catch (Exception e) {
            Util.logger().log(Level.WARNING, "Unable to unserialize item: " + contents, e);
            return null;
        }

        ItemCraftContract itemCraft;
        ItemStack recipe = json.getItemStack("recipe");
        ItemStack[][] items = (ItemStack[][]) json.get("craft");

        if (json.getBoolean("shaped", false)) {
            itemCraft = new ItemCraftTrimed(items);
        } else {
            itemCraft = new ItemCraft(items);
        }

        return new Recipe(recipe, itemCraft);
    }
}
