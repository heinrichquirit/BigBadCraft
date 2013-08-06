package me.bigbadhenz.bigbadcraft.other;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class PreventFireDispense implements Listener 
{
	@EventHandler
	public void onDispense(BlockDispenseEvent event) 
	{
		boolean itsWater = event.getItem().getType().equals(Material.WATER_BUCKET);
		boolean itsLava = event.getItem().getType().equals(Material.LAVA_BUCKET);
		boolean itsFireBall = event.getItem().getType().equals(Material.FIREBALL);
		boolean itsFlint = event.getItem().getType().equals(Material.FLINT_AND_STEEL);
		
		if(itsWater || itsLava || itsFireBall || itsFlint) 
		{
			event.setCancelled(true);
		}
	}
}
