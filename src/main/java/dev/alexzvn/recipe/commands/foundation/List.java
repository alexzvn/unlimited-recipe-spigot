package dev.alexzvn.recipe.commands.foundation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import dev.alexzvn.recipe.commands.BaseCommand;

public class List extends BaseCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (! this.validateSender(sender)) return false;

        return false;
    }

    @Override
    public String getName() {
        return "all";
    }

    @Override
    protected String permission() {
        return "";
    }
}
