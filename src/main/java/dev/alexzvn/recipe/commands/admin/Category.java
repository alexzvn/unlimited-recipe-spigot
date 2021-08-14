package dev.alexzvn.recipe.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import dev.alexzvn.recipe.commands.SubBaseCommand;
import dev.alexzvn.recipe.commands.admin.category.*;
import dev.alexzvn.recipe.contracts.CommandContract;

public class Category extends SubBaseCommand {

    @Override
    public String getName() {
        return "category";
    }

    @Override
    protected boolean onBaseCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (! hasPermission(sender)) {
            alertPermissionRequired(sender);
            return false;
        }

        

        return true;
    }

    @Override
    protected CommandContract[] getCommands() {
        return new CommandContract[] {
            new Add(),
            new Remove()
        };
    }

    @Override
    protected String permission() {
        return "recipe.admin.category";
    }
}
