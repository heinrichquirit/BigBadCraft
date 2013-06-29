package me.bigbadhenz.bigbadcraft.other;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PreventPoisonDamage implements Listener {
	
	@EventHandler
	public void onPotionSplashEvent(PotionSplashEvent event) {
		for(PotionEffect effect : event.getPotion().getEffects()) {
			for(LivingEntity entity : event.getAffectedEntities()) {
				if(effect.getType().equals(PotionEffectType.POISON) && entity instanceof Player) {
					continue;
				}
				entity.addPotionEffect(effect);
			}
		}
		event.setCancelled(true);
	}
		
}
