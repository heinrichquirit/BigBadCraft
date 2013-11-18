package main.java.net.bigbadcraft.buyhead;

import main.java.net.bigbadcraft.BigPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 5:09 AM
 */
public class BuyHeadListener implements Listener {

    private final ChatColor GREEN = ChatColor.GREEN;
    private final ChatColor RED = ChatColor.RED;
    private final ChatColor DARKBLUE = ChatColor.DARK_BLUE;

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(0).equalsIgnoreCase(DARKBLUE + "[BuyHead]")) {
                    int price = Integer.parseInt(sign.getLine(1));
                    int balance = (int) Math.round(BigPlugin.economy.getBalance(player.getName()));
                    if (balance >= price) {
                        player.getInventory().addItem(playerSkull(player.getName()));
                        player.updateInventory();
                        BigPlugin.economy.withdrawPlayer(player.getName(), price);
                        player.sendMessage(GREEN + "$" + price + " has been taken from your account.");
                    } else {
                        player.sendMessage(RED + "You do not have enough money!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCreate(SignChangeEvent event) {
        final Player player = event.getPlayer();
        if (event.getLine(0).equalsIgnoreCase("[BuyHead]")) {
            if (player.isOp()) {
                event.setLine(0, DARKBLUE + "[BuyHead]");
                player.sendMessage(GREEN + "Successfully created a BuyHead sign.");
            } else {
                event.setCancelled(true);
                event.getBlock().breakNaturally();
                player.sendMessage(RED + "You cannot make a BuyHead sign.");
            }
        }
    }

    private ItemStack playerSkull(String name) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(name);
        skull.setItemMeta(meta);
        return skull;
    }
}
