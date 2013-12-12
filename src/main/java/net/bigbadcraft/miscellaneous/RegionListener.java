package main.java.net.bigbadcraft.miscellaneous;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;

public class RegionListener implements Listener {

	private final ChatColor CYAN = ChatColor.AQUA;
	
	@EventHandler
	public void onRegionEnter(RegionEnterEvent e) {
		e.getPlayer().sendMessage(CYAN + "You have entered " + e.getRegion().getId() + ".");
	}
	
	@EventHandler
	public void onRegionLeave(RegionLeaveEvent e) {
		e.getPlayer().sendMessage(CYAN + "You have left " + e.getRegion().getId() + ".");
	}
}
