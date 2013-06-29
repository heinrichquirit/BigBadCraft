
package me.bigbadhenz.bigbadcraft.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.bigbadhenz.bigbadcraft.other.BuildSpectate;
import me.bigbadhenz.bigbadcraft.other.Health;
import me.bigbadhenz.bigbadcraft.other.OtherCommands;
import me.bigbadhenz.bigbadcraft.other.PreventPoisonDamage;
import me.bigbadhenz.bigbadcraft.other.SilkTouchDisabler;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	List<String> trigger = new ArrayList<String>();
	public static HashMap<String, String> playerTicket = new HashMap<String, String>();
	public static HashMap<String, String> helpopNames = new HashMap<String, String>();
	public static List<String> helpopTickets = new ArrayList<String>();
	
	int ticketCounter;
	
	private TicketListener ticketListener = new TicketListener(this);
	private TicketScheduler ticketScheduler = new TicketScheduler(this);
	private Commands commands = new Commands(this, this);
	
	private Health health = new Health();
	private SilkTouchDisabler silktouchDisabler = new SilkTouchDisabler();
	private PreventPoisonDamage preventPoison = new PreventPoisonDamage();
	private OtherCommands otherCommands = new OtherCommands();
	
	public void onEnable() {
		
		PluginManager pm = this.getServer().getPluginManager();
		
		pm.registerEvents(health, this);
		pm.registerEvents(silktouchDisabler, this);
		pm.registerEvents(preventPoison, this);
		pm.registerEvents(ticketListener, this);
		pm.registerEvents(new BuildSpectate(), this);
		
		getCommand("helpop").setExecutor(commands);
		getCommand("ticket").setExecutor(commands);
		
		getCommand("fakeop").setExecutor(otherCommands);
		getCommand("id").setExecutor(otherCommands);
		getCommand("work").setExecutor(otherCommands);
		getCommand("coal").setExecutor(otherCommands);
		getCommand("lapis").setExecutor(otherCommands);
		getCommand("diamond").setExecutor(otherCommands);
		getCommand("redstone").setExecutor(otherCommands);
		
		getConfig().set("trigger", trigger);
		getConfig().getInt("ticket-reminder.enable.delay");
		updateConfig();
		
		if(this.getConfig().getBoolean("reminder.enable") == true) {
			Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, ticketScheduler, 3600,
					getConfig().getInt("reminder.interval"));
		}
	}
	
	public void updateConfig() {
		getConfig();
		saveDefaultConfig();
		reloadConfig();
	}
	
	public void onDisable() {}
	
}