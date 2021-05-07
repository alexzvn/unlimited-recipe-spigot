package dev.alexzvn.recipe.ui;

import java.util.Arrays;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.CoupleLocation;
import dev.alexzvn.recipe.helper.Item;
import dev.alexzvn.recipe.helper.ItemPaginator;
import dev.alexzvn.recipe.helper.Location;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.recipe.Recipe;
import dev.alexzvn.recipe.recipe.RecipeManager;
import dev.alexzvn.recipe.task.recipetable.ChangePageTable;
import dev.alexzvn.recipe.task.recipetable.PaginationItemTable;
import dev.alexzvn.recipe.task.recipetable.ShowItemCraft;

public class ListRecipeTable extends Table {

    final static public String name = "Vật phẩm có thể chế tạo";

    final static public CoupleLocation yellowPaneSlots = new CoupleLocation(1, 5, 4, 5);

    final static public CoupleLocation blackPaneSlots = new CoupleLocation(5, 1, 9, 6);

    final static public CoupleLocation craftSlots = new CoupleLocation(6, 2, 8, 4);

    final static public CoupleLocation itemSlots = new CoupleLocation(1, 1, 4, 4);

    final static public Location recipeSlot = new Location(7, 6);

    final static public Location closeSlot = new Location(1, 6);

    final static public Location workbenchSlot = new Location(2, 6);

    final static public Location prevSlot = new Location(3, 6);

    final static public Location nextSlot = new Location(4, 6);

    final static public int itemChunk = Math.abs(itemSlots.a.x - itemSlots.b.x) + 1;

    final static public int itemSize = itemSlots.acreSquare();

    @Override
    public void createUI() {
        chest = new Chest(Bukkit.createInventory(null, 9*6, name));

        ItemStack blackPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE),
            yellowPane = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE),
            barrier = new ItemStack(Material.BARRIER),
            workbench = new ItemStack(Material.CRAFTING_TABLE),
            air = Util.airItem();

        // fill background
        chest.fill(blackPane, blackPaneSlots);
        chest.fill(yellowPane, yellowPaneSlots);
        chest.fill(air, craftSlots);
        chest.fill(air, recipeSlot);

        // fill special function
        chest.fill(workbench, workbenchSlot);
        chest.fill(barrier, closeSlot);

        Util.dispatch(new PaginationItemTable(chest, 0));
    }

    public static boolean isClickedFunction(Location location) {
        return location.is(workbenchSlot) ||
            location.is(closeSlot) ||
            location.is(prevSlot) ||
            location.is(nextSlot);
    }

    public static void handleClickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle() != name) return;

        Inventory inv = event.getInventory();
        Chest chest = new Chest(inv);
        Location clicked = Chest.indexToCoordinate(event.getSlot());
        Player player = Util.humanToPlayer(event.getWhoClicked());

        if (event.getClick().equals(ClickType.DOUBLE_CLICK)) {
            event.setCancelled(true);
        }

        if (event.getSlot() != event.getRawSlot()) { // is player slot
            return;
        }

        event.setCancelled(true);

        if (itemSlots.contains(clicked)) {
            Util.dispatch(new ShowItemCraft(chest, clicked)); return;
        }

        if (nextSlot.is(clicked) || prevSlot.is(clicked)) {
            Util.dispatch(new ChangePageTable(chest, clicked)); return;
        }

        if (closeSlot.is(clicked)) {
            player.closeInventory(); return;
        }

        if (workbenchSlot.is(clicked)) {
            player.openInventory(new WorkbenchTable().getInventory());
        }
    }

    public static ItemPaginator paginateRecipes(int page) {
        ItemPaginator paginator = new ItemPaginator();
        ItemStack[] recipes = recipeItems();
        int size = itemSize - 1;
        int offset = page * size;

        ItemStack[] items = Arrays.copyOfRange(recipes, offset, Math.min(recipes.length, offset + size));

        paginator.items = Chest.chunk(items, itemChunk);
        paginator.next  = recipes.length - 1 > offset + size;
        paginator.prev  = page > 0;
        paginator.total = recipes.length;

        return paginator;
    }

    protected static ItemStack[] recipeItems() {
        Map<String, Recipe> recipes = RecipeManager.getInstance().getMapRecipe();

        ItemStack[] recipeItems = new ItemStack[recipes.size()];

        int i = 0;
        for (String name : recipes.keySet()) {
            recipeItems[i] = recipes.get(name).getRecipe();
            recipeItems[i].setAmount(1);
            Item.setString(recipeItems[i], "name", name);
            i++;
        }

        return recipeItems;
    }
}
