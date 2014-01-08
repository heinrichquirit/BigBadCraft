package main.java.net.bigbadcraft.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.java.net.bigbadcraft.BigBadCraft;

public class CommandLogger {
	
	private static BigBadCraft plugin;
	
	public CommandLogger(BigBadCraft plugin) {
		CommandLogger.plugin = plugin;
	}
	
	public static void logWarnCommand(String name, String target, String message) {
		// Create a directory warnlog and a file for each player
		
		File dir = new File(plugin.getDataFolder() + "/warnlog");
		
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		File file = new File(plugin.getDataFolder() + "/warnlog/" + name + ".txt");
		
		Utilities.loadFile(file);
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
			writer.append("[" + new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime())
                + "] " + name + " has warned " + target + ". Reason: " + message);
			writer.newLine();
			Utilities.info("Logged /warn command for " + name); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
