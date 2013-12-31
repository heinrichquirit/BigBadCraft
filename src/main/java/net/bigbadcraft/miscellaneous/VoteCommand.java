package main.java.net.bigbadcraft.miscellaneous;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {

	private final ChatColor G = ChatColor.GREEN;
	private final ChatColor Y = ChatColor.YELLOW;
	private final ChatColor R = ChatColor.RED;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("vote")) {
				if (args.length == 0) {
					player.sendMessage(voteMenu());
				} else {
					player.sendMessage(R + "Incorrect syntax, usage: /vote");
				}
			}
		}
		return true;
	}
	
	private String voteMenu() {
		return G + "---------" + Y + " Vote Links" + G + " ---------\n"
				+ G + "Vote and receive" + ChatColor.AQUA + " 1 " + G + "diamond for each site.\n"
				+ G + "Top50Servers:" + Y + " http://goo.gl/JdOLxC\n"
				+ G + "PlanetMinecraft:" + Y + " http://goo.gl/RtdQ98\n"
				+ G + "MCIndex:" + Y + " http://goo.gl/VfLxVX\n"
				+ G + "MCServerLister:" + Y + " http://goo.gl/QmuhlI\n"
				+ G + "MCServers:" + Y + " http://goo.gl/NF9aDC\n"
				+ G + "ServerLister:" + Y + " http://goo.gl/J4G2Jn\n"
				+ G + "Minestatus:" + Y + " http://goo.gl/7YdHCx\n"
				+ G + "----------------------------";
	}
	
	
}
