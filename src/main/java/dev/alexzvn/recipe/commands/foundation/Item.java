package dev.alexzvn.recipe.commands.foundation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.alexzvn.recipe.commands.BaseCommand;
import dev.alexzvn.recipe.ui.ListRecipeTable;

public class Item extends BaseCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (! validateSender(sender)) {
            return false;
        }

        ((Player) sender).openInventory(
            new ListRecipeTable().getInventory()
        );

        return true;
    }

    @Override
    public String getName() {
        return "items";
    }

    @Override
    protected String permission() {
        return null;
    }
}
