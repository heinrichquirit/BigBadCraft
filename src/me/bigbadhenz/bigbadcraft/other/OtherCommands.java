package me.bigbadhenz.bigbadcraft.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OtherCommands implements CommandExecutor {
	
	public boolean onCommand(CommandSender cs, Command cmd, String lbl, String[] args) {
		if(cs instanceof Player == false) {
			cs.sendMessage(ChatColor.RED + "Please use this command in game.");
			return true;
		}
		Player p = (Player) cs;
		
		if(cmd.getName().equalsIgnoreCase("fakeop")) {
			if(args.length == 0) {
				cs.sendMessage("Usage: /fakeop <player>");
			} else if(args.length == 1) {
				Player target = Bukkit.getPlayer(args[1]);
				target.sendMessage(ChatColor.YELLOW + "You have been opped.");
			}
		}
		if(cmd.getName().equalsIgnoreCase("work")) {
			p.openWorkbench(p.getLocation(), true);
		}
		if(cmd.getName().equalsIgnoreCase("id")) {
			cs.sendMessage(ChatColor.YELLOW + "Item ID: " + p.getItemInHand().getTypeId());
		}
		if(cmd.getName().equals("coal")) {
			ItemStack coal = new ItemStack(Material.COAL_ORE);
			p.getInventory().addItem(coal);
			cs.sendMessage(ChatColor.GREEN + "Spawned a " + ChatColor.BLACK + "coal" + ChatColor.GREEN + " pstone." +
					" Please use this only at users request for bugged pstones!");
		}
		if(cmd.getName().equals("lapis")) {
			ItemStack lapis = new ItemStack(Material.LAPIS_ORE);
			p.getInventory().addItem(lapis);
			cs.sendMessage(ChatColor.GREEN + "Spawned a " + ChatColor.BLUE + "lapis" + ChatColor.GREEN + " pstone." +
					" Please use this only at users request for bugged pstones!");
		}
		if(cmd.getName().equals("diamond")) {
			ItemStack diamond = new ItemStack(Material.DIAMOND_ORE);
			p.getInventory().addItem(diamond);
			cs.sendMessage(ChatColor.GREEN + "Spawned a " + ChatColor.AQUA + "diamond" + ChatColor.GREEN + " pstone." +
					" Please use this only at users request for bugged pstones!");
		}
		if(cmd.getName().equals("redstone")) {
			ItemStack redstone = new ItemStack(Material.REDSTONE_ORE);
			p.getInventory().addItem(redstone);
			cs.sendMessage(ChatColor.GREEN + "Spawned a " + ChatColor.RED + "redstone" + ChatColor.GREEN + " pstone." +
					" Please use this only at users request for bugged pstones!");
		}
		return true;
		
	}
	
}
