package main.java.net.bigbadcraft.warns;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 6:03 AM
 */
public class WarnsManager {

    private final ChatColor RED = ChatColor.RED;
    private HashMap<String, Integer> warns;

    public WarnsManager() {
        this.warns = new HashMap<String, Integer>();
    }

    public void warnUser(Player sender, Player player, String message) {
        if (isWarned(player)) {
            incrementWarn(player);
            player.sendMessage(this.RED + "You have been warned: (" + getWarns(player) + "/3)");
            player.sendMessage(this.RED + sender.getName() + ": " + message);
            if (getWarns(player) == 3) {
                removeWarn(player);
                kick(player);
                Bukkit.broadcastMessage(RED + player.getName() + " has been kicked for exceeding 3 warns.");
            }
        } else {
            addWarn(player);
            player.sendMessage(this.RED + "You have been warned: (" + getWarns(player) + "/3)");
            player.sendMessage(this.RED + sender.getName() + ": " + message);
        }
    }

    public void kick(Player player) {
        player.kickPlayer(this.RED + "[Warned] You have been warned mate. (3/3)");
    }

    public void addWarn(Player player) {
        this.warns.put(player.getName(), 1);
    }

    public void removeWarn(Player player) {
        this.warns.remove(player.getName());
    }

    public void incrementWarn(Player player) {
        if (isWarned(player)) {
            this.warns.put(player.getName(), getWarns(player) + 1);
        }
    }

    public boolean isWarned(Player player) {
        return this.warns.containsKey(player.getName());
    }

    public int getWarns(Player player) {
        return this.warns.get(player.getName());
    }
}
