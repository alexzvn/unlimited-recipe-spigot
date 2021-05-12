package dev.alexzvn.recipe.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import dev.alexzvn.recipe.ui.EditRecipeTable;
import dev.alexzvn.recipe.ui.ListRecipeTable;
import dev.alexzvn.recipe.ui.NewRecipeTable;
import dev.alexzvn.recipe.ui.ShowRecipeEditTable;
import dev.alexzvn.recipe.ui.WorkbenchTable;

public class InventoryListener implements Listener {

    @EventHandler
    public void clickEventHandler(InventoryClickEvent event) {
        WorkbenchTable.handleClickEvent(event);
        ListRecipeTable.handleClickEvent(event);
        EditRecipeTable.handleClickEvent(event);
        NewRecipeTable.handleClickEvent(event);
        ShowRecipeEditTable.handleClickEvent(event);
    }

    @EventHandler
    public void closeEventHandler(InventoryCloseEvent event) {
        WorkbenchTable.handleCloseEvent(event);
    }

}
