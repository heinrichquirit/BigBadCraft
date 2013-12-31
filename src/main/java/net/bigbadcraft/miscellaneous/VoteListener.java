package main.java.net.bigbadcraft.miscellaneous;

import java.util.HashMap;
import java.util.logging.Level;

import main.java.net.bigbadcraft.BigBadCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

enum PrefixType {
	INFO("[Votifier Info] "), WARNING("[Votifier Warning] "), SEVERE("[Votifier Severe] ");
	
	String name;
	
	PrefixType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}

public class VoteListener implements Listener {

	private final ChatColor CYAN = ChatColor.AQUA;
	private final ChatColor WHITE = ChatColor.WHITE;
	
	private final HashMap<String, Integer> votes = new HashMap<String, Integer>();
	
	private BigBadCraft plugin;
	public VoteListener(BigBadCraft plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onVote(VotifierEvent event) {
		Vote vote = event.getVote();
		Player player = Bukkit.getPlayer(vote.getUsername());
		
		if (player != null) {
			Bukkit.broadcastMessage(CYAN + player.getName() + WHITE + " has voted @ " + CYAN + vote.getServiceName());
			logVote(PrefixType.INFO, player.getName(), vote.getServiceName());
			player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
			player.sendMessage("You received " + CYAN + "1" + WHITE + " diamond. Thanks for voting!");
		} else {
			Bukkit.broadcastMessage(CYAN + vote.getUsername() + WHITE + " has voted offline @ " + CYAN + vote.getServiceName());
			storeVote(vote.getUsername());
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new BukkitRunnable() {
			@Override
			public void run() {
				if (votes.containsKey(player.getName())) {
					player.getInventory().addItem(new ItemStack(Material.DIAMOND, getStoredVotes(player.getName())));
					player.sendMessage("You received " + CYAN + getStoredVotes(player.getName()) + WHITE + " diamond(s) from voting offline. Thanks for voting!");
					votes.remove(player.getName());
				}
			}
		}, 20 * 5);
	}
	
	private void logVote(PrefixType type, String name, String service) {
		Bukkit.getLogger().log(Level.INFO, type.getName() + name + " has voted @ " + service);
	}
	
	private void storeVote(String name) {
		if (!votes.containsKey(name)) {
			votes.put(name, 1);
		} else {
			votes.put(name, getStoredVotes(name) + 1);
		}
	}
	
	private int getStoredVotes(String name) {
		return votes.get(name);
	}
	
}
