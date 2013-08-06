package me.bigbadhenz.bigbadcraft.classes;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Archer implements Listener {

	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		
		if (event.getPlayer().isSneaking()) {
			
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 100));
			
		} else {
			
			event.getPlayer().removePotionEffect(PotionEffectType.SLOW);
		}
	}
}