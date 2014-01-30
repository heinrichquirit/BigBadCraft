package main.java.net.bigbadcraft.miscellaneous;

import main.java.net.bigbadcraft.BigBadCraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TitleCommand implements CommandExecutor {

	private final ChatColor G = ChatColor.GREEN;
	private final ChatColor Y = ChatColor.YELLOW;
	private final ChatColor W = ChatColor.WHITE;
	private final ChatColor R = ChatColor.RED;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("title")) {
				if (player.hasPermission("bigbadcraft.title")) {
					processCommand(player, args);
				} else {
					player.sendMessage(R + "You do not have permission: bigbadcraft.title");
				}
			}
		}
		return true;
	}
	
	
	private void processCommand(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(G + "Use " + Y + "/title set <title>" + G +
					" to set your title or " + Y + "/title clear" + G + " to clear your title.");
		}
		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("set")) {
				player.sendMessage(R + "Incorrect syntax, usage: /title set <title>");
			}
			else if (args[0].equalsIgnoreCase("clear")) {
				clearPrefix(player);
				player.sendMessage(G + "You've cleared your prefix.");
			}
		}
		else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("set")) {
				String title = ChatColor.translateAlternateColorCodes('&', args[1]);
				int length = ChatColor.stripColor(title).length();
				
				if (length <= 8) {
					setPrefix(player, title + getGroupColor(player));
					player.sendMessage(G + "You've set your prefix to " + W + "[" + title + W + "]");
				} else {
					player.sendMessage(R + "Title must be 8 characters or less.");
				}
				
			}
		}
	}
	
	private void setPrefix(Player player, String prefix) {
		BigBadCraft.chat.setPlayerPrefix(player, W + "[" + prefix + W + "] " + getGroupColor(player));
	}
	
	private void clearPrefix(Player player) {
		BigBadCraft.chat.setPlayerPrefix(player, "");
	}
	
	private ChatColor getGroupColor(Player player) {
		for (String group : BigBadCraft.chat.getPlayerGroups(player)) {
			if (group.equals("New")) {
				return ChatColor.WHITE;
			}
			else if (group.equals("Member")) {
				return ChatColor.GRAY;
			}
			else if (group.equals("Trusted")) {
				return ChatColor.GOLD;
			}
			else if (group.equals("SMPMod")) {
				return ChatColor.DARK_AQUA;
			}
			else if (group.equals("Operator")) {
				return ChatColor.GREEN;
			}
			else if (group.equals("Admin")) {
				return ChatColor.BLUE;
			}
			else if (group.equals("Owner")) {
				return ChatColor.RED;
			}
		}
		return ChatColor.WHITE;
	}
}
