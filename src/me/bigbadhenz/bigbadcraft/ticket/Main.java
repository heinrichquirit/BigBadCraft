
package me.bigbadhenz.bigbadcraft.ticket;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import me.bigbadhenz.bigbadcraft.classes.Archer;
import me.bigbadhenz.bigbadcraft.classes.Farmer;
import me.bigbadhenz.bigbadcraft.classes.Miner;
import me.bigbadhenz.bigbadcraft.classes.Warrior;
import me.bigbadhenz.bigbadcraft.other.BuyHead;
import me.bigbadhenz.bigbadcraft.other.HorsesWorld;
import me.bigbadhenz.bigbadcraft.other.ModMailCommands;
import me.bigbadhenz.bigbadcraft.other.OtherCommands;
import me.bigbadhenz.bigbadcraft.other.PreventFireDispense;
import me.bigbadhenz.bigbadcraft.other.PreventPoisonDamage;
import me.bigbadhenz.bigbadcraft.other.SilkTouchDisabler;
import me.bigbadhenz.bigbadcraft.recipes.Recipes;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	
	public static final Logger logger = Logger.getLogger("Minecraft");
	
	public static Economy economy = null;
	public static Chat chat = null;
	
	List<String> trigger = new ArrayList<String>();
	public static HashMap<String, String> playerTicket = new HashMap<String, String>();
	
	int ticketCounter;
	public File file = new File(getDataFolder(), "playerinfo.yml"); 
	public static File fileLog;
	
	private TicketListener ticketListener;
	private TicketScheduler ticketScheduler;
	private Commands commands;
	private OtherCommands otherCommands;
	
	public void onEnable() {
		
		this.invokeInstances();
		
		PluginManager pm = this.getServer().getPluginManager();
		
		pm.registerEvents(new Miner(), this);
		
		// Register classes
		pm.registerEvents(new Archer(), this);
		pm.registerEvents(new Farmer(), this);
		pm.registerEvents(new Miner(), this);
		pm.registerEvents(new Warrior(), this);
		
		pm.registerEvents(new BuyHead(), this);
		pm.registerEvents(new SilkTouchDisabler(), this);
		pm.registerEvents(new PreventPoisonDamage(), this);
		pm.registerEvents(ticketListener, this);
		pm.registerEvents(new HorsesWorld(), this);
		pm.registerEvents(new PreventFireDispense(), this);
		
		getCommand("helpop").setExecutor(commands);
		getCommand("ticket").setExecutor(commands);
		
		getCommand("mod").setExecutor(new ModMailCommands());
		getCommand("fakeop").setExecutor(otherCommands);
		getCommand("id").setExecutor(otherCommands);
		getCommand("coal").setExecutor(otherCommands);
		getCommand("lapis").setExecutor(otherCommands);
		getCommand("diamond").setExecutor(otherCommands);
		getCommand("redstone").setExecutor(otherCommands);
		
		getConfig().set("trigger", trigger);
		getConfig().getInt("ticket-reminder.enable.delay");
		updateConfig();
		
		Main.fileLog = new File(this.getDataFolder(), "ticketlog.txt");
		
		if (!fileLog.exists()) {
			try {
				fileLog.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		if (this.getConfig().getBoolean("reminder.enable")) {
			Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, ticketScheduler, 3600, 
					20 * this.getConfig().getInt("reminder.interval"));
		}
		
		new Recipes();
		setupChat();
		setupEconomy();
		
	}
	
	public void updateConfig() 
	{
		getConfig();
		saveDefaultConfig();
		reloadConfig();
	}
	
	private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }
	
	private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
	
	private void invokeInstances() {
		
		this.ticketListener = new TicketListener(this);
		this.ticketScheduler = new TicketScheduler();
		
		this.commands = new Commands(this);
		this.otherCommands = new OtherCommands();
	}
	
	public void onDisable() {}
	
}