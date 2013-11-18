package main.java.net.bigbadcraft.playerrider.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 5:45 AM
 */
public class RideCommand implements CommandExecutor {

    private ChatColor GREEN = ChatColor.GREEN;
    private ChatColor RED = ChatColor.DARK_RED;

    @Override
    public boolean onCommand(CommandSender sender, Command cmdObj, String lbl, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmdObj.getName().equalsIgnoreCase("ride")) {
                if (args.length == 0) {
                    player.sendMessage(GREEN + "Usage: /" + lbl + " <player>");
                    return true;
                }
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        player.teleport(target.getLocation());
                        target.setPassenger(player);
                        player.sendMessage(GREEN + "You're now riding " + target.getName() + ". LOL you homo.");
                        target.sendMessage(RED + player.getName() + " is on your shoulders mate.");
                    } else {
                        player.sendMessage(GREEN + args[0] + " is offline!");
                    }
                }
            }
        }
        return true;
    }
}
