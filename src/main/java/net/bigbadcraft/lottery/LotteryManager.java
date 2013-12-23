package main.java.net.bigbadcraft.lottery;

import java.util.HashMap;

import main.java.net.bigbadcraft.BigBadCraft;

import org.bukkit.entity.Player;

public class LotteryManager {
	
	private final HashMap<String, Integer> lotteryTickets = new HashMap<String, Integer>();
	
	private int potAmount = 0;
	public boolean broadcastRunning = false;
	
	public void addToPot(int amount) {
		this.potAmount = this.potAmount + amount;
	}
	
	public void buyTicket(Player player) {
		BigBadCraft.economy.withdrawPlayer(player.getName(), 100);
		lotteryTickets.put(player.getName(),
				!lotteryTickets.containsKey(
						player.getName()) ? 1 : lotteryTickets.get(player.getName()) + 1);
		this.potAmount = this.potAmount + 100;
	}
	
	public void buyTicket(Player player, int amount) {
		int total = amount * 100;
		BigBadCraft.economy.withdrawPlayer(player.getName(), total);
		lotteryTickets.put(player.getName(),
				!lotteryTickets.containsKey(
						player.getName()) ? amount : lotteryTickets.get(player.getName()) + amount);
		this.potAmount = this.potAmount + total;
	}
	
	public int getTicket(Player player) {
		return lotteryTickets.get(player.getName());
	}
	
	public boolean contains(Player player) {
		return lotteryTickets.containsKey(player.getName());
	}
	
	public HashMap<String, Integer> getMap() {
		return lotteryTickets;
	}
	
	public int getPot() {
		return potAmount;
	}
	
}
