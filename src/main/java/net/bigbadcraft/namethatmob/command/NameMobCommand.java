package main.java.net.bigbadcraft.namethatmob.command;

import main.java.net.bigbadcraft.BigPlugin;
import main.java.net.bigbadcraft.namethatmob.utils.NameManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * User: Heinrich Quirit
 * Last Modified: 9/30/13
 * Time: 12:41 AM
 */
public class NameMobCommand implements CommandExecutor {

    private final ChatColor GREEN = ChatColor.GREEN;
    private final ChatColor RED = ChatColor.RED;

    private BigPlugin plugin;
    private NameManager nameMang;

    public NameMobCommand(BigPlugin plugin) {
        this.plugin = plugin;
        this.nameMang = plugin.nameMang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmdObj, String lbl, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmdObj.getName().equalsIgnoreCase("namemob")) {
                return namemob(player, strings);
            }
        }
        return true;
    }

    private boolean namemob(Player player, String[] strings) {
        if (strings.length == 0) {
            player.sendMessage(GREEN + "-/namemob price - Retrieves cost of usage.");
            player.sendMessage(GREEN + "-/namemob <name> - Names entity with specified name once clicked on.");
            return true;
        } else if (strings.length >= 1) {
            if (strings[0].equalsIgnoreCase("price")) {
                player.sendMessage(GREEN + "Cost to use: $" + plugin.price);
            } else {
                String message = StringUtils.join(strings, ' ', 0, strings.length);
                message = ChatColor.translateAlternateColorCodes('&', message);
                if (!nameMang.containsSwear(message)) {
                    if (nameMang.underCharLimit(message)) {
                        nameMang.saveName(player, message);
                        player.sendMessage(GREEN+ "Stored '" + message + GREEN + "', now right click your mob.");
                    } else {
                        player.sendMessage(RED + "Your name must be under " + plugin.charCount + " characters.");
                    }
                } else {
                    int count = 1;
                    player.sendMessage(RED + "Your name cannot contain the following:");
                    for (String words : plugin.badWords) {
                        player.sendMessage(RED + "" + count++ + ". " + words);
                    }
                }
            }
        }
        return true;
    }
}
