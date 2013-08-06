package me.bigbadhenz.bigbadcraft.classes;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Warrior implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if(event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();
			if(player.isBlocking() && (player.hasPermission("BigBadCraft.Warrior") || player.isOp())) {
				if(event.getDamager().getType() == EntityType.PLAYER) {
					return;
				}
				else if (event.getDamager() instanceof Monster){
					event.setCancelled(true);
				}
			}
		}
	}
 
	
}