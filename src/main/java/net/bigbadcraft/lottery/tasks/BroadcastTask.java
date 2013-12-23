package main.java.net.bigbadcraft.lottery.tasks;

import main.java.net.bigbadcraft.BigBadCraft;
import main.java.net.bigbadcraft.lottery.LotteryManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class BroadcastTask extends BukkitRunnable {
	
	private final ChatColor G = ChatColor.GREEN;
	private final ChatColor Y = ChatColor.YELLOW;
	
	private BigBadCraft plugin;
	private LotteryManager lm;
	
	public BroadcastTask(BigBadCraft plugin) {
		this.plugin = plugin;
		lm = this.plugin.lotteryMang;
	}
	
	@Override
	public void run() {
		Bukkit.broadcastMessage(G + "[Lottery] Current pot: " + Y + "$" + lm.getPot() + G + " | Use: " + Y + "/lottery");
		// Broadcast the time in which was drawn - current
		Bukkit.broadcastMessage(G + "Drawing in: " + Y + "");
		lm.broadcastRunning = true;
	}
	
}
