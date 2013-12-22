package main.java.net.bigbadcraft.miscellaneous;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class TitleCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (s instanceof Player) {
			Player p = (Player) s;
			if (c.getName().equalsIgnoreCase("title")) {
				if (p.hasPermission("bigbadcraft.title")) {
					if (args.length == 0) {
						p.sendMessage("Incorrect syntax, usage: /title <title_name>");
						return true;
					} else if (args.length == 1) {
						
						File essentials = Bukkit.getPluginManager().getPlugin("GroupManager").getDataFolder();
						// Have it set for all worlds
						// Use a loop ?
						File player = new File(essentials, "/worlds/bigbadcraft/users.yml");
						FileConfiguration player_config = YamlConfiguration.loadConfiguration(player);
						
						String title = ChatColor.translateAlternateColorCodes('&', args[0]);
						int strippedLength = ChatColor.stripColor(title).length();
						
						if (strippedLength <= 8) {
							player_config.set("users." + p.getName() + ".info.prefix", title);
						}
						
					}
				}
			}
		}
		return true;
	}
}
