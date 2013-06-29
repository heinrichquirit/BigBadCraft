
package me.bigbadhenz.bigbadcraft.ticket;

import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	
	private Main plugin;
	
	public Commands(Main plugin, Main main) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cs instanceof Player == false) {
			cs.sendMessage("Please use this command in game.");
			return true;
		}
		
		Player player = (Player) cs;
		String playerName = player.getName();
		
		if(cmd.getName().equalsIgnoreCase("helpop")) {
			if(args.length == 0) {
				cs.sendMessage("§aBigBadCraft §f- Please use '/helpop <message>'");
			}
			if(args.length > 0) {
				String message = StringUtils.join(args, ' ', 0, args.length);
				plugin.ticketCounter++;
				for(Player p: Bukkit.getOnlinePlayers()) {
					if(p.hasPermission("staffticket.mod")) {
						p.sendMessage("§4" + playerName + ": " + message);
					}
				}
				Main.helpopNames.put(playerName, " - View this players ticket.");
				Main.helpopTickets.add(message + "\n");
				cs.sendMessage("§aYou have submitted ticket. Queue Position: " + "§f" + plugin.ticketCounter);
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("ticket")) {
			if(args.length == 0) {
				StringBuilder sb = new StringBuilder();
				sb.append("--- §aTicket Info§f ---\n")
				.append("§a/ticket list: §fA lists of all open tickets.\n")
				.append("§a/ticket tp <player>: §fClaims and teleports specified ticket.\n")
				.append("§a/ticket cl <player>: §fClaims specified ticket.\n")	
				.append("§a/ticket del <player>: §fDeletes specified ticket.\n")
				.append("§a/ticket view <player>: §fViews specified ticket.\n")
				.append("§a/ticket clear: §fClears all tickets.");
				cs.sendMessage(sb.toString());
				return true;
			}
			
			if(args.length == 1) {
				
				if(args[0].equalsIgnoreCase("tp")) {
					cs.sendMessage("§aBigBadCraft§f - Please use '/ticket tp <player>'");
				}
				if(args[0].equalsIgnoreCase("cl")) {
					cs.sendMessage("§aBigBadCraft§f - Please use '/ticket cl <player>'");
				}
				if(args[0].equalsIgnoreCase("del")) {
					cs.sendMessage("§aBigBadCraft§f - Please use '/ticket del <player>'");
				}
				if(args[0].equalsIgnoreCase("view")) {
					cs.sendMessage("§aBigBadCraft§f - Please use '/ticket view <player>'");
				}
				
				if(args[0].equalsIgnoreCase("list")) {
	                if(Main.playerTicket.isEmpty() && Main.helpopNames.isEmpty()) {
	                    cs.sendMessage("§aBigBadCraft§f - There are no tickets available.");
	                } 
	                else {
	                    cs.sendMessage("--- §aAvailable Tickets: §f(" + plugin.ticketCounter + ") ---");
	                    for(Entry<String, String> entry : Main.playerTicket.entrySet()) {
	                    	cs.sendMessage("§a" + entry.getKey() + "§f" + entry.getValue());
	                    }
	                    for(Entry<String, String> entry2 : Main.helpopNames.entrySet()) {
	                    	cs.sendMessage("§a" + entry2.getKey() + "§f" + entry2.getValue());
	                    }
	                }
	                return true;
	            }
				
				if (args[0].equalsIgnoreCase("clear") && !Main.playerTicket.isEmpty()) {
					Main.playerTicket.clear();
					Main.helpopNames.clear();
					Main.helpopTickets.clear();
					plugin.ticketCounter = 0;
					cs.sendMessage("§aBigBadCraft§f - All tickets cleared.");
				} 
				else if(args[0].equalsIgnoreCase("clear") && Main.playerTicket.isEmpty()) {
					cs.sendMessage("§aBigBadCraft§f - There is nothing to clear.");
				}
			}
		
			if(args.length == 2) {
				
				Player p;
				
				try {
					p = Bukkit.getPlayer(args[1]);
					
					boolean ifHelpopNames = Main.helpopNames.containsKey(playerName);
					
					//Check to see if playerTicket contains playname
					if(args[0].equalsIgnoreCase("cl") && p.isOnline() && ifHelpopNames) {
						Main.playerTicket.remove(p.getName());
						Main.helpopNames.remove(p.getName());
						Main.helpopTickets.remove(p.getName());
						if(plugin.ticketCounter > 0) {
							plugin.ticketCounter--;
						}
						cs.sendMessage("§a" + p.getName() + "'s §fticket claimed.");
					}
				} catch (NullPointerException ex) {
					p = Bukkit.getPlayer(args[1]);
					if(!Main.playerTicket.containsKey(p.getName())) {
						cs.sendMessage("§4" + "Error: " + args[1] + " is not online or in queue!");
					}
				}
			
				
				try {
					p = Bukkit.getPlayer(args[1]);
					if(args[0].equalsIgnoreCase("tp") && p.isOnline() && Main.playerTicket.containsKey(p.getName())) {
						Main.playerTicket.remove(p.getName());
						if(plugin.ticketCounter > 0) {
							plugin.ticketCounter--;
						}
						Location loc = Bukkit.getPlayer(p.getName()).getLocation();
						player.teleport(loc);
						cs.sendMessage("Teleporting to §a" + p.getName() + "§f...");
					}
				} catch(NullPointerException ex) {
					cs.sendMessage("§4" + "Error: " + args[1] + " is not online or in queue!");
				}
				
				try {
					p = Bukkit.getPlayer(args[1]);
					if(args[0].equalsIgnoreCase("del") && p.isOnline() && Main.playerTicket.containsKey(p.getName())) {
						Main.playerTicket.remove(p.getName());
						if(plugin.ticketCounter > 0) {
							plugin.ticketCounter--;
						}
						cs.sendMessage("§a" + p.getName() + "'s §fticket removed.");
					}
				} catch(NullPointerException ex) {
					cs.sendMessage("§4" + "Error: " + args[1] + " is not online or in queue!");
				}
				
				try {
					p = Bukkit.getPlayer(args[1]);
					if(args[0].equalsIgnoreCase("view") && p.isOnline()) {
						cs.sendMessage("--- §aViewing " + playerName + "'s ticket §f---");
						for(String messages : Main.helpopTickets) {
							cs.sendMessage("§a" + playerName + "§f: " + messages);
						}
						if(Main.helpopTickets.isEmpty()) {
							cs.sendMessage("§aBigBadCraft§f - " + playerName + " has no tickets.");
						}
					} 
				} catch(NullPointerException ex) {
					cs.sendMessage("§4" + "Error: " + args[1] + " is not online or in queue!");
				}
			}
		}
		return true;
	}
}