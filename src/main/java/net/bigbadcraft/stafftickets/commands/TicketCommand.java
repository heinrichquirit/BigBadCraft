package main.java.net.bigbadcraft.stafftickets.commands;

import main.java.net.bigbadcraft.BigBadCraft;
import main.java.net.bigbadcraft.stafftickets.utils.TicketManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * User: Heinrich Quirit
 * Last Modified: 9/25/13
 * Time: 6:14 PM
 */
public class TicketCommand implements CommandExecutor {

    private ChatColor WHITE = ChatColor.WHITE;
    private ChatColor BLUE = ChatColor.BLUE;
    private ChatColor RED = ChatColor.RED;

    private BigBadCraft plugin;
    private TicketManager ticketMang;

    public TicketCommand(BigBadCraft plugin) {
        this.plugin = plugin;
        this.ticketMang = plugin.ticketMang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmdObj, String lbl, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmdObj.getName().equalsIgnoreCase("ticket")) {
                return ticket(player, strings);
            }
        }
        return true;
    }

    private boolean ticket(Player player, String[] strings) {
        if (strings.length == 0) {
            player.sendMessage(ticketMang.helpHeader());
            player.sendMessage(ticketMang.helpMenu());
            return true;
        } else if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("list")) {
                if (ticketMang.isNotEmpty()) {
                    player.sendMessage(ticketMang.ticketListHeader());
                    ticketMang.sendTicketList(player);
                    player.sendMessage(ticketMang.helpFooter());
                } else {
                    player.sendMessage(BLUE + "There are no tickets available.");
                }
            } else if (strings[0].equalsIgnoreCase("readfile")) {
                player.sendMessage(RED + "Incorrecy syntax, usage: /ticket readfile <player>");
            } else if (strings[0].equalsIgnoreCase("view")) {
                player.sendMessage(RED + "Incorrect syntax, usage: /ticket view <player>");
            } else if (strings[0].equalsIgnoreCase("tp")) {
                player.sendMessage(RED + "Incorrect syntax, usage: /ticket tp <player>");
            } else if (strings[0].equalsIgnoreCase("cl")) {
                player.sendMessage(RED + "Incorrect syntax, usage: /ticket cl <player>");
            } else if (strings[0].equalsIgnoreCase("del")) {
                player.sendMessage(RED + "Incorrect syntax, usage: /ticket del <player> or");
                player.sendMessage(RED + "Incorrecy syntax, usage: /ticket del <player> <index>");
            } else if (strings[0].equalsIgnoreCase("clear")) {
                if (ticketMang.isNotEmpty()) {
                    ticketMang.clearTickets();
                    ticketMang.helpopClear();
                    player.sendMessage(BLUE + "Ticket list has been cleared.");
                } else {
                    player.sendMessage(BLUE + "Ticket list is already empty.");
                }
            }
        } else if (strings.length == 2) {
            Player target = Bukkit.getPlayer(strings[1]);
            if (strings[0].equalsIgnoreCase("readfile")) {
                if (new File(plugin.getDataFolder() + "/ticketlogs", strings[1] + ".txt").exists()) {
                    ticketMang.readLoggedTickets(player, target);
                } else {
                    player.sendMessage(BLUE + strings[1] + WHITE + " has no logged ticket(s)");
                }
            } else if (target != null) {
                if (strings[0].equalsIgnoreCase("view")) {
                    if (ticketMang.hasHelpop(target)) {
                        ticketMang.displayTicket(player, target);
                    } else {
                        player.sendMessage(BLUE + target.getName() + WHITE + " has no helpop ticket(s).");
                    }
                } else if (strings[0].equalsIgnoreCase("tp")) {
                    if (ticketMang.hasTicket(target)) {
                        ticketMang.deleteTicket(target);
                        ticketMang.helpopDelete(target);
                        player.sendMessage("Teleporting to " + BLUE + target.getName() + WHITE + "..");
                        player.teleport(target.getLocation());
                        ticketMang.notifyStaff(BLUE + player.getName() + WHITE + " is now handling with "
                        		+ BLUE + target.getName() + WHITE + "'s issue.");
                    } else {
                        player.sendMessage(BLUE + target.getName() + WHITE + " has no ticket(s).");
                    }
                } else if (strings[0].equalsIgnoreCase("cl")) {
                    if (ticketMang.hasTicket(target)) {
                        ticketMang.deleteTicket(target);
                        ticketMang.helpopDelete(target);
                        player.sendMessage("Claimed " + BLUE + target.getName() + WHITE + "'s ticket.");
                        ticketMang.notifyStaff(BLUE + player.getName() + WHITE + " has claimed " + BLUE 
                        		+ target.getName() + WHITE + "'s ticket.");
                    } else {
                        player.sendMessage(BLUE + target.getName() + WHITE + " has no ticket(s).");
                    }
                } else if (strings[0].equalsIgnoreCase("del")) {
                    if (ticketMang.hasTicket(target)) {
                        ticketMang.deleteTicket(target);
                        ticketMang.helpopDelete(target);
                        player.sendMessage("Deleted " + BLUE + target.getName() + WHITE + "'s ticket.");
                        ticketMang.notifyStaff(BLUE + player.getName() + WHITE + " has deleted " + BLUE 
                        		+ target.getName() + WHITE + "'s ticket.");
                    } else {
                        player.sendMessage(BLUE + target.getName() + WHITE + " has no ticket(s).");
                    }
                }
            } else {
                player.sendMessage(RED + "Error: " + strings[1] + " is offline!");
            }
        } else if (strings.length == 3) {
            try {
                Player target = Bukkit.getPlayer(strings[1]);
                int index = Integer.valueOf(strings[2]);
                if (strings[0].equalsIgnoreCase("del")) {
                    if (index <= ticketMang.getTicketAmount(target)) {
                        ticketMang.helpopDelete(target, index);
                        player.sendMessage("Deleted " + BLUE + target.getName() + WHITE + "'s ticket @index " + BLUE + index + ".");
                    } else {
                        player.sendMessage(BLUE + target.getName() + WHITE
                                + " only has " + BLUE + ticketMang.getTicketAmount(target) + WHITE + " tickets.");
                    }
                }
            } catch (NumberFormatException e) {
                player.sendMessage(RED + "Error: You must use a number for your index argument.");
            }
        }
        return true;
    }
}