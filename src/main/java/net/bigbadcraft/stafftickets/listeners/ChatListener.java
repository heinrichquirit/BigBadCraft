package main.java.net.bigbadcraft.stafftickets.listeners;

import main.java.net.bigbadcraft.BigBadCraft;
import main.java.net.bigbadcraft.stafftickets.utils.Perm;
import main.java.net.bigbadcraft.stafftickets.utils.TicketManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * User: Heinrich Quirit
 * Last Modified: 9/25/13
 * Time: 6:11 PM
 */
public class ChatListener implements Listener {

    private ChatColor RED = ChatColor.RED;

    private TicketManager ticketMang;
    private BigBadCraft plugin;

    public ChatListener(BigBadCraft plugin) {
        this.plugin = plugin;
        this.ticketMang = plugin.ticketMang;
    }

    @org.bukkit.event.EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onChat(final AsyncPlayerChatEvent event) {
       final Player player = event.getPlayer();
       for (String message : plugin.getConfig().getStringList("ticket-list.trigger")) {
           if (event.getMessage().contains(message)) {
               if (!ticketMang.hasTicket(player) && !player.hasPermission(Perm.PERM)) {
                   ticketMang.notifyStaff(RED + player.getName() + ": " + event.getMessage());
                   ticketMang.createTicket(player, event.getMessage());
                   if (plugin.getConfig().getBoolean("ticket-list.log-ticket-information")) {
                       ticketMang.logPlayersTicket(player.getName(), event.getMessage(), player.getLocation());
                   }
               }
           }
       }
    }

}
