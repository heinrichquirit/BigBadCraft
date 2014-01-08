package main.java.net.bigbadcraft.playerrider.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 5:46 AM
 */
public class InteractEntityListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked().getType() == EntityType.PLAYER && player.hasPermission("bigbadcraft.ride")) {
            Player target = (Player) event.getRightClicked();
            target.setPassenger(player);
            player.sendMessage(ChatColor.GREEN + "You're riding " + target.getName());
            target.sendMessage(ChatColor.RED + player.getName() + " is on your shoulders mate.");
        }
    }
}
