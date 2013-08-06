package me.bigbadhenz.bigbadcraft.other;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SilkTouchDisabler implements Listener 
{
	private final HashSet<Material> disabledMaterials;
	public SilkTouchDisabler() 
	{
		disabledMaterials = new HashSet<Material>();
		disabledMaterials.add(Material.LAPIS_ORE);
		disabledMaterials.add(Material.DIAMOND_ORE);
		disabledMaterials.add(Material.REDSTONE_ORE);
		disabledMaterials.add(Material.COAL_ORE);
		disabledMaterials.add(Material.GLOWING_REDSTONE_ORE);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) 
	{
		if (event.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) 
		{
			if (disabledMaterials.contains(event.getBlock().getType())) 
			{
				event.setCancelled(true);
				event.getPlayer().sendMessage("Silktouch disabled on this block.");
			}
		}
		
		if(event.getBlock().getType().equals(Material.BEDROCK) && event.getBlock().getLocation().getBlockY() == 0) 
		{
			if(!event.getPlayer().isOp()) 
			{
				event.setCancelled(true);
				event.getPlayer().sendMessage("You're not allowed to break bedrock floor.");
			}
		}
	}
}
