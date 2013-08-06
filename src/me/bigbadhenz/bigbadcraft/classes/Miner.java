package me.bigbadhenz.bigbadcraft.classes;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Miner implements Listener {
	
	protected Set<Material> materialOre;
	protected Set<Material> pickaxeTool; 
	
	public Miner() {
		
		materialOre = new HashSet<Material>();
		materialOre.add(Material.GOLD_ORE);
		materialOre.add(Material.IRON_ORE);
		materialOre.add(Material.COAL_ORE);
		materialOre.add(Material.LAPIS_ORE);
		materialOre.add(Material.DIAMOND_ORE);
		materialOre.add(Material.REDSTONE_ORE);
		materialOre.add(Material.EMERALD_ORE);
		
		pickaxeTool = new HashSet<Material>();
		pickaxeTool.add(Material.WOOD_PICKAXE);
		pickaxeTool.add(Material.STONE_PICKAXE);
		pickaxeTool.add(Material.GOLD_PICKAXE);
		pickaxeTool.add(Material.IRON_PICKAXE);
		pickaxeTool.add(Material.DIAMOND_PICKAXE);
		
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		
		if (materialOre.contains(event.getBlock().getType())) {
			
			if (pickaxeTool.contains(event.getPlayer().getItemInHand().getType())) {
				
				if (event.getPlayer().hasPermission(Permission.MINER)) {
					
					event.getBlock().breakNaturally();
					
				}
			}
		}
	}
}
