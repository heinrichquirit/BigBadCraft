package main.java.net.bigbadcraft.miscellaneous;

import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class PreciousStonesListener implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		if (b.getType() == Material.GLOWING_REDSTONE_ORE || b.getType() == Material.REDSTONE_ORE) {
			if (PreciousStones.API().isPStone(b.getLocation())) {
				for (ItemStack stack : b.getDrops()) {
					if (stack.getType() == Material.REDSTONE_WIRE) {
						stack.setType(Material.AIR);
					}
				}
			}
		}
	}
}
