package dev.alexzvn.recipe.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.alexzvn.recipe.commands.BaseCommand;
import dev.alexzvn.recipe.ui.ShowRecipeEditTable;

public class Show extends BaseCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (! validateSender(sender)) {
            return false;
        }

        ((Player) sender).openInventory(
            new ShowRecipeEditTable().getInventory()
        );

        return true;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    protected String permission() {
        return "recipe.admin.show";
    }
}
