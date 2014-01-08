package main.java.net.bigbadcraft.stafftickets.tasks;

import main.java.net.bigbadcraft.BigBadCraft;
import main.java.net.bigbadcraft.stafftickets.utils.Perm;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * User: Heinrich Quirit
 * Last Modified: 9/26/13
 * Time: 11:16 PM
 */
public class BroadcastTask extends BukkitRunnable {

    private ChatColor BLUE = ChatColor.BLUE;
    private ChatColor WHITE = ChatColor.WHITE;

    private BigBadCraft plugin;

    public BroadcastTask(BigBadCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission(Perm.PERM)) {
                if (plugin.ticketMang.isNotEmpty()) {
                    players.sendMessage(plugin.ticketMang.noticeHeader());
                    plugin.ticketMang.sendTicketList(players);
                } else {
                    players.sendMessage(BLUE + "Notice" + WHITE + " - There are no tickets available.");
                }
            }
        }
    }
}
