package me.bigbadhenz.bigbadcraft.accalias;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import me.bigbadhenz.bigbadcraft.ticket.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AccountListener implements Listener {
	
	private Main plugin;
	public AccountListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		String playerName = player.getName();
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(plugin.file))) {
			bw.write(playerName + ".IP: " + player.getAddress());
			bw.newLine();
			bw.close();
		} catch(IOException ex) {
			plugin.getLogger().warning("Unable to write to file: " + plugin.file.toString()); 
			ex.printStackTrace();
		}
	}

}
