package me.bigbadhenz.bigbadcraft.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Health implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective obj = board.registerNewObjective("playerHealth", "health");
			obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
			obj.setDisplayName("/ " + p.getMaxHealth() + ChatColor.RED + " �?�");
			p.setHealth(p.getHealth());
			p.setScoreboard(board);
		}
	}
}
