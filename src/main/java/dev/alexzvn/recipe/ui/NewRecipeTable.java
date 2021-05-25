package dev.alexzvn.recipe.ui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.CoupleLocation;
import dev.alexzvn.recipe.helper.Item;
import dev.alexzvn.recipe.helper.Location;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.recipe.Recipe;
import dev.alexzvn.recipe.recipe.RecipeManager;

public class NewRecipeTable extends Table {

    public static final String name = "Tạo recipe mới";

    public static final CoupleLocation craftSlots = new CoupleLocation(3, 1, 5, 3);

    public static final CoupleLocation blackPaneSlots = new CoupleLocation(1, 1, 6, 3);

    public static final CoupleLocation brownPaneSlots = new CoupleLocation(7, 1, 9, 3);

    public static final Location recipeSlot = new Location(8, 2);

    public static final Location saveSlot = new Location(1, 1);

    protected String recipe;

    public NewRecipeTable(@NotNull String recipeName) {
        super();

        this.recipe = recipeName;

        createNamedUI();
    }

    public void createNamedUI() {
        if (chest == null) {
            chest = new Chest(Bukkit.createInventory(null, 3*9, name));
        }

        ItemStack blackPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE),
            brownPane = new ItemStack(Material.BROWN_STAINED_GLASS_PANE),
            ironBlock = new ItemStack(Material.IRON_BLOCK),
            air = Util.airItem();

        Item.setName(ironBlock, "&lLưu lại");
        Item.setString(ironBlock, "name", recipe);

        chest.fill(blackPane, blackPaneSlots);
        chest.fill(brownPane, brownPaneSlots);
        chest.fill(ironBlock, saveSlot);
        chest.fill(air, recipeSlot);
        chest.fill(air, craftSlots);
    }

    public static void handleClickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle() != name) return;

        boolean isInventory = isCurrentInventory(event.getSlot(), event.getRawSlot());
        Chest chest = new Chest(event.getInventory());
        Location clicked = Chest.indexToCoordinate(event.getSlot());

        if (isInventory && (recipeSlot.isNot(clicked) && !craftSlots.contains(clicked)) ) {
            event.setCancelled(true);
        }

        if (saveSlot.is(clicked)) {
            saveRecipe(chest);
            event.getWhoClicked().closeInventory();
            Util.tell(event.getWhoClicked(), "&aĐã lưu lại recipe");
            return;
        }
    }

    /**
     * all data should be validate before goes here
     * after validate this method will add recipe to RecipeManager
     * 
     * @param chest
     * @return Recipe created
     */
    protected static Recipe saveRecipe(Chest chest) {
        String name = getRecipeName(chest.slot(saveSlot));
        RecipeManager manager = RecipeManager.getInstance();

        if (manager.getMapRecipe().containsKey(name)) {
            manager.remove(name);
        }

        Recipe recipe = new Recipe(
            chest.slot(recipeSlot),
            chest.rageMatrixItemStack(craftSlots),
            name
        );

        manager.add(recipe);

        return recipe;
    }

    protected static boolean containCraft(Chest chest) {
        List<ItemStack> items = Chest.flatMatrix(chest.rangeMatrixItemStack(craftSlots));

        for (ItemStack item : items) {
            if (! Util.isAirItem(item)) return true;
        }

        return false;
    }

    public static String getRecipeName(ItemStack item) {
        if (item == null) return null;

        return Item.getString(item, "name");
    }
}
