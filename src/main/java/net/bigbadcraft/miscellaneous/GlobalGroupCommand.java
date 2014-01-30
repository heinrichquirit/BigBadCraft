package main.java.net.bigbadcraft.miscellaneous;

import java.util.ArrayList;
import java.util.List;

import main.java.net.bigbadcraft.BigBadCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

public class GlobalGroupCommand implements CommandExecutor {
	
	private final ChatColor G = ChatColor.GREEN;
	private final ChatColor R = ChatColor.RED;
	
	private final String[] groups = { "New", "Member", "Trusted", "SMPMod", "Operator", "Admin", "Owner" };

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (cmd.getName().equalsIgnoreCase("globalgroup")) {
				if (player.hasPermission("bigbadcraft.globalgroup")) {
					if (args.length < 3) {
						player.sendMessage(G + "Usage: /globalgroup set <player> <group>");
					}
					else if (args.length == 3) {
						if (args[0].equalsIgnoreCase("set")) {
							
							String name = args[1];
							String group = args[2];
							
							if (isValidGroup(group)) {
								
								List<String> list = new ArrayList<String>();
								
								for (World worlds : Bukkit.getServer().getWorlds()) {
									BigBadCraft.permission.playerRemoveGroup(worlds.getName(), name, getGroup(name));
									BigBadCraft.permission.playerAddGroup(worlds.getName(), name, group);
									list.add(worlds.getName());
								}
								player.sendMessage(G + "Successfully moved " + name + " to " + group + " in worlds: " + Joiner.on(", ").join(list));
								list.clear();
								
							} else {
								player.sendMessage(R + group + " is not a valid group. Valid groups: " + Joiner.on(", ").join(groups));
							}
							
						}
					}
				} else {
					player.sendMessage(R + "You do not have permission: bigbadcraft.globalgroup");
				}
			}
		}
		
		return true;
	}
	
	private String getGroup(String player) {
		for (World worlds : Bukkit.getServer().getWorlds()) {
			for (String group : BigBadCraft.chat.getPlayerGroups(worlds.getName(), player)) {
				if (group.equals("New")) {
					return "New";
				}
				else if (group.equals("Member")) {
					return "Member";
				}
				else if (group.equals("Trusted")) {
					return "Trusted";
				}
				else if (group.equals("SMPMod")) {
					return "SMPMod";
				}
				else if (group.equals("Operator")) {
					return "Operator";
				}
				else if (group.equals("Admin")) {
					return "Admin";
				}
				else if (group.equals("Owner")) {
					return "Owner";
				}
			}
		}
		return "";
	}
	
	private boolean isValidGroup(String arg) {
		return arg.equalsIgnoreCase("New") || arg.equalsIgnoreCase("Member")
				|| arg.equalsIgnoreCase("Trusted") || arg.equalsIgnoreCase("SMPMod")
				|| arg.equalsIgnoreCase("Operator") || arg.equalsIgnoreCase("Admin")
				|| arg.equalsIgnoreCase("Owner");
	}
	
}
