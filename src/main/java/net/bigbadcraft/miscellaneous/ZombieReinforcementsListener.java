package main.java.net.bigbadcraft.miscellaneous;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class ZombieReinforcementsListener implements Listener {
	
	// Because this is the worst idea ever 
	
	@EventHandler
	public void onZombieSpawn(CreatureSpawnEvent event) {
		if (event.getSpawnReason() == SpawnReason.REINFORCEMENTS) {
			event.setCancelled(true);
		}
	}
}
