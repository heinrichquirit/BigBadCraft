package me.bigbadhenz.bigbadcraft.other;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModMailCommands implements CommandExecutor{
	
	public HashMap<String, String> msg = new HashMap<String, String>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage("Please use this command in game");
		}
		
		if (cmd.getName().equalsIgnoreCase("mod")){
			return mail(sender, args);
		}
		return true;
	}
	
	private boolean mail(CommandSender sender, String[] args){
		if (args.length == 0){
			StringBuilder sb = new StringBuilder();
			sb.append("§3----------------- [§bMod Commands§3] -----------------\n")
			.append("§b- /mod mail <msg>     §3Leaves a mail for the staff to read\n")
			.append("§b- /mod view            §3Reads mail that the staff has left\n")
			.append("§3------------------------------------------------");
			sender.sendMessage(sb.toString());
		}
		
		if (args.length == 1){
			if (args[0].equalsIgnoreCase("view")){
				if (!(msg.isEmpty())){
					sender.sendMessage("§3-------- [§bModMail§3] --------");
					for (Entry<String, String> entry : msg.entrySet()){
						sender.sendMessage("§3" + entry.getKey() + ": §b" + entry.getValue());
					}
					sender.sendMessage("§3-------------------------");
				}else{
					sender.sendMessage("§3There are mod mails left to read.");
				}
			}
			else if(args[0].equalsIgnoreCase("mail")){
				sender.sendMessage("§cIncorrect syntax, use: /mod to list commands.");
			}
		}
		
		if (args.length > 1){
			if (args[0].equalsIgnoreCase("mail")){
				String message = StringUtils.join(args, ' ', 1, args.length);
				msg.put(sender.getName(), message);
				sender.sendMessage("§aSuccessfully saved mail.");
			}
		}
		return true;
	}
}