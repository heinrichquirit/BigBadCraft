package main.java.net.bigbadcraft.warns;

import main.java.net.bigbadcraft.BigBadCraft;
import main.java.net.bigbadcraft.utils.CommandLogger;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 6:06 AM
 */
public class WarnsCommand implements CommandExecutor {

    private final ChatColor RED = ChatColor.RED;
    private WarnsManager warnsMang;
    protected BigBadCraft plugin;

    public WarnsCommand(BigBadCraft plugin) {
        this.plugin = plugin;
        this.warnsMang = plugin.warnsMang;
    }

    public boolean onCommand(CommandSender sender, Command cmdObj, String lbl, String[] strings) {
        if ((sender instanceof Player)) {
            Player player = (Player)sender;
            if (cmdObj.getName().equalsIgnoreCase("warn")) {
                return warn(player, strings);
            }
        }
        return true;
    }

    private boolean warn(Player player, String[] strings) {
        if ((strings.length == 0) || (strings.length == 1)) {
            player.sendMessage(this.RED + "Incorrect syntax, usage: /warn <player> <message>");
            return true;
        } else if (strings.length >= 2) {
            Player target = Bukkit.getPlayer(strings[0]);
            String message = StringUtils.join(strings, ' ', 1, strings.length);
            if (target != null) {
            	if (!target.hasPermission("bigbadcraft.staff")) {
	                this.warnsMang.warnUser(player, target, message);
	                Bukkit.broadcastMessage(RED + target.getName() + " has been warned for an offense: (" + warnsMang.getWarns(target) + "/3)");
            	} else {
            		CommandLogger.logWarnCommand(player.getName(), target.getName(), message);
            		player.sendMessage(RED + "You should not be warning a staff member. Your name has been logged.");
            	}
            } else {
                player.sendMessage(RED + strings[0] + " is offline!");
            }
        }
        return true;
    }
}
