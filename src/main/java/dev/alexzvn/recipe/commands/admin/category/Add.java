package dev.alexzvn.recipe.commands.admin.category;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import dev.alexzvn.recipe.commands.BaseCommand;

public class Add extends BaseCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (! hasPermission(sender)) {
            alertPermissionRequired(sender);
            return false;
        }

        if (args.length < 1) {
            alertInvalidCommand(sender);
            return false;
        }

        return false;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    protected String permission() {
        return "recipe.admin.category";
    }
}
