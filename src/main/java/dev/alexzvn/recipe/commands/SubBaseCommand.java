package dev.alexzvn.recipe.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import dev.alexzvn.recipe.contracts.CommandContract;
import dev.alexzvn.recipe.helper.Config;

abstract public class SubBaseCommand extends BaseCommand {
    protected Map<String, CommandContract> excutors = new HashMap<String, CommandContract>();

    public SubBaseCommand () {
        for (CommandContract cmd : getCommands()) {
            excutors.put(cmd.getName(), cmd);

            for (String alias : cmd.getAlias()) {
                excutors.put(alias, cmd);
            }
        }
    }

    @Override
    final public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            return onBaseCommand(sender, cmd, label, args);
        }

        label = args[0];

        if (! excutors.containsKey(label)) {
            sender.sendMessage(Config.message("command_doesnt_exists"));
            return false;
        }

        String[] newArgs = new String[args.length -1];

        for (int i = 1; i < args.length; i++) {
            newArgs[i -1]= args[i];
        }

        return excutors.get(label).onCommand(sender, cmd, label, newArgs);
    }

    abstract protected boolean onBaseCommand(CommandSender sender, Command cmd, String label, String[] args);

    abstract protected CommandContract[] getCommands();
}
