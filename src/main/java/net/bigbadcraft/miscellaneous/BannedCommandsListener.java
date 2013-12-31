package main.java.net.bigbadcraft.miscellaneous;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BannedCommandsListener implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().startsWith("/ban-ip") || event.getMessage().startsWith("/banip")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "Cannot use that command with BungeeCord.");
		}
	}
}
