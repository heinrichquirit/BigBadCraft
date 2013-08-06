package me.bigbadhenz.bigbadcraft.titles;

import me.bigbadhenz.bigbadcraft.ticket.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TitleCommands implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Please use this command in game.");
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("title")) {
			return title(sender, lbl, args);
		}
		
		return true;
	}
	
	private boolean title(CommandSender sender, String lbl, String[] args) {
		
		Player player = (Player) sender;
		
		if (args.length == 0) {
			
			sender.sendMessage(ChatColor.RED + "Incorrect syntax, usage /" + lbl + " <title>");
		}
		else if (args.length == 1) {
			
			
			if (args[0].length() < 9) {
					
				String prefix = "[" + args[0] + "]";
				
				Main.chat.setPlayerPrefix(player, prefix);
				sender.sendMessage(ChatColor.GREEN + "Set title to '" + args[0] + "'.");
					
			} else {
				
				sender.sendMessage(ChatColor.RED + "You can only use 8 letters in your title.");
			}
			
			if ("clear".equalsIgnoreCase(args[0])) {
				
				Main.chat.setPlayerPrefix(player, null);
			}
				
		}
		
		return true;
	}
}
