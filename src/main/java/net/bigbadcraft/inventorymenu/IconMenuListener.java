package main.java.net.bigbadcraft.inventorymenu;

import main.java.net.bigbadcraft.BigPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;

public class IconMenuListener implements Listener {
		
	private IconMenu menu;
	//private final Set<String> set = new HashSet<String>();
	
	protected BigPlugin plugin;
	public IconMenuListener(BigPlugin plugin) {
		this.plugin = plugin;
		menu = new IconMenu("Test inventory...", 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				event.getPlayer().sendMessage("You have chosen " + event.getName());
				event.setWillClose(true);
			}
		}, plugin)
		.setOption(3, new ItemStack(Material.APPLE, 1), "Food", "The food is delicious")
		.setOption(4, new ItemStack(Material.IRON_SWORD, 1), "Weapon", "Weapons are awesome")
		.setOption(5, new ItemStack(Material.EMERALD, 1), "Money", "Money brings happiness");
	}
	
	
	@EventHandler
	public void onRegionEnter(RegionEnterEvent event) {
		final Player player = event.getPlayer();
		player.sendMessage(ChatColor.AQUA + "You have entered " + event.getRegion().getId() + ".");
		if (event.getRegion().getId().equals("spawn1")) {
			if (player.isSneaking()) {
				menu.open(player);
			}
		}
	}
	
	/*
	@EventHandler
	public void onMenuOpen(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		if (player.getItemInHand().getType() == Material.BLAZE_ROD) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR) {
				if (!set.contains(player.getName())) {
					set.add(player.getName());
				} else {
					set.remove(player.getName());
				}
			}
		}
	}
	*/
	
	@EventHandler
	public void onRegionLeave(RegionLeaveEvent event) {
		event.getPlayer().sendMessage(ChatColor.AQUA + "You have left " + event.getRegion().getId() + ".");
	}
	
}
