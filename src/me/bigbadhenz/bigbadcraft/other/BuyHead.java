package me.bigbadhenz.bigbadcraft.other;

import me.bigbadhenz.bigbadcraft.ticket.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BuyHead implements Listener {

	@EventHandler
	public void onEvent(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
		
			if (event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN) {
				
				Sign sign = (Sign) event.getClickedBlock().getState();
				
				if (sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_BLUE + "[BuyHead]")) {
					
					int price = Integer.parseInt(sign.getLine(1));
					
					if (!(Main.economy.getBalance(player.getName()) >= price)) {
						
						player.sendMessage(ChatColor.RED + "You do not have enough money!");
						
					} else {
						Main.economy.withdrawPlayer(player.getName(), price);
						player.sendMessage(ChatColor.GREEN + "$" + price + " has been taken from your account.");
						
						ItemStack skullitem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				        SkullMeta skullmeta = (SkullMeta)skullitem.getItemMeta();
				        skullmeta.setOwner(player.getName());
				        skullitem.setItemMeta(skullmeta);
				        
						player.getInventory().addItem(skullitem);
						player.updateInventory();
					}
				}
			}
			
		}
	}
	
	@EventHandler
	public void onCreate(SignChangeEvent event) {
		if (event.getPlayer().isOp()) {
			if (event.getLine(0).equalsIgnoreCase("[BuyHead]")) {
				event.setLine(0, ChatColor.DARK_BLUE + "[BuyHead]");
			}
		}
	}

	
}
