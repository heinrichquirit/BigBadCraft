package main.java.net.bigbadcraft.silktouchdisabler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 5:30 AM
 */
public class SilkTouchFilter implements Listener {

    private HashSet<Material> disabledOres;

    public SilkTouchFilter() {
        disabledOres = new HashSet<Material>();
        disabledOres.add(Material.COAL_ORE);
        disabledOres.add(Material.LAPIS_ORE);
        disabledOres.add(Material.DIAMOND_ORE);
        disabledOres.add(Material.REDSTONE_ORE);
        disabledOres.add(Material.GLOWING_REDSTONE_ORE);
        disabledOres.add(Material.EMERALD_ORE);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        if (player.getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
            if (disabledOres.contains(event.getBlock().getType())) {
                if (!player.isOp()) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Don't try to exploit protection stones.");
                }
            }
        }
    }
}
