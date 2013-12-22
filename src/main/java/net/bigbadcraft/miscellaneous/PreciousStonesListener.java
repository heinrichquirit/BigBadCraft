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
		if (PreciousStones.API().isPStone(b.getLocation())) {
			for (ItemStack stack : b.getDrops()) {
				if (stack.getType() == Material.REDSTONE) {
					stack.setType(Material.AIR);
				}
			}
		}
	}
}
