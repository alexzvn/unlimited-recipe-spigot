package dev.alexzvn.recipe.commands.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import dev.alexzvn.recipe.UnlimitedRecipe;
import dev.alexzvn.recipe.commands.BaseCommand;
import dev.alexzvn.recipe.helper.Config;
import dev.alexzvn.recipe.helper.Util;

public class Version extends BaseCommand {

    protected String[] alias = {"ver"};

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        sender.sendMessage(Util.color(
            Config.getString("message_prefix") +
            "&2Plugin version &6" + UnlimitedRecipe.version
        ));

        return true;
    }

    @Override
    public String getName() {
        return "version";
    }

    @Override
    protected String permission() {
        return "";
    }
}
