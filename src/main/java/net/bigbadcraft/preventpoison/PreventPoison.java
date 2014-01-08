package main.java.net.bigbadcraft.preventpoison;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 5:37 AM
 */
public class PreventPoison implements Listener {

    @EventHandler
    public void onPotionSplashEvent(PotionSplashEvent event) {
        for (PotionEffect effect : event.getPotion().getEffects())  {
            for (LivingEntity entity : event.getAffectedEntities()) {
                if (effect.getType() == PotionEffectType.POISON && entity.getType() == EntityType.PLAYER) {
                    continue;
                }
                entity.addPotionEffect(effect);
            }
        }
        event.setCancelled(true);
    }

}
