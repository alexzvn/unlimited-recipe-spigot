package dev.alexzvn.recipe.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import dev.alexzvn.recipe.helper.Chest;
import dev.alexzvn.recipe.helper.Item;
import dev.alexzvn.recipe.helper.Location;
import dev.alexzvn.recipe.helper.Util;

public class EditRecipeTable extends NewRecipeTable {

    public static final String name = "Sửa recipe mới";

    public final static Location deleteSlot = new Location(9, 1);

    public static final Location backSlot = new Location(1, 3);

    public EditRecipeTable(String recipeName) {
        super(recipeName);
    }

    @Override
    public void createUI() {
        chest = new Chest(Bukkit.createInventory(null, 3*9, name));

        super.createUI();

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemStack workbench = new ItemStack(Material.CRAFTING_TABLE);

        Item.setName(barrier, "Xóa");
        Item.setName(workbench, "Quay lại");

        chest.fill(barrier, deleteSlot);
    }

    public static void handleClickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle() != name) return;

        boolean isInventory = isCurrentInventory(event.getSlot(), event.getRawSlot());
        Chest chest = new Chest(event.getInventory());
        Location clicked = Chest.indexToCoordinate(event.getSlot());
        Player player = Util.humanToPlayer(event.getWhoClicked());

        if (! (clicked.in(craftSlots) && clicked.is(recipeSlot) && isInventory)) {
            event.setCancelled(true);
        }

        if (backSlot.is(clicked)) {
            player.openInventory(new ShowRecipeEditTable().getInventory());
            return;
        }

        if (! saveSlot.is(clicked)) return;

        if (chest.hasItemAt(recipeSlot) && containCraft(chest)) {
            saveRecipe(chest);
        }
    }
}
