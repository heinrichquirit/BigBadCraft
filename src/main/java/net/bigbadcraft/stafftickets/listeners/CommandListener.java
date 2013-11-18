package main.java.net.bigbadcraft.stafftickets.listeners;

import main.java.net.bigbadcraft.BigPlugin;
import main.java.net.bigbadcraft.stafftickets.utils.TicketManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * User: Heinrich Quirit
 * Last Modified: 9/27/13
 * Time: 2:07 AM
 */
public class CommandListener implements Listener {

    private TicketManager ticketMang;
    protected BigPlugin plugin;

    public CommandListener(BigPlugin plugin) {
        this.plugin = plugin;
        this.ticketMang = plugin.ticketMang;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        String message = event.getMessage();
        if (message.length() >= 9) {
            if (ticketMang.isHelpopCmd(message)) {
                String trimmedMsg = message.substring(8, message.length());
                ticketMang.helpopCreate(player, trimmedMsg);
            }
        }
    }
}
