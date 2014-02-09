package main.java.net.bigbadcraft.miscellaneous;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.java.net.bigbadcraft.BigBadCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.base.Joiner;

public class GlobalPermissionsCommand implements CommandExecutor {

	private final ChatColor G = ChatColor.GREEN;
	private final ChatColor R = ChatColor.RED;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("globalperm")) {
			processCommand(sender, args);
		}
		
		return true;
	}
	
	private void processCommand(CommandSender sender, String[] args) {
		if (sender.hasPermission("*") || sender.isOp()) {
			if (args.length < 3) {
				sender.sendMessage(R + "Usage: /globalperm <add/remove> <player> <permission>");
			}
			else if (args.length == 3) {
				
				String name = args[1];
				String permission = args[2];
				
				File essentials = Bukkit.getPluginManager().getPlugin("Essentials").getDataFolder();
				File file = new File(essentials, "/userdata/" + name +".yml");
				
				if (args[0].equalsIgnoreCase("add")) {
					if (file.exists()) {	
						List<String> list = new ArrayList<String>();
						
						for (World worlds : Bukkit.getServer().getWorlds()) {
							BigBadCraft.permission.playerAdd(worlds.getName(), name, permission);
							list.add(worlds.getName());
						}
						
						sendMessage(sender, G + "Added '" + permission + "' for " + name + " in worlds: " + Joiner.on(", ").join(list) + ".");
						list.clear();
					} else {
						sendMessage(sender, R + "Could not add permission. " + name + " doesn't exist.");
					}
				}
				else if (args[0].equalsIgnoreCase("remove")) {
					if (file.exists()) {
						List<String> list = new ArrayList<String>();
						
						for (World worlds : Bukkit.getServer().getWorlds()) {
							BigBadCraft.permission.playerRemove(worlds.getName(), name, permission);
							list.add(worlds.getName());
						}
						
						sendMessage(sender, G + "Removed '" + permission + "' for " + name + " in worlds: " + Joiner.on(", ").join(list) + ".");
						list.clear();
					} else {
						sendMessage(sender, R + "Could not add permission. " + name + " doesn't exist.");
					}
				}
				
			}
		}
	}
	
	private void sendMessage(CommandSender sender, String message) {
		if (sender.getName().equals("CONSOLE")) {
			sender.sendMessage(ChatColor.stripColor(message));
		} else {
			sender.sendMessage(message);
		}
	}
	
}
