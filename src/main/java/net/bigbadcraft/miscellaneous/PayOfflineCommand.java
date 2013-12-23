package main.java.net.bigbadcraft.miscellaneous;

import java.io.File;

import main.java.net.bigbadcraft.BigBadCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayOfflineCommand implements CommandExecutor {
	
	private final ChatColor GREEN = ChatColor.GREEN;
	private final ChatColor RED = ChatColor.RED;
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if (s instanceof Player) {
			Player p = (Player) s;
			if (c.getName().equalsIgnoreCase("opay")) {
				if (a.length < 2) {
					p.sendMessage(GREEN + "-/opay <player> <amount> - To pay users offline.");
					return true;
				} else if (a.length == 2) {
					try {
						String name = a[0].toLowerCase();
						int amount = Integer.parseInt(a[1]);
						
						File essentials = Bukkit.getPluginManager().getPlugin("Essentials").getDataFolder();
						
						File file = new File(essentials, "/userdata/" + name +".yml");
						if (file.exists()) {
							BigBadCraft.economy.depositPlayer(name, amount);
							BigBadCraft.economy.withdrawPlayer(p.getName(), amount);
							p.sendMessage(GREEN + "Successfully made an offline payment of $" + amount + " to " + name + ".");
							Bukkit.dispatchCommand(p, "mail send " + name + " I've made an offline payment of $" 
									+ amount + " to you."); 
							p.sendMessage(GREEN + "Successfully notified " + name + " via mail.");
						} else {
							p.sendMessage(RED + name + " userdata cannot be found, cannot pay user.");
						}
					} catch (NumberFormatException e) {
						p.sendMessage(RED + "You must enter a number for the amount.");
					}
				}
			}
		}
		return true;
	}
}
