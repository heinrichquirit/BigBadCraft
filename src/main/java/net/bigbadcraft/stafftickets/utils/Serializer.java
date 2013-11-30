package main.java.net.bigbadcraft.stafftickets.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Serializer {
	
	/*
	 * Turns inventory into a string for storage in database
	 * @param	invInventory The player's inventory
	 * @return	The output string
	 */
	@SuppressWarnings("deprecation")
	public static String serialize(Inventory invInventory) {
		String serialization = invInventory.getSize() + ";";
		for (int i = 0; i < invInventory.getSize(); i++) {
			ItemStack is = invInventory.getItem(i);
			if (is != null) {
				String serializedItemStack = new String();

				String isType = String.valueOf(is.getType().getId());
				serializedItemStack += "t@" + isType;

				if (is.getDurability() != 0) {
					String isDurability = String.valueOf(is.getDurability());
					serializedItemStack += ":d@" + isDurability;
				}

				if (is.getAmount() != 1) {
					String isAmount = String.valueOf(is.getAmount());
					serializedItemStack += ":a@" + isAmount;
				}

				if (is.getItemMeta().getDisplayName() != null) {
					String isName = is.getItemMeta().getDisplayName();
					serializedItemStack += ":n@" + isName;
				}
				
				if (is.getItemMeta().getLore() != null) {
					List<String> lores = is.getItemMeta().getLore();
					for (int j = 0; j < lores.size(); j++) {
						serializedItemStack += ":l@" + j + "@" + lores.get(j);
					}
				}

				Map<Enchantment, Integer> isEnch = is.getEnchantments();
				if (isEnch.size() > 0) {
					for (Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
						serializedItemStack += ":e@" + ench.getKey().getId() + "@" + ench.getValue();
					}
				}

				serialization += i + "#" + serializedItemStack + ";";
			}
		}
		return serialization;
	}
	   
	/*
	 * Turns string into inventory
	 * @param	invString The input string
	 * @return 	The output inventory
	 */
	@SuppressWarnings("deprecation")
	public static Inventory deserialize(String invString) {
		String[] serializedBlocks = invString.split(";");
		String invInfo = serializedBlocks[0];
		Inventory deserializedInventory = Bukkit.getServer().createInventory(
				null, Integer.valueOf(invInfo));

		for (int i = 1; i < serializedBlocks.length; i++) {
			String[] serializedBlock = serializedBlocks[i].split("#");
			int stackPosition = Integer.valueOf(serializedBlock[0]);

			if (stackPosition >= deserializedInventory.getSize()) {
				continue;
			}

			ItemStack is = null;
			Boolean createdItemStack = false;

			String[] serializedItemStack = serializedBlock[1].split(":");
			List<String> lores = new ArrayList<String>();
			for (String itemInfo : serializedItemStack) {
				String[] itemAttribute = itemInfo.split("@");
				if (itemAttribute[0].equals("t")) {
					is = new ItemStack(Material.getMaterial(Integer
							.valueOf(itemAttribute[1])));
					createdItemStack = true;
				} else if (itemAttribute[0].equals("d") && createdItemStack) {
					is.setDurability(Short.valueOf(itemAttribute[1]));
				} else if (itemAttribute[0].equals("a") && createdItemStack) {
					is.setAmount(Integer.valueOf(itemAttribute[1]));
				} else if (itemAttribute[0].equals("n") && createdItemStack) {
					is.getItemMeta().setDisplayName(itemAttribute[1]);
				} else if (itemAttribute[0].equals("l") && createdItemStack) {
					lores.add(Integer.valueOf(itemAttribute[1]), itemAttribute[2]);
					is.getItemMeta().setLore(lores);
				} else if (itemAttribute[0].equals("e") && createdItemStack) {
					is.addEnchantment(Enchantment.getById(Integer
							.valueOf(itemAttribute[1])), Integer
							.valueOf(itemAttribute[2]));
				}
			}
			deserializedInventory.setItem(stackPosition, is);
		}

		return deserializedInventory;
	}
	
	@SuppressWarnings("deprecation")
	public static String serializeArmor(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
		ItemStack[] invInventory = {helmet, chestplate, leggings, boots};
		String serialization = invInventory.length + ";";
		for (int i = 0; i < invInventory.length; i++) {
			ItemStack is = invInventory[i];
			if (is != null) {
				String serializedItemStack = new String();

				String isType = String.valueOf(is.getType().getId());
				serializedItemStack += "t@" + isType;

				if (is.getDurability() != 0) {
					String isDurability = String.valueOf(is.getDurability());
					serializedItemStack += ":d@" + isDurability;
				}

				if (is.getAmount() != 1) {
					String isAmount = String.valueOf(is.getAmount());
					serializedItemStack += ":a@" + isAmount;
				}

				if (is.getItemMeta().getDisplayName() != null) {
					String isName = is.getItemMeta().getDisplayName();
					serializedItemStack += ":n@" + isName;
				}
				
				if (is.getItemMeta().getLore() != null) {
					List<String> lores = is.getItemMeta().getLore();
					for (int j = 0; j < lores.size(); j++) {
						serializedItemStack += ":l@" + j + "@" + lores.get(j);
					}
				}

				Map<Enchantment, Integer> isEnch = is.getEnchantments();
				if (isEnch.size() > 0) {
					for (Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
						serializedItemStack += ":e@" + ench.getKey().getId() + "@" + ench.getValue();
					}
				}

				serialization += i + "#" + serializedItemStack + ";";
			}
		}
		return serialization;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack[] deserializeArmor(String invString) {
		String[] serializedBlocks = invString.split(";");
		List<ItemStack> isList = new ArrayList<ItemStack>();
		for (int i = 1; i < serializedBlocks.length; i++) {
			String[] serializedBlock = serializedBlocks[i].split("#");

			ItemStack is = null;
			Boolean createdItemStack = false;

			String[] serializedItemStack = serializedBlock[1].split(":");
			List<String> lores = new ArrayList<String>();
			for (String itemInfo : serializedItemStack) {
				String[] itemAttribute = itemInfo.split("@");
				if (itemAttribute[0].equals("t")) {
					is = new ItemStack(Material.getMaterial(Integer
							.valueOf(itemAttribute[1])));
					createdItemStack = true;
				} else if (itemAttribute[0].equals("d") && createdItemStack) {
					is.setDurability(Short.valueOf(itemAttribute[1]));
				} else if (itemAttribute[0].equals("a") && createdItemStack) {
					is.setAmount(Integer.valueOf(itemAttribute[1]));
				} else if (itemAttribute[0].equals("n") && createdItemStack) {
					is.getItemMeta().setDisplayName(itemAttribute[1]);
				} else if (itemAttribute[0].equals("l") && createdItemStack) {
					lores.add(Integer.valueOf(itemAttribute[1]), itemAttribute[2]);
					is.getItemMeta().setLore(lores);
				} else if (itemAttribute[0].equals("e") && createdItemStack) {
					is.addEnchantment(Enchantment.getById(Integer
							.valueOf(itemAttribute[1])), Integer
							.valueOf(itemAttribute[2]));
				}
			}
			isList.add(is);
		}

		return (ItemStack[]) isList.toArray();
	}
}
