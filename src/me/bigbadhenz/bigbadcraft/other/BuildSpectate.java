package me.bigbadhenz.bigbadcraft.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BuildSpectate implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		World world = Bukkit.getServer().getWorld("plotworld2");
		if(player.getWorld().equals(world)) {
			if(!player.hasPermission("bigbadcraft.spectate")) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You can only spectate this event.");
			}
		}
	}
}
