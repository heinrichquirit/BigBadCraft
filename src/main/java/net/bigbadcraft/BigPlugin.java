package main.java.net.bigbadcraft;

import main.java.net.bigbadcraft.buyhead.BuyHeadListener;
import main.java.net.bigbadcraft.elbacon.BedListener;
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
import main.java.net.bigbadcraft.warns.WarnsCommand;
import main.java.net.bigbadcraft.warns.WarnsManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

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
        // Registers CommandListener to prevent fagget elbacon's gay bed home
        getServer().getPluginManager().registerEvents(new BedListener(), this);

        setupEconomy();

    }

    @Override
    public void onDisable() {
        // Clear tickets
        ticketMang.clearTickets();
        ticketMang.helpopClear();
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

}
