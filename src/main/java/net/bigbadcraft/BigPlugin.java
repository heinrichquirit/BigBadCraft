package main.java.net.bigbadcraft;

import java.io.File;
import java.util.List;

import main.java.net.bigbadcraft.buyhead.BuyHeadListener;
import main.java.net.bigbadcraft.elbacon.BedListener;
import main.java.net.bigbadcraft.inventorymenu.IconMenuListener;
import main.java.net.bigbadcraft.miscellaneous.FireworkOnJoinListener;
import main.java.net.bigbadcraft.miscellaneous.VoteListener;
import main.java.net.bigbadcraft.miscellaneous.ZombieReinforcementsListener;
import main.java.net.bigbadcraft.namethatmob.command.NameMobCommand;
import main.java.net.bigbadcraft.namethatmob.listener.EntityInteractListener;
import main.java.net.bigbadcraft.namethatmob.utils.NameManager;
import main.java.net.bigbadcraft.nonmember.BlockChangeListener;
import main.java.net.bigbadcraft.playerrider.command.RideCommand;
import main.java.net.bigbadcraft.playerrider.listener.InteractEntityListener;
import main.java.net.bigbadcraft.preventbaddispense.PreventBadDispense;
import main.java.net.bigbadcraft.preventpoison.PreventPoison;
import main.java.net.bigbadcraft.recipes.Recipes;
import main.java.net.bigbadcraft.silktouchdisabler.SilkTouchFilter;
import main.java.net.bigbadcraft.stafftickets.commands.HelpopCommand;
import main.java.net.bigbadcraft.stafftickets.commands.MyTicketsCommand;
import main.java.net.bigbadcraft.stafftickets.commands.TicketCommand;
import main.java.net.bigbadcraft.stafftickets.listeners.ChatListener;
import main.java.net.bigbadcraft.stafftickets.listeners.CommandListener;
import main.java.net.bigbadcraft.stafftickets.listeners.QuitListener;
import main.java.net.bigbadcraft.stafftickets.tasks.BroadcastTask;
import main.java.net.bigbadcraft.stafftickets.utils.TicketManager;
import main.java.net.bigbadcraft.utils.SQLite;
import main.java.net.bigbadcraft.warns.WarnsCommand;
import main.java.net.bigbadcraft.warns.WarnsManager;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 4:34 AM
 */
public class BigPlugin extends JavaPlugin {

    // Ticket System Variables
    private File config;
    private File ticketLogs;

    // NameThatMob Variables
    public int price;
    public int charCount;
    public List<String> badWords;
    public boolean vaultEnabled;

    // AnimalProtection Variables
    public int protPrice;

    // Certain Plugin Managers
    public TicketManager ticketMang;
    public NameManager nameMang;
    public WarnsManager warnsMang;
    
    // Inventory database
    private SQLite sql;
    private File flatFile;

    // Dependencies
    public static Economy economy = null;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        new Recipes();

        initTicketSystem();
        initNameThatMob();
        initWarns();
        initRide();

        // Registers BuyHeadSignListener
        getServer().getPluginManager().registerEvents(new BuyHeadListener(), this);
        // Registers SilkTouchFilterListener
        getServer().getPluginManager().registerEvents(new SilkTouchFilter(), this);
        // Registers PreventPoisonBySplashListener
        getServer().getPluginManager().registerEvents(new PreventPoison(), this);
        // Registers PreventBadDispenseListener
        getServer().getPluginManager().registerEvents(new PreventBadDispense(), this);
        // Registers BlockChangeListener
        getServer().getPluginManager().registerEvents(new BlockChangeListener(), this);
        // Registers PlayerCommandPreProccessevent
        getServer().getPluginManager().registerEvents(new BedListener(), this);
        // Registers random firework generator on PlayerJoinListener
        getServer().getPluginManager().registerEvents(new FireworkOnJoinListener(), this);
        // Registers ZombieReinforcementsListener
        getServer().getPluginManager().registerEvents(new ZombieReinforcementsListener(), this);
        // Registers IconMenuListener
        getServer().getPluginManager().registerEvents(new IconMenuListener(this), this);
        // Registers VoteListener
        getServer().getPluginManager().registerEvents(new VoteListener(this), this);

        setupEconomy();
        setupInvDatabase();

    }

    @Override
    public void onDisable() {
        // Clear tickets
        ticketMang.clearTickets();
        ticketMang.helpopClear();
        
        // Close database connection
        sql.close();
    }

    private void initTicketSystem() {
        ticketMang = new TicketManager(this);

        config = new File(getDataFolder(), "config.yml");
        ticketLogs = new File(getDataFolder() + "/ticketlogs");
        ticketMang.loadFile(config);
        ticketLogs.mkdir();

        getCommand("helpop").setExecutor(new HelpopCommand(this));
        getCommand("ticket").setExecutor(new TicketCommand(this));
        getCommand("mytickets").setExecutor(new MyTicketsCommand(this));

        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        if (getConfig().getBoolean("reminder.enable")) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, new BroadcastTask(this),
                    3600, 20 * 60 * getConfig().getInt("reminder.interval"));
        }
    }

    private void initNameThatMob() {
        nameMang = new NameManager(this);

        price = getConfig().getInt("currency.currency-cost-per-nametag");
        charCount = getConfig().getInt("namethatmob.character-limit");
        badWords = getConfig().getStringList("namethatmob.blacklisted-words");
        vaultEnabled = getConfig().getBoolean("currency.vault-enable");

        nameMang.missing(this);

        getServer().getPluginManager().registerEvents(new EntityInteractListener(this), this);
        getCommand("namemob").setExecutor(new NameMobCommand(this));
    }

    private void initWarns() {
        warnsMang = new WarnsManager();
        getCommand("warn").setExecutor(new WarnsCommand(this));
    }

    private void initRide() {
        getServer().getPluginManager().registerEvents(new InteractEntityListener(), this);
        getCommand("ride").setExecutor(new RideCommand());
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = (Economy) economyProvider.getProvider();
        }
        return economy != null;
    }
    
    // Set up flatfile database, why not a MySQL one Skepter?
    private void setupInvDatabase() {
    	flatFile = new File(getDataFolder(), "inventories.db");
    	sql = new SQLite(flatFile);
    	sql.open();
    	sql.execute("CREATE TABLE IF NOT EXISTS database(playername VARCHAR(16), inventory VARCHAR(10000), armor VARCHAR(10000), enderchest VARCHAR(10000);");
    }

}
