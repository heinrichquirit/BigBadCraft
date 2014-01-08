package main.java.net.bigbadcraft.miscellaneous;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemIDCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("id")) {
				if (args.length == 0) {
					int id = player.getItemInHand().getTypeId();
					byte data = player.getItemInHand().getData().getData();
					player.sendMessage(ChatColor.GREEN + "Item ID: " + ChatColor.YELLOW
							+ id + ((data != 0 || data == -1) ? ":"+data : ""));
				}
			}
		}
		return true;
	}
	
	
}
