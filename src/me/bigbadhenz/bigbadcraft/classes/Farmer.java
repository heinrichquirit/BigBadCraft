package me.bigbadhenz.bigbadcraft.classes;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Farmer implements Listener {

	Set<Material> hoes;
	Random rand;
	
	public Farmer() {
		this.hoes = new HashSet<Material>();
		this.rand = new Random();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFarm(BlockBreakEvent event) {
		final Player player = event.getPlayer();
		
		if (event.getBlock().getType() == Material.WHEAT) {
			
			if (player.hasPermission(Permission.FARMER) && hoes.contains(player.getItemInHand().getType())) {
				
				Collection<ItemStack> drops = event.getBlock().getDrops();
				
				for (ItemStack items : drops) {
				
					player.getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(items.getTypeId(), items.getAmount() * rand.nextInt(2)));
					
				}
			}
		}
	}
	
}