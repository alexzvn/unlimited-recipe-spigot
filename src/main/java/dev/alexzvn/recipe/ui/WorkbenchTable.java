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
import dev.alexzvn.recipe.recipe.ReturnItem;
import dev.alexzvn.recipe.task.workbench.SetDealWorkbenchTask;
public class WorkbenchTable extends Table {

    final public static String name = "Bàn chế tạo";

    final public static CoupleLocation blackPaneSlot  = new CoupleLocation(1, 1, 9, 5);

    final public static CoupleLocation yellowPaneSlot = new CoupleLocation(1, 6, 9, 6);

    final public static CoupleLocation craftSlot      = new CoupleLocation(2, 2, 4, 4);

    final public static Location recipeSlot           = new Location(8, 3);

    final public static Location arrowSlot           = new Location(6, 3);

    final public static Location guideSlot           = new Location(5, 6);

    @Override
    public Chest createUI() {
        Chest chest = new Chest(Bukkit.createInventory(null, 6*9, name));

        ItemStack blackPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE),
            yellowPane = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE),
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

        return chest;
    }

    public static void handleClickEvent(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        Chest chest = new Chest(inv);
        Location clicked = Chest.indexToCoordinate(event.getSlot());
        Player player = Util.humanToPlayer(event.getWhoClicked());

        if (event.getView().getTitle() != name) return;

        if (event.getClick().equals(ClickType.DOUBLE_CLICK)) {
            chest.fill(Util.airItem(), recipeSlot);
        }

        if (! isCurrentInventory(event)) return;

        event.setCancelled(
            (recipeSlot.isNot(clicked) && craftSlot.notContains(clicked)) ||
            (recipeSlot.is(clicked) && ! Util.isAirItem(event.getCursor()))
        );

        if (guideSlot.is(clicked)) {
            player.closeInventory();
            player.openInventory(new ListRecipeTable().getInventory());
            return;
        }

        if (craftSlot.contains(clicked)) {
            Util.debug("CRAFT");
            onCrafting(chest, clicked, event); return;
        }

        if (recipeSlot.is(clicked)) {
            Util.debug("GETING");
            onGetting(chest, player, event); return;
        }
    }

    protected static void onCrafting(Chest chest, Location clicked, InventoryClickEvent event) {
        ItemStack cursor = Util.isAirItem(event.getCursor()) ? Util.airItem() : event.getCursor().clone();
        ItemStack current = Util.isAirItem(event.getCurrentItem()) ? Util.airItem() : event.getCurrentItem().clone();

        Chest newUI = new WorkbenchTable().getChest();

        chest.fill(cursor, clicked);

        Recipe recipe = RecipeManager.getInstance().find(
            chest.rageMatrixItemStack(craftSlot)
        );

        chest.fill(current, clicked);

        if (recipe != null && !ClickType.DOUBLE_CLICK.equals(event.getClick())) {
            newUI.fill(recipe.getRecipe(), recipeSlot);
        } else {
            newUI.fill(Util.airItem(), recipeSlot);
        }

        newUI.matrixFill(chest.rageMatrixItemStack(craftSlot), craftSlot.a);

        chest.clear();

        Util.humanToPlayer(event.getWhoClicked()).openInventory(newUI.getInventory());
    }

    protected static void onGetting(Chest chest, Player player, InventoryClickEvent event) {
        Chest  newUI  = new WorkbenchTable().getChest();
        Recipe recipe = RecipeManager.getInstance().find(chest.rageMatrixItemStack(craftSlot));
        ReturnItem returnItem;

        if (recipe == null) return;

        SetDealWorkbenchTask deal = new SetDealWorkbenchTask(recipe);

        ItemStack[][] craft = chest.rageMatrixItemStack(craftSlot);

        if (event.getClick().equals(ClickType.SHIFT_LEFT)) {
            returnItem = deal.dealStack(craft);
            Util.sendPlayerItems(returnItem.recipe, player);
        } else {
            returnItem = deal.dealSingle(craft);
        }

        newUI.matrixFill(returnItem.craft, craftSlot.a);

        player.openInventory(newUI.getInventory());
    }

    public static void handleCloseEvent(InventoryCloseEvent event) {
        if (event.getView().getTitle() == "ignore") return;

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
