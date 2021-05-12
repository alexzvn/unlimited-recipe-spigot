package dev.alexzvn.recipe.commands.admin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import dev.alexzvn.recipe.commands.BaseCommand;
import dev.alexzvn.recipe.recipe.RecipeManager;

public class Delete extends BaseCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {



        return false;
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    protected String permission() {
        return "recipe.admin.delete";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<String>(
            RecipeManager.getInstance().getNames()
        );
    }
}
