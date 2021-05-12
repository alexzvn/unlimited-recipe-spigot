package dev.alexzvn.recipe.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import dev.alexzvn.recipe.commands.admin.Create;
import dev.alexzvn.recipe.commands.admin.Edit;
import dev.alexzvn.recipe.commands.admin.Show;
import dev.alexzvn.recipe.commands.foundation.Item;
import dev.alexzvn.recipe.commands.foundation.Workbench;
import dev.alexzvn.recipe.commands.plugin.*;
import dev.alexzvn.recipe.contracts.CommandContract;
import dev.alexzvn.recipe.helper.Config;
import dev.alexzvn.recipe.helper.Util;

public class CommandHandler implements CommandExecutor, TabCompleter {
    private static HashMap<String, CommandContract> commands = new HashMap<String, CommandContract>();

    public CommandHandler() {
        registerCommands();
    }

    protected static void register(CommandContract cmd) {

        commands.put(cmd.getName(), cmd);

        for (String alias : cmd.getAlias()) {
            commands.put(alias, cmd);
        }
    }

    protected static void registerAll(CommandContract[] cmd)
    {
        for (CommandContract CommandContract : cmd) {
            register(CommandContract);
        }
    }

    public static boolean exists(String name) {
        return commands.containsKey(name);
    }

    public static CommandContract getExcutor(String name) {
        return commands.get(name);
    }

    public static List<String> getListSubCommand() {
        List<String> subCommands = new ArrayList<String>();

        Set<String> keySet = commands.keySet();

        for (String cmd : keySet.toArray(new String[keySet.size()])) {
            subCommands.add(cmd);
        }

        return subCommands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage(Util.color("[&6&lUnlimitedRecipe&r] &aDevelopment by &6HlV&a for &6&lMythicwolf.net"));
            return false;
        }

        label = args[0];

        if (! exists(label)) {
            sender.sendMessage(Config.message("command_doesnt_exists"));
            return false;
        }

        String[] newArgs = new String[args.length -1];

        for (int i = 1; i < args.length; i++) {
            newArgs[i -1]= args[i];
        }

        return getExcutor(args[0]).onCommand(sender, command, label, newArgs);
    }

    public void registerCommands() {

        CommandContract[] cmds = {
            new Create(),
            new Edit(),
            new Show(),

            new Item(),
            new Workbench(),

            new Version()
        };

        registerAll(cmds);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length < 2) {
            return new ArrayList<String>(commands.keySet());
        }

        if (! commands.containsKey(command.getName())) {
            return new ArrayList<String>();
        }

        CommandContract cmd = commands.get(command.getName());

        String label = args[0];

        String[] newArgs = new String[args.length -1];

        for (int i = 1; i < args.length; i++) {
            newArgs[i -1]= args[i];
        }

        return cmd.onTabComplete(sender, command, label, newArgs);
    }
}
