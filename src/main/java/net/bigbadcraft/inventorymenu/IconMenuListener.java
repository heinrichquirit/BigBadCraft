package main.java.net.bigbadcraft.inventorymenu;

import main.java.net.bigbadcraft.BigPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;

public class IconMenuListener implements Listener {
		
	protected IconMenu menu;
	
	protected BigPlugin plugin;
	@SuppressWarnings("deprecation")
	public IconMenuListener(BigPlugin plugin) {
		this.plugin = plugin;
		menu = new IconMenu(ChatColor.GREEN + "Welcome to BigBadCraft", 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				Player player = event.getPlayer();
				String clicked = event.getName();
				switch(clicked) {
				case "SMP": 
					Bukkit.dispatchCommand(player, "spawn"); 
					break;
				case "Build": 
					Bukkit.dispatchCommand(player, "server creative"); 
					break;
				}
			}
		}, plugin)
		.setOption(3, new ItemStack(Material.WOOL, 1, DyeColor.RED.getData()), "SMP", "Survive and thrive")
		.setOption(4, new ItemStack(Material.WOOL, 1, DyeColor.ORANGE.getData()), "Build", "Craft and develop your building skills")
		.setOption(5, new ItemStack(Material.BOW, 1, DyeColor.YELLOW.getData()), "More to come", "Coming soon.");
	}
	
	
	@EventHandler
	public void onRegionEnter(RegionEnterEvent event) {
		final Player player = event.getPlayer();
		player.sendMessage(ChatColor.AQUA + "You have entered " + event.getRegion().getId());
		
		/*
		 * This is the region (hubspawn) where users will have their inventory saved and loaded
		 * once entering and leaving the region. Their inventories will be replaced with a starter inventory
		 * (books, gui menu etc) with some information regarding ranks, ticket system, protection stones and 
		 * everything in general.
		 */
		if (event.getRegion().getId().equals("centralhub")) {
			// Not sure if it's a good idea to keep the SQL connection open, but I'll assume
			// it is, rather to have a constant opening and closing connection.
			
			/*
			 * This is where your magic starts Skepter.
			 * When player enters region, lets save their inventory onto the flatfile
			 * including metadata.
			 * Once inventory has been 'temporarily' saved, let's replace it with said starter 
			 * inventory.
			 * Once the player leaves the region, lets successfully load back their
			 * inventory and delete their 'temporary' saved inventory in the database as we will
			 * no longer be needing it.
			 */
		}
	}
	
	
	@EventHandler
	public void onRegionLeave(RegionLeaveEvent event) {
		event.getPlayer().sendMessage(ChatColor.AQUA + "You have left " + event.getRegion().getId() + ".");
	}
	
}
