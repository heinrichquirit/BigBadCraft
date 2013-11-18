package main.java.net.bigbadcraft.preventbaddispense;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

import java.util.HashSet;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 5:40 AM
 */
public class PreventBadDispense implements Listener {

    private HashSet<Material> disabledMats;

    public PreventBadDispense() {
        disabledMats = new HashSet<Material>();
        disabledMats.add(Material.WATER_BUCKET);
        disabledMats.add(Material.LAVA_BUCKET);
        disabledMats.add(Material.FIREBALL);
        disabledMats.add(Material.FLINT_AND_STEEL);
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent event) {
        if (disabledMats.contains(event.getItem().getType())) {
            event.setCancelled(true);
        }
    }
}
