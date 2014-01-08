package main.java.net.bigbadcraft.nonmember;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 5:51 AM
 */
public class BlockChangeListener implements Listener {

    private final ChatColor GREEN = ChatColor.GREEN;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        if (player.getWorld().equals(Bukkit.getServer().getWorld("BigBadCraft_survival"))) {
            if (!player.hasPermission("bigbadcraft.member")) {
                player.sendMessage(GREEN + "Hey, you're new!");
                player.sendMessage(GREEN + "Feel free to look around this world.");
                player.sendMessage(GREEN + "But if you want to start building, please register");
                player.sendMessage(GREEN + "for free membership @ " + ChatColor.YELLOW + "www.bigbadcraft.net");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        if (player.getWorld().equals(Bukkit.getServer().getWorld("BigBadCraft_survival"))) {
            if (!player.hasPermission("bigbadcraft.member")) {
                player.sendMessage(GREEN + "Hey, you're new!");
                player.sendMessage(GREEN + "Feel free to look around this world.");
                player.sendMessage(GREEN + "But if you want to start building, please register");
                player.sendMessage(GREEN + "for free membership @ " + ChatColor.YELLOW + "www.bigbadcraft.net");
                event.setCancelled(true);
            }
        }
    }
}
