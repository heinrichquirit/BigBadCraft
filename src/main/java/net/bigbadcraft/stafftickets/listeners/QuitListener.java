package main.java.net.bigbadcraft.stafftickets.listeners;

import main.java.net.bigbadcraft.BigPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * User: Heinrich Quirit
 * Last Modified: 9/25/13
 * Time: 6:12 PM
 */
public class QuitListener implements Listener {

    private BigPlugin plugin;

    public QuitListener(BigPlugin plugin) {
        this.plugin = plugin;
    }

    @org.bukkit.event.EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("ticket-list.delete-on-leave")) {
            if (plugin.ticketMang.hasTicket(player)) {
                plugin.ticketMang.deleteTicket(player);
                plugin.ticketMang.helpopDelete(player);
            }
        }
    }
}
