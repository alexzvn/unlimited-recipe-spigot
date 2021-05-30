package dev.alexzvn.recipe.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.alexzvn.recipe.contracts.CommandContract;
import dev.alexzvn.recipe.helper.Config;
import dev.alexzvn.recipe.helper.Util;

abstract public class BaseCommand implements CommandContract, CommandExecutor {

    protected boolean validateSender(CommandSender sender) {
        if (! this.hasPermission(sender)) {
            this.alertPermissionRequired(sender);
            return false;
        }

        if (this.isSendFromConsole(sender)) {
            this.alertExcuteInGameOnly(sender);
            return false;
        }

        return true;
    }

    protected boolean hasPermission(CommandSender sender) {
        return permission() != null && sender.hasPermission(permission());
    }

    protected void alert(CommandSender sender, String message) {
        sender.sendMessage(Util.color(message));
    }

    protected void alertInvalidCommand(CommandSender sender) {
        sender.sendMessage(Config.message("command_invalid"));
    }

    protected void alertPermissionRequired(CommandSender sender) {
        sender.sendMessage(Config.message("missing_permission"));
    }

    protected void alertExcuteInGameOnly(CommandSender sender) {
        sender.sendMessage(Config.message("command_must_send_ingame"));
    }

    abstract protected String permission();

    protected boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    protected boolean isSendFromConsole(CommandSender sender) {
        return !this.isPlayer(sender);
    }

    public String[] getAlias() {
        return new String[] {};
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<String>();
    }
}
