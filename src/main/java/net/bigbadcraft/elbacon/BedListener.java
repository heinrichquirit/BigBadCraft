package main.java.net.bigbadcraft.elbacon;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/30/13
 * Time: 12:42 AM
 */
public class BedListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().isOp()) {
            String message = event.getMessage();
            if (message.substring(0, message.length()).equalsIgnoreCase("/home elbacon550:bed")
                    || message.substring(0, message.length()).equalsIgnoreCase("/home elbacon:bed")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "No creative mode for you mate.");
            }
        }
    }
}
