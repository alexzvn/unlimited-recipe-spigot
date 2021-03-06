package dev.alexzvn.recipe.recipe;

import java.util.List;
import java.util.logging.Level;

import com.dumptruckman.bukkit.configuration.json.JsonConfiguration;

import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.contracts.ItemCraftContract;
import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.Util;

public class Recipe {
    protected ItemCraftContract payload;

    protected ItemStack recipe;

    protected String name;

    /**
     * if shaped, we only compare ignore empty space such as air slot
     */
    protected boolean shaped = true;

    public Recipe(ItemStack recipe, ItemStack[][] payload, String name, boolean sharped) {
        this.recipe  = recipe;
        this.payload = makeItemCraft(payload);
        this.shaped  = sharped;
        this.name    = name;
    }

    public Recipe(ItemStack recipe, ItemStack[][] payload, String name) {
        this.recipe = recipe;
        this.payload = makeItemCraft(payload);
        this.shaped = true;
        this.name = name;
    }

    protected ItemCraftContract makeItemCraft(ItemStack[][] items) {
        if (shaped) {
            return new ItemCraftTrimed(items);
        }

        return new ItemCraft(items);
    }

    public ItemStack[][] getCraft() {
        return payload.getItems();
    }

    public String name() {
        return name;
    }

    public boolean canDeal(ItemStack[][] items) {
        ItemStack[][] craft = this.payload.getItems();

        if (shaped) items = Chest.trimMatrix(items);
        if (items.length != craft.length) return false;

        for (int i = 0; i < craft.length; i++) {
            for (int j = 0; j < craft[i].length; j++) {
                ItemStack a = craft[i][j];
                ItemStack b = items[i][j];

                if (Util.isAirItem(a) && Util.isAirItem(b)) continue;
                if (Util.isAirItem(a) || Util.isAirItem(b)) return false;

                if (!compareItemContent(a, b) || !compareItemAmount(a, b)) return false;
            }
        }

        return true;
    }

    protected boolean compareItemContent(ItemStack a, ItemStack b) {
        a = a.clone(); a.setAmount(1);
        b = b.clone(); b.setAmount(1);

        return Util.serialize("item", a).equals(Util.serialize("item", b));
    }

    protected boolean compareItemAmount(ItemStack recipeItem, ItemStack tradeItem) {
        return tradeItem.getAmount() >= recipeItem.getAmount();
    }

    /**
     * 
     * @param items
     * @return itemstack matrix after trade
     */
    public ItemStack[][] deal(ItemStack[][] items) {
        ItemStack[][] crafts = payload.getItems();
        ItemStack[][] deal = new ItemStack[crafts.length][];

        for (int y = 0; y < crafts.length; y++) {
            ItemStack[] dealRow = new ItemStack[crafts[y].length];

            for (int x = 0; x < crafts[y].length; x++) {

                ItemStack a = crafts[y][x];
                ItemStack b = items[y][x];

                if (Util.isAirItem(a) && Util.isAirItem(b)) continue;

                dealRow[x] = b.clone();

                dealRow[x].setAmount(b.getAmount() - a.getAmount());
            }

            deal[y] = dealRow;
        }

        return deal;
    }

    public ItemStack getRecipe() {
        return recipe.clone();
    }

    public String serialize() {
        JsonConfiguration json = new JsonConfiguration();

        json.set("recipe", this.recipe);
        json.getBoolean("shaped", shaped);
        json.set("craft", this.payload.getItems());

        return json.saveToString();
    }

    public static Recipe unserialize(String contents, String name) {
        JsonConfiguration json = new JsonConfiguration();

        try {
            json.loadFromString(contents);
        }

        catch (Exception e) {
            Util.logger().log(Level.WARNING, "Unable to unserialize item: " + contents, e);
            return null;
        }

        ItemStack recipe = (ItemStack) json.get("recipe");
        ItemStack[][] items = convertUnserializeCraft(json.get("craft"));

        return new Recipe(recipe, items, name);
    }

    @SuppressWarnings("unchecked")
    protected static ItemStack[][] convertUnserializeCraft(Object object) {
        List<List<ItemStack>> items = (List<List<ItemStack>>) object;

        ItemStack[][] craft = new ItemStack[items.size()][];

        int i = 0;
        for (List<ItemStack> row : items) {
            ItemStack[] craftRow = new ItemStack[row.size()];
            int j = 0;

            for (ItemStack item : row) {
                craftRow[j] = item;

                j++;
            }

            craft[i] = craftRow;
            i++;
        }

        return craft;
    }
}
