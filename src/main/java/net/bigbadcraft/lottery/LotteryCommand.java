package main.java.net.bigbadcraft.lottery;

import main.java.net.bigbadcraft.BigBadCraft;
import main.java.net.bigbadcraft.lottery.tasks.BroadcastTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LotteryCommand implements CommandExecutor {

	private final ChatColor G = ChatColor.GREEN;
	private final ChatColor Y = ChatColor.YELLOW;
	private final ChatColor R = ChatColor.RED;
	
	private BigBadCraft plugin;
	private LotteryManager lm;
	
	public LotteryCommand(BigBadCraft plugin) {
		this.plugin = plugin;
		lm = this.plugin.lotteryMang;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("lottery")) {
				proccessCommand(player, args);
			}
		}
		return true;
	}
	
	private void proccessCommand(Player player, String[] args) {
		if (args.length == 0) {
			player.sendMessage(!player.hasPermission("*") ? helpMenu() : adminHelpMenu());
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("buy")) {
				/*
				 * Prevent the player from buying more than 20 tickets
				 */
				if (lm.getTicket(player) >= 20) {
					player.sendMessage(R + "You can only buy " + Y + "20" + G + " tickets each draw.");
					return;
				}
				
				/*
				 * Start the broadcast once a ticket has been purchased
				 */
				if (lm.getMap().size() >= 1) { 
					if (!lm.broadcastRunning) {
						Bukkit.getScheduler().runTaskTimer(plugin, new BroadcastTask(plugin), 20, 20 * 60 * 5);
					}
				}
				
				lm.buyTicket(player);
				player.sendMessage(G + "The current jackpot is: " + Y + "$" + lm.getPot());
				player.sendMessage(G + "You have purchased " + Y + "1" + G + " ticket." 
						+ (lm.getTicket(player) > 0 ? " Tickets in draw: " + Y + lm.getTicket(player) : ""));
				/*
				 * Extend time if ticket is bought with 10 minutes near draw time
				 */
			} else if (args[0].equalsIgnoreCase("info")) {
				player.sendMessage(G + "Current pot: " + Y + "$" + lm.getPot() + G + " | Ticket cost: " + Y + "$100");
				// You have purchased 4 tickets in this draw or NONE?
				// Current pot: $1000 | Ticket cost: $100
				// Draw time: 9 hours, 36 miuntes, 10 seconds
				// Last draw pot: $1000
				// Previous Winners: Henz ($100), Ryan ($50), Logan ($10)
			} else if (args[0].equalsIgnoreCase("forcedraw")) {
				// Admin Command
				if (player.hasPermission("*")) {
					
				}
			}
		}
	}
	
	private String helpMenu() {
		return G + "---------- (" + Y + "Lottery Commands" + G + ") ----------\n"
				+ G + "Shorcut: " + Y + "/l\n"
				+ G + "-/lottery buy" + Y + " - Buy one lottery ticket.\n"
				+ G + "-/lottery buy <#tickets>" + Y + " - Buy specified number of tickets.\n"
				+ G + "-/lottery info" + Y + " - Information of current draw.\n"
				+ G + "----------------------------------------";
 	}
	
	private String adminHelpMenu() {
		return G + "---------- (" + Y + "Lottery Commands(Admin)" + G + ") ----------\n"
				+ G + "Shorcut: " + Y + "/l\n"
				+ G + "-/lottery buy" + Y + " - Buy one lottery ticket.\n"
				+ G + "-/lottery buy <#tickets>" + Y + " - Buy specified number of tickets.\n"
				+ G + "-/lottery info" + Y + " - Information of current draw.\n"
				+ G + "-/lottery takefrompot <percentage>" + Y + " - Take away % from pot. (Admin)\n"
				+ G + "-/lottery addtopot <amount>" + Y + " - Add specified amount to pot. (Admin)\n" 
				+ G + "-/lottery forcedraw" + Y + " - Force the draw to happen. (Admin)\n"
				+ G + "----------------------------------------";
	}

}
