package main.java.net.bigbadcraft.jukeboxplus;

import java.util.HashMap;

import main.java.net.bigbadcraft.BigBadCraft;
import main.java.net.bigbadcraft.jukeboxplus.IconMenu.OptionClickEvent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class JukeboxListener implements Listener {

	private HashMap<Location, Material> jukeboxTrack = new HashMap<Location, Material>();

	private BigBadCraft plugin;
	public JukeboxListener(BigBadCraft plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent event) {

		final Player player = event.getPlayer();

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.JUKEBOX) {
				if (player.hasPermission("jukeboxplus.use")) {

					event.setCancelled(true);

					final Jukebox jukebox = (Jukebox) event.getClickedBlock().getState();
					final Location jukeboxLocation = jukebox.getLocation();

					IconMenu menu = new IconMenu("Select a disc", 18, new IconMenu.OptionClickEventHandler() {

								@Override
								public void onOptionClick(OptionClickEvent event) {

									String predictedName = event.getName();

									if (predictedName.equals("13")) {
										play(player, jukebox, Material.GOLD_RECORD);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Cat")) {
										play(player, jukebox,Material.GREEN_RECORD);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Blocks")) {
										play(player, jukebox, Material.RECORD_3);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Chirp")) {
										play(player, jukebox, Material.RECORD_4);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Far")) {
										play(player, jukebox, Material.RECORD_5);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Mall")) {
										play(player, jukebox, Material.RECORD_6);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Mellohi")) {
										play(player, jukebox, Material.RECORD_7);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Stal")) {
										play(player, jukebox, Material.RECORD_8);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Strad")) {
										play(player, jukebox, Material.RECORD_9);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Ward")) {
										play(player, jukebox, Material.RECORD_10);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Eleven")) {
										play(player, jukebox, Material.RECORD_11);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Wait")) {
										play(player, jukebox, Material.RECORD_12);
										withdraw(player, jukebox);
										remove(jukeboxLocation);
									} else if (predictedName.equals("Play")) {
										if (!jukebox.isPlaying()) {
											if (jukeboxTrack.containsKey(jukeboxLocation)) {
												jukebox.setPlaying(jukeboxTrack.get(jukeboxLocation));
												player.sendMessage(BigBadCraft.PREFIX + "Playing " + ChatColor.RED + Disc.getEnumName(jukeboxTrack.get(jukeboxLocation)) + ChatColor.WHITE + ".");
											}
										} else {
											player.sendMessage(BigBadCraft.PREFIX + "A track is already playing.");
										}
									} else if (predictedName.equals("Stop")) {
										if (jukebox.isPlaying()) {
											if (!jukeboxTrack.containsKey(jukeboxLocation)) {
												jukeboxTrack.put(jukeboxLocation, jukebox.getPlaying());
												player.sendMessage(BigBadCraft.PREFIX + "Stopped playing " + ChatColor.RED + Disc.getEnumName(jukebox.getPlaying())+ ChatColor.WHITE + ".");
											}
											jukebox.setPlaying(null);
										} else {
											player.sendMessage(BigBadCraft.PREFIX + "There are no tracks to stop.");
										}
									} else if (predictedName.equals("Previous")) {
										if (jukebox.isPlaying()) {
											int currentTrackId = Disc.getEnumId(jukebox.getPlaying());
											jukebox.setPlaying(Disc.getRecord(currentTrackId != 1 ? currentTrackId - 1: null));
											withdraw(player, jukebox);
										}
									} else if (predictedName.equals("Next")) {
										if (jukebox.isPlaying()) {
											int currentTrackId = Disc.getEnumId(jukebox.getPlaying());
											jukebox.setPlaying(Disc.getRecord(currentTrackId != 12 ? currentTrackId + 1: null));
											withdraw(player, jukebox);
										}
									}

									event.setWillDestroy(true);
									event.setWillClose(true);

								}
							}, plugin)
							.setOption(0,new ItemStack(Material.WOOL, 1, (short) 7),"Previous", "Play previous track.")
							.setOption(1, getDiscItem(Disc.THIRTEEN), "13", "Click to play.")
							.setOption(2, getDiscItem(Disc.CAT), "Cat","Click to play.")
							.setOption(3, getDiscItem(Disc.BLOCKS), "Blocks", "Click to play.")
							.setOption(4, getDiscItem(Disc.CHIRP), "Chirp", "Click to play.")
							.setOption(5, getDiscItem(Disc.FAR), "Far", "Click to play.")
							.setOption(6, new ItemStack(Material.WOOL, 1, (short) 5), "Play", "Resume track.")
							.setOption(7, new ItemStack(Material.WOOL, 1, (short) 14), "Stop", "Pause track.")
							.setOption(8, new ItemStack(Material.WOOL, 1, (short) 8), "Next", "Play next track.")
							.setOption(10, getDiscItem(Disc.MALL), "Mall", "Click to play.")
							.setOption(11, getDiscItem(Disc.MELLOHI), "Mellohi", "Click to play.")
							.setOption(12, getDiscItem(Disc.STAL), "Stal", "Click to play.")
							.setOption(13, getDiscItem(Disc.STRAD), "Strad", "Click to play.")
							.setOption(14, getDiscItem(Disc.WARD), "Ward", "Click to play.")
							.setOption(15, getDiscItem(Disc.ELEVEN), "Eleven", "Click to play.")
							.setOption(16, getDiscItem(Disc.WAIT), "Wait", "Click to play.");
					menu.open(player);
				}
			}
		}
	}

	// Enables user to use the stop and play function
	private void remove(Location location) {
		if (jukeboxTrack.containsKey(location)) {
			jukeboxTrack.remove(location);
		}
	}
	
	public ItemStack getDiscItem(Disc disc) {
        switch(Disc.getId(disc)) {
        case 1: return new ItemStack(Material.GOLD_RECORD, 1);
        case 2: return new ItemStack(Material.GREEN_RECORD, 1);
        case 3: return new ItemStack(Material.RECORD_3, 1);
        case 4: return new ItemStack(Material.RECORD_4, 1);
        case 5: return new ItemStack(Material.RECORD_5, 1);
        case 6: return new ItemStack(Material.RECORD_6, 1);
        case 7: return new ItemStack(Material.RECORD_7, 1);
        case 8: return new ItemStack(Material.RECORD_8, 1);
        case 9: return new ItemStack(Material.RECORD_9, 1);
        case 10: return new ItemStack(Material.RECORD_10, 1);
        case 11: return new ItemStack(Material.RECORD_11, 1);
        case 12: return new ItemStack(Material.RECORD_12, 1);
        default: return null;
        }
}

	public void play(Player player, Jukebox jukebox, Material material) {
		if (BigBadCraft.economy.getBalance(player.getName()) >= plugin.costPerDisc) {
			jukebox.setPlaying(material);
		}
	}

	public void withdraw(Player player, Jukebox box) {
		if (BigBadCraft.economy.getBalance(player.getName()) >= plugin.costPerDisc) {
			BigBadCraft.economy.withdrawPlayer(player.getName(), plugin.costPerDisc);
        	player.sendMessage(BigBadCraft.PREFIX + "Now playing " + ChatColor.RED + Disc.getEnumName(box.getPlaying()) + ChatColor.WHITE + ". $" + plugin.costPerDisc + " has been deducted.");
        } else {
        	player.sendMessage(BigBadCraft.PREFIX + "You do not have enough money.");
            box.setPlaying(null);
        }
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent event) {
		if (jukeboxTrack.containsKey(event.getBlock().getLocation())) {
			jukeboxTrack.remove(event.getBlock().getLocation());
		}
	}
}