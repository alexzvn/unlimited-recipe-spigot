package dev.alexzvn.recipe.contracts;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public interface CommandContract extends TabCompleter {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    public String getName();

    public String[] getAlias();
}
