package dev.alexzvn.recipe.commands.admin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.alexzvn.recipe.commands.BaseCommand;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.recipe.RecipeManager;
import dev.alexzvn.recipe.ui.EditRecipeTable;

public class Edit extends BaseCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (! validateSender(sender)) {
            return false;
        }

        String recipe = args[0];

        if (recipe == null) {
            alertInvalidCommand(sender);
            return false;
        }

        RecipeManager manager = RecipeManager.getInstance();

        if (! manager.getMapRecipe().containsKey(recipe)) {
            sender.sendMessage(Util.color("&e Recipe không tồn tại"));
            return false;
        }

        ((Player) sender).openInventory(
            new EditRecipeTable(recipe).getInventory()
        );

        return true;
    }

    @Override
    public String getName() {
        return "edit";
    }

    @Override
    protected String permission() {
        return "recipe.admin.edit";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<String>(
            RecipeManager.getInstance().getNames()
        );
    }
}
