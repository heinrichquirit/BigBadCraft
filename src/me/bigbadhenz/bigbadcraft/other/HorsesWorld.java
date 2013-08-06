package me.bigbadhenz.bigbadcraft.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class HorsesWorld implements Listener {
	//http://codetidy.com/6097/ AccountCommands
	//http://codetidy.com/6098/ AccountListener
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		World world = Bukkit.getServer().getWorld("BigBadCraft_horses");
		
		if(player.getWorld().equals(world)) {
			if(!player.hasPermission("bigbadcraft.member")) {
				event.setCancelled(true);
				StringBuilder sb = new StringBuilder();
				sb.append(ChatColor.GREEN + "You cannot use this world, if you wish to please\n")
				.append("register at " + ChatColor.BLUE + "www.bigbadcraft.net" + ChatColor.GREEN + " and make sure\n")
				.append(ChatColor.GREEN + "you add your mc character when prompted.\n")
				.append(ChatColor.GREEN + "Otherwise use our default world, thanks!");
				player.sendMessage(sb.toString());
			}
		}
	}
	
	@EventHandler
	public void onTeleport(PlayerChangedWorldEvent event) {
		final Player player = event.getPlayer();
		
		boolean world = event.getFrom().getName().equalsIgnoreCase("BigBadCraft");
		boolean nether = event.getFrom().getName().equalsIgnoreCase("BigBadCraft_nether");
		boolean theend = event.getFrom().getName().equalsIgnoreCase("BigBadCraft_the_end");
		boolean horseworld = event.getFrom().getName().equalsIgnoreCase("BigBadCraft_horses");
		
		if(world || nether || theend || horseworld) {
			player.sendMessage(ChatColor.GREEN + "You are now in " + ChatColor.BLUE + player.getWorld().getName() + ChatColor.GREEN + " world.");
		}
		
		if (player.getWorld().equals(Bukkit.getWorld("world_the_end"))) {
			if (player.hasPermission("bigbadcraft.builders")) {
				player.getInventory().clear();
				player.sendMessage(ChatColor.GREEN + "You're inventory has been cleared");
			} else {
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "spawn");
				player.sendMessage(ChatColor.RED + "You're not suppose to be here.");
			}
		}
	}
}
