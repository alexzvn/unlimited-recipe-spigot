package dev.alexzvn.recipe.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.CoupleLocation;
import dev.alexzvn.recipe.helper.Location;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.recipe.Recipe;
import dev.alexzvn.recipe.recipe.RecipeManager;
import dev.alexzvn.recipe.task.workbench.FindRecipeWorkbenchTask;
import dev.alexzvn.recipe.task.workbench.SetDealWorkbenchTask;

public class WorkbenchTable extends Table {

    final public static String name = "Bàn chế tạo";

    final public static CoupleLocation blackPaneSlot  = new CoupleLocation(1, 1, 9, 5);

    final public static CoupleLocation yellowPaneSlot = new CoupleLocation(1, 6, 9, 6);

    final public static CoupleLocation craftSlot      = new CoupleLocation(2, 2, 4, 4);

    final public static Location recipeSlot           = new Location(8, 3);

    final public static Location arrowSlot           = new Location(6, 3);

    final public static Location guideSlot           = new Location(5, 6);

    public void createUI() {
        chest = new Chest(Bukkit.createInventory(null, 6*9, name));

        ItemStack blackPane = new ItemStack(Material.STAINED_GLASS_PANE),
            yellowPane = new ItemStack(Material.STAINED_GLASS_PANE),
            arrow = new ItemStack(Material.ARROW),
            book = new ItemStack(Material.BOOK),
            air = Util.airItem();

        // Fill background
        chest.fill(blackPane, blackPaneSlot);
        chest.fill(yellowPane, yellowPaneSlot);

        // Fill empty slot
        chest.fill(air, craftSlot);
        chest.fill(air, recipeSlot);

        // Fill Special item
        chest.fill(arrow, arrowSlot);
        chest.fill(book, guideSlot);
    }

    public static void handleClickEvent(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        Chest chest = new Chest(inv);
        Location clicked = Chest.indexToCoordinate(event.getSlot());
        Player player = Util.humanToPlayer(event.getWhoClicked());

        if (event.getView().getTitle() == name && event.getClick().equals(ClickType.DOUBLE_CLICK)) {
            event.setCancelled(true);
        }

        Boolean shouldIgnoreEvent = event.getView().getTitle() != name ||
            event.getSlot() != event.getRawSlot();

        if (shouldIgnoreEvent) return;

        boolean shouldCancelEvent = (recipeSlot.isNot(clicked) && craftSlot.notContains(clicked)) ||
            (recipeSlot.is(clicked) && ! Util.isAirItem(event.getCursor()));

        if (shouldCancelEvent) event.setCancelled(true);

        if (guideSlot.is(clicked)) {
            player.closeInventory();
            player.openInventory(new ListRecipeTable().getInventory());
            return;
        }

        if (craftSlot.contains(clicked)) {
            Util.dispatchDelay(new FindRecipeWorkbenchTask(chest), 1); return;
        }

        Recipe recipe = RecipeManager.getInstance().find(chest.rageMatrixItemStack(craftSlot));
        if (recipeSlot.is(clicked) && recipe != null) {
            Boolean stack = event.isShiftClick() && event.isLeftClick();

            Util.dispatchDelay(
                new SetDealWorkbenchTask(recipe, chest, player, stack), 1
            );

            return;
        }
    }

    public static void handleCloseEvent(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();
        Player player = Bukkit.getPlayer(event.getPlayer().getName());

        if (
            event.getView().getTitle() != name || 
            ! InventoryType.CHEST.equals(inv.getType())
        ) return;

        Chest chest = new Chest(inv);

        Util.sendPlayerItems(chest.rageMatrixItemStack(craftSlot), player);
    }

    
}
