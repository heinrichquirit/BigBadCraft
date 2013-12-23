package main.java.net.bigbadcraft.namethatmob.listener;

import main.java.net.bigbadcraft.BigBadCraft;
import main.java.net.bigbadcraft.namethatmob.utils.NameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * User: Heinrich Quirit
 * Last Modified: 9/30/13
 * Time: 12:36 AM
 */
public class EntityInteractListener implements Listener {

    private BigBadCraft plugin;
    private NameManager nameMang;

    public EntityInteractListener(BigBadCraft plugin) {
        this.plugin = plugin;
        this.nameMang = plugin.nameMang;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        if (nameMang.isAnimOrMons(event) && nameMang.contains(player)) {
            LivingEntity entity = (LivingEntity) event.getRightClicked();
            if (nameMang.isNameNull(entity)) entity.setCustomName(" ");
            if (!entity.getCustomName().equals(nameMang.getMobName(player))) {
                if (!nameMang.isPoor(player)) {
                    entity.setCustomName(nameMang.getMobName(player));
                    entity.setCustomNameVisible(true);
                    nameMang.withdraw(player);
                    nameMang.remove(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You need $" + Math.round(plugin.price) + " to use this.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "That mob is already named: " + nameMang.getMobName(player) + ".");
            }
        }
    }
}
