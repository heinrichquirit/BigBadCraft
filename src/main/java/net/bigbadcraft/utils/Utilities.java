package main.java.net.bigbadcraft.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;

public class Utilities {
	
	public static void loadFile(File file) {
		if (!file.exists()) {
			try {
				warning(file.getName() + " doesn't exist, creating..");
				file.createNewFile();
				info(file.getName() + " successfully created.");
			} catch (IOException ioe) {
				severe(file.getName() + " could not be created.");
				ioe.printStackTrace();
			}
		}
	}
	
	public static void info(String message) {
		Bukkit.getLogger().info(message);
	}
	
	public static void warning(String message) {
		Bukkit.getLogger().warning(message);
	}
	
	public static void severe(String message) {
		Bukkit.getLogger().severe(message);
	}
}
