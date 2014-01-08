package main.java.net.bigbadcraft.miscellaneous;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.java.net.bigbadcraft.BigBadCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TemporaryHomes implements CommandExecutor{
	
	private final List<String> lines = new ArrayList<String>();
	
	private BigBadCraft plugin;
	
	public TemporaryHomes(BigBadCraft plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args){
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			if (cmd.getName().equalsIgnoreCase("bhome")){
				
				if (plugin.voteHomesList.contains(player.getName())){
					
				}
				
				// Should get rid of bed homes
				File temp_homes = new File(this.plugin.getDataFolder() + "/temphomes/" + player.getName() + ".txt");
				if (args.length == 0){
					
					if (temp_homes.exists()) {
						
						World world = Bukkit.getWorld(getValue(temp_homes, 1));
						
						int x = Integer.parseInt(getValue(temp_homes, 2));
						int y = Integer.parseInt(getValue(temp_homes, 3));
						int z = Integer.parseInt(getValue(temp_homes, 4));
						
						float yaw = Float.parseFloat(getValue(temp_homes, 5));
						float pitch = Float.parseFloat(getValue(temp_homes, 6));
						
						player.sendMessage(ChatColor.GREEN + "Teleporting to vote home..");
						player.teleport(new Location(world, x, y, z, yaw, pitch));
						
						lines.clear();
						
					} else{
						player.sendMessage(ChatColor.RED + "You have not set your vote home. Use /bhome set");
					}
					
				} else if (args.length == 1){
					if (args[0].equalsIgnoreCase("set")){
						
						// Create directory
						File temp_dir = new File(plugin.getDataFolder() + "/temphomes");
						
						if (!temp_dir.exists()){
							temp_dir.mkdir();
						}
						
						if (!temp_homes.exists()){
							saveHome(player, temp_homes);
						} else{
							
							try(PrintWriter writer = new PrintWriter(temp_homes)){
								
								@SuppressWarnings("resource")
								Scanner in = new Scanner(temp_homes);
								
								@SuppressWarnings("unused")
								int lineCounter = 0;
								
								// Reset the file
								while (in.hasNextLine()){
									lineCounter++;
									in.nextLine();
									writer.write("");
								}
								
								saveHome(player, temp_homes);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	private String getValue(File file, int index){
		try{
			@SuppressWarnings("resource")
			Scanner in = new Scanner(file);
			
			while (in.hasNextLine()){
				lines.add(in.nextLine());
			}
			
			return lines.get(index);
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	private void saveHome(Player player, File file){
		
		try(BufferedWriter out = new BufferedWriter(new FileWriter(file, true))){
			Location loc = player.getLocation();
			out.append(player.getName());
			out.newLine();
			out.append(loc.getWorld().getName());
			out.newLine();
			out.append(""+loc.getBlockX());
			out.newLine();
			out.append(""+loc.getBlockY());
			out.newLine();
			out.append(""+loc.getBlockZ());
			out.newLine();
			out.append(""+loc.getYaw());
			out.newLine();
			out.append(""+loc.getPitch());
			out.newLine();
			player.sendMessage(ChatColor.GREEN + "Successfully saved vote home. Use /bhome");
		} catch (IOException e){
			player.sendMessage(ChatColor.RED + "Could not save vote home, report to Administrators.");
			e.printStackTrace();
		}
		
	}
	
}
