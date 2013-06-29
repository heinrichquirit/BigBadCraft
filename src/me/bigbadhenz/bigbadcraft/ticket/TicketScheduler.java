package me.bigbadhenz.bigbadcraft.ticket;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TicketScheduler extends BukkitRunnable {
	
	private Main plugin;
	
	public TicketScheduler(Main plugin) {
		this.plugin = plugin;
	}

	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.hasPermission("staffticket.mod")) {
				if(Main.playerTicket.isEmpty()) {
					p.sendMessage("§aNotice§f - There are no tickets available.");
				} else {
					p.sendMessage("--- §aBigBadCraft: §f(" + plugin.ticketCounter + ") ---");
					for(Entry<String, String> entry : Main.playerTicket.entrySet()) {
						p.sendMessage("§a" + entry.getKey() + "§f" + entry.getValue());
					}
				}
			}
		}
	}
}
