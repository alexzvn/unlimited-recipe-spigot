package dev.alexzvn.recipe.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.alexzvn.recipe.commands.BaseCommand;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.recipe.RecipeManager;
import dev.alexzvn.recipe.ui.NewRecipeTable;

public class Create extends BaseCommand{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (! validateSender(sender)) {
            return false;
        }

        if (args.length < 1) {
            alertInvalidCommand(sender);
            return false;
        }

        String recipe = args[0];
        RecipeManager manager = RecipeManager.getInstance();

        if (manager.getMapRecipe().containsKey(recipe)) {
            sender.sendMessage(Util.color("&e Recipe đã có trong hệ thống"));
            return false;
        }

        ((Player) sender).openInventory(
            new NewRecipeTable(recipe).getInventory()
        );

        return true;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    protected String permission() {
        return "recipe.admin.create";
    }
}
