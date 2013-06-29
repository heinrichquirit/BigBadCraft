package me.bigbadhenz.bigbadcraft.ticket;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TicketListener implements Listener {
	
	private Main plugin;
	public TicketListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		String playerName = event.getPlayer().getName();
		String playerMessage = ": " + event.getMessage();
		Player p = Bukkit.getPlayer(playerName);
		for (String s : plugin.getConfig().getStringList("trigger")) {
			if (event.getMessage().contains(s)) {
				if (!Main.playerTicket.containsKey(playerName) && !event.getPlayer().hasPermission("staffticket.mod")) {
					plugin.ticketCounter++;
					Bukkit.broadcast("§4" + p.getName() + playerMessage, "staffticket.mod");
					Main.playerTicket.put(p.getName(), playerMessage);
				}
			}
		}
		
		boolean fire = event.getMessage().contains("fire");
		boolean water = event.getMessage().contains("water");
		boolean lava = event.getMessage().contains("lava");
		boolean grief = event.getMessage().contains("grief");
		boolean portal = event.getMessage().contains("portal");
		boolean mod = event.getMessage().contains("mod");
			
		if (fire || water || lava || grief || portal || mod) {
			if (!Main.playerTicket.containsKey(playerName) && !event.getPlayer().hasPermission("staffticket.mod")) {
				plugin.ticketCounter++;
				Bukkit.broadcast("§4" + p.getName() + playerMessage, "staffticket.mod");
				Main.playerTicket.put(p.getName(), playerMessage);
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (Main.playerTicket.containsKey(event.getPlayer().getName())) {
			plugin.ticketCounter--;
			Main.playerTicket.remove(event.getPlayer().getName());
		}
	}

}
