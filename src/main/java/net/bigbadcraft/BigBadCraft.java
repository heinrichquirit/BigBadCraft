package main.java.net.bigbadcraft;

import java.io.File;
import java.util.List;

import main.java.net.bigbadcraft.buyhead.BuyHeadListener;
import main.java.net.bigbadcraft.elbacon.BedListener;
import main.java.net.bigbadcraft.lottery.LotteryCommand;
import main.java.net.bigbadcraft.lottery.LotteryManager;
import main.java.net.bigbadcraft.miscellaneous.BannedCommandsListener;
import main.java.net.bigbadcraft.miscellaneous.FireworkOnJoinListener;
import main.java.net.bigbadcraft.miscellaneous.ItemIDCommand;
import main.java.net.bigbadcraft.miscellaneous.PayOfflineCommand;
import main.java.net.bigbadcraft.miscellaneous.VoteCommand;
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
import main.java.net.bigbadcraft.utils.CommandLogger;
import main.java.net.bigbadcraft.utils.Utilities;
import main.java.net.bigbadcraft.warns.WarnsCommand;
import main.java.net.bigbadcraft.warns.WarnsManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * User: Heinrich Quirit
 * Last Modified: 10/2/13
 * Time: 4:34 AM
 */
public class BigBadCraft extends JavaPlugin {

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
    
    // BanCommands variables
    public File playerCmds;
    public File groupCmds;
    
    public FileConfiguration playerConf;
    public FileConfiguration groupConf;
    
    // Lottery variables
    public LotteryManager lotteryMang;

    // Dependencies
    public static Economy economy = null;
    public static Permission permission = null;
    
    // Temporary Homes global variable
    public File voteHomes;
    public List<String> voteHomesList;
    
    @Override
    public void onEnable() {

        saveDefaultConfig();

        new Recipes();
        new Utilities();
        new CommandLogger(this);

        initTicketSystem();
        initNameThatMob();
        initWarns();
        initRide();
        initLottery();
        //initVoteHomes();
        
        PluginManager pm = Bukkit.getPluginManager();
        registerListeners(pm);
        registerCommands();

        setupEconomy();
        setupPermissions();
    }

    @Override
    public void onDisable() {
        // Clear tickets
        ticketMang.clearTickets();
        ticketMang.helpopClear();
    }
    
    private void registerListeners(PluginManager pm) {
    	// Registers BuyHeadSignListener
        pm.registerEvents(new BuyHeadListener(), this);
        // Registers SilkTouchFilterListener
        pm.registerEvents(new SilkTouchFilter(), this);
        // Registers PreventPoisonBySplashListener
        pm.registerEvents(new PreventPoison(), this);
        // Registers PreventBadDispenseListener
        pm.registerEvents(new PreventBadDispense(), this);
        // Registers BlockChangeListener
        pm.registerEvents(new BlockChangeListener(), this);
        // Registers PlayerCommandPreProccessevent
        pm.registerEvents(new BedListener(), this);
        // Registers random firework generator on PlayerJoinListener
        pm.registerEvents(new FireworkOnJoinListener(), this);
        // Registers ZombieReinforcementsListener
        pm.registerEvents(new ZombieReinforcementsListener(), this);
        // Registers VoteListener
        pm.registerEvents(new VoteListener(this), this);
        // Registers BannedCommmandsListener
        pm.registerEvents(new BannedCommandsListener(), this);
    }
    
    private void registerCommands() {
    	getCommand("opay").setExecutor(new PayOfflineCommand());
    	getCommand("id").setExecutor(new ItemIDCommand());
    	getCommand("vote").setExecutor(new VoteCommand());
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
    
    private void initLottery() {
    	lotteryMang = new LotteryManager();
    	getCommand("lottery").setExecutor(new LotteryCommand(this));
    }
    
    /*
    private void initVoteHomes() {
    	
    	voteHomesList = new ArrayList<String>();
    	
    	voteHomes = new File(getDataFolder() + "/votehomes.txt");
        Utilities.loadFile(voteHomes);
        
        // Load array list
        
        try {
        	@SuppressWarnings("resource")
			Scanner in = new Scanner(voteHomes);
        	
        	while (in.hasNextLine()) {
        		voteHomesList.add(in.nextLine());
        	}
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
         
        getCommand("bhome").setExecutor(new TemporaryHomes(this));
    }
    */
    
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
    
    private boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
    
}
