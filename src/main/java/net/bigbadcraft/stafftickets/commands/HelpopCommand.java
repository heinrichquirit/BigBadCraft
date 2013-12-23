package main.java.net.bigbadcraft.stafftickets.commands;

import main.java.net.bigbadcraft.BigBadCraft;
import main.java.net.bigbadcraft.stafftickets.utils.TicketManager;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * User: Heinrich Quirit
 * Last Modified: 9/25/13
 * Time: 6:14 PM
 */
public class HelpopCommand implements CommandExecutor {

    private ChatColor RED = ChatColor.RED;
    private ChatColor BLUE = ChatColor.BLUE;
    private ChatColor WHITE = ChatColor.WHITE;

    protected BigBadCraft plugin;
    private TicketManager ticketMang;


    public HelpopCommand(BigBadCraft plugin) {
        this.plugin = plugin;
        this.ticketMang = plugin.ticketMang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmdObj, String lbl, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmdObj.getName().equalsIgnoreCase("helpop")) {
                return helpop(player, strings);
            }
        }
        return true;
    }

    private boolean helpop(Player player, String[] strings) {
        if (strings.length == 0) {
            player.sendMessage(RED + "Incorrect syntax, usage: /helpop <message>");
            return true;
        }
        if (strings.length > 0) {
            String name = player.getName();
            String message = StringUtils.join(strings, ' ', 0, strings.length);
            if (!ticketMang.hasTicket(player)) {
                ticketMang.createTicket(player, message);
                ticketMang.logPlayersTicket(name, message, player.getLocation());
                ticketMang.notifyStaff(RED + name + ": " + message);
                player.sendMessage(BLUE + "Successfully submitted your ticket, position" + WHITE + ": " + ticketMang.getTickets());
            } else {
                player.sendMessage(BLUE + "You have one outstanding ticket in queue, position" + WHITE + ": " + ticketMang.getTickets());
                player.sendMessage("Outstanding Ticket: " + BLUE + ticketMang.getOutstandingTicket(player));
            }
        }
        return true;
    }
}
