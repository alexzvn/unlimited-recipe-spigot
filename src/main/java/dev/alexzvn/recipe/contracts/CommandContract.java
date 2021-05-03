package dev.alexzvn.recipe.contracts;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandContract {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    public String getName();

    public String[] getAlias();
}
