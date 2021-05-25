package dev.alexzvn.recipe.commands.admin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.alexzvn.recipe.commands.BaseCommand;
import dev.alexzvn.recipe.helper.Util;
import dev.alexzvn.recipe.recipe.RecipeManager;

public class Delete extends BaseCommand {

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

        if (! manager.getMapRecipe().containsKey(recipe)) {
            Util.tell((Player) sender, "&e Recipe đã có trong hệ thống");
            return false;
        }

        manager.remove(recipe);

        sender.sendMessage(Util.color("&a Đã xoá thành công " + recipe));

        return true;
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
