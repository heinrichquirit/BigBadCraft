package main.java.net.bigbadcraft.miscellaneous;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkOnJoinListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(ChatColor.YELLOW + event.getPlayer().getName() + " has joined the network.");
		Firework firework = (Firework) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.FIREWORK);
		randomizeFirework(firework);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onLeave(PlayerQuitEvent event) {
		event.setQuitMessage(ChatColor.YELLOW + event.getPlayer().getName() + " has left the network.");
	}
	
	private void randomizeFirework(Firework firework) {
		FireworkMeta meta = firework.getFireworkMeta();
		Random random = new Random();
		int randValue = random.nextInt(5) + 1;
		Type type = Type.BALL;
		
		switch(randValue) {
		case 1: type = Type.BALL; break;
		case 2: type = Type.BALL_LARGE; break;
		case 3: type = Type.BURST; break;
		case 4: type = Type.CREEPER; break;
		case 5: type = Type.STAR; break;
		}
		
		int r1i = random.nextInt(17) + 1;
		int r2i = random.nextInt(17) + 1;
		
		Color c1 = getColor(r1i);
		Color c2 = getColor(r2i);
		
		FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(random.nextBoolean()).build();
		meta.addEffect(effect);
		int randPower = random.nextInt(2) + 1;
		meta.setPower(randPower);
		firework.setFireworkMeta(meta);
	}

	private Color getColor(int i) {
		Color c = null;
		switch(i) {
		case 1: c = Color.AQUA; break;
		case 2: c = Color.BLACK; break;
		case 3: c = Color.BLUE; break;
		case 4: c = Color.FUCHSIA; break;
		case 5: c = Color.GRAY; break;
		case 6: c = Color.GREEN; break;
		case 7: c = Color.LIME; break;
		case 8: c = Color.MAROON; break;
		case 9: c = Color.NAVY; break;
		case 10: c = Color.OLIVE; break;
		case 11: c = Color.ORANGE; break;
		case 12: c = Color.PURPLE; break;
		case 13: c = Color.RED; break;
		case 14: c = Color.SILVER; break;
		case 15: c = Color.TEAL; break;
		case 16: c = Color.WHITE; break;
		case 17: c = Color.YELLOW; break;
		}
		return c;
	}
}
