package me.bigbadhenz.bigbadcraft.ticket;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TicketScheduler extends BukkitRunnable {
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission(Permission.PERMISSION)) {
				if (TicketListener.playerTicket.isEmpty()) {
					player.sendMessage("§9Notice§f - There are no tickets available.");
				} else {
					player.sendMessage("§9+-------- §f[ §9Notice" + " §f(" + TicketListener.playerTicket.size() + ")" + " ]§9 --------+");
					for (Entry<String, String> entry : TicketListener.playerTicket.entrySet()) {
						player.sendMessage("§9" + entry.getKey() + "§f: "  + entry.getValue());
					}
				}
			}
		}
	}
}

