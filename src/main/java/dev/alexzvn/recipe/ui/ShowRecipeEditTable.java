package dev.alexzvn.recipe.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.CoupleLocation;
import dev.alexzvn.recipe.helper.Item;
import dev.alexzvn.recipe.helper.ItemPaginator;
import dev.alexzvn.recipe.helper.Location;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.task.admin.recipetable.DisplayEditRecipeTask;
import dev.alexzvn.recipe.task.admin.recipetable.PaginationItem;

public class ShowRecipeEditTable extends Table {

    final public static String name = "Quản lý recipes";

    final public static CoupleLocation recipeSlots = new CoupleLocation(1, 1, 9, 4);

    final public static CoupleLocation bluePaneSlots = new CoupleLocation(1, 5, 9, 6);

    final public static Location prevSlot = new Location(4, 6);

    final public static Location closeSlot = new Location(5, 6);

    final public static Location nextSlot = new Location(6, 6);

    final static public int itemChunk = Math.abs(recipeSlots.a.x - recipeSlots.b.x) + 1;

    final static public int itemSize = recipeSlots.acreSquare();

    @Override
    public Chest createUI() {
        Chest chest = new Chest(Bukkit.createInventory(null, 6*9, name));

        ItemStack bluePane = new ItemStack(Material.BLUE_STAINED_GLASS_PANE),
            close = new ItemStack(Material.BARRIER),
            prev = new ItemStack(Material.BLUE_WOOL),
            next = new ItemStack(Material.YELLOW_WOOL);

        chest.fill(bluePane, bluePaneSlots);
        chest.fill(close, closeSlot);
        chest.fill(prev, prevSlot);
        chest.fill(next, nextSlot);

        new PaginationItem(chest, 0).run();

        return chest;
    }

    public static void handleClickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle() != name) return;

        Player player = Util.humanToPlayer(event.getWhoClicked());
        Chest chest = new Chest(event.getInventory());
        Location clicked = Chest.indexToCoordinate(event.getSlot());

        if (! isCurrentInventory(event)) {
            event.setCancelled(true);
        }


        if (clicked.in(recipeSlots)) {
            new DisplayEditRecipeTask(chest, clicked, player).run();
            return;
        }

        if (closeSlot.is(clicked)) {
            player.closeInventory();
        }

        if (nextSlot.is(clicked) || prevSlot.is(clicked)) {
            if (! chest.hasItemAt(clicked)) return;

            int page =  Item.getInt(chest.slot(clicked), "page");

            new PaginationItem(chest, page).run();

            return;
        }
    }

    public static ItemPaginator paginateRecipes(int page) {
        return new ItemPaginator(recipeItems(), itemSize, itemChunk, page);
    }
}
