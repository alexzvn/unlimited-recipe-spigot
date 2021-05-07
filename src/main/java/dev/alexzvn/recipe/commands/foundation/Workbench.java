package dev.alexzvn.recipe.commands.foundation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.alexzvn.recipe.commands.BaseCommand;
import dev.alexzvn.recipe.ui.WorkbenchTable;

public class Workbench extends BaseCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (! this.validateSender(sender)) return false;

        Player player = (Player) sender;

        player.openInventory(new WorkbenchTable().getInventory());

        return true;
    }

    @Override
    public String getName() {
        return "workbench";
    }

    @Override
    protected String permission() {
        return "";
    }

    @Override
    public String[] getAlias() {
        return new String[] {"wb"};
    }
}
