package me.clip.noflyzone;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoFlyCommands implements CommandExecutor {
	
	NoFlyZone plugin;
	
	public NoFlyCommands(NoFlyZone i) {
		plugin = i;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String label,
			String[] args) {

		if (s instanceof Player) {
			Player p = (Player) s;
			
			if (!p.hasPermission("noflyzone.admin")) {
				
				plugin.sms(p, "&cYou don't have permission to do that!");
				return true;
			}
		}
		
		if (args.length == 0) {
			
			plugin.sms(s, "&8&m+----------------+");
			plugin.sms(s, "&cNo&fFly&cZone &f&o"+plugin.getDescription().getVersion());
			plugin.sms(s, "&7Created by: &f&oextended_clip");
			plugin.sms(s, "&8&m+----------------+");
			
		} else if (args.length != 0 && args[0].equalsIgnoreCase("help")) {
			
			plugin.sms(s, "&8&m+----------------+");
			plugin.sms(s, "&cNo&fFly&cZone &f&oHelp");
			plugin.sms(s, "&7/noflyzone - &fshow plugin version");
			plugin.sms(s, "&7/noflyzone reload - &freload config");
			plugin.sms(s, "&7/noflyzone list - &flist no fly regions");
			plugin.sms(s, "&7/noflyzone add <region> - &fadd a no fly region");
			plugin.sms(s, "&7/noflyzone remove <region> - &fremove a no fly region");
			plugin.sms(s, "&8&m+----------------+");
			return true;
			
		} else if (args.length != 0 && args[0].equalsIgnoreCase("reload")) {
			
			plugin.reloadConfig();
			plugin.saveConfig();
			NoFlyZone.message = plugin.cfg.noFlyMessage();
			NoFlyZone.regions = plugin.cfg.noFlyRegions();
			plugin.sms(s, "&8&m+----------------+");
			plugin.sms(s, "&7Configuration successfully reloaded!");
			plugin.sms(s, "&8&m+----------------+");
			return true;
			
		}  else if (args.length != 0 && args[0].equalsIgnoreCase("list")) {
			
			if (NoFlyZone.regions != null && !NoFlyZone.regions.isEmpty()) {
				
				plugin.sms(s, "&8&m+----------------+");
				plugin.sms(s, "&7No fly regions: &f"+NoFlyZone.regions.size());
				plugin.sms(s, NoFlyZone.regions.toString().replace("[", "").replace("]", "").replace(",", "&c,&f"));
				plugin.sms(s, "&8&m+----------------+");
				
			} else {
				
				plugin.sms(s, "&8&m+----------------+");
				plugin.sms(s, "&cNo regions loaded!");
				plugin.sms(s, "&8&m+----------------+");
				
			}
			return true;
			
		}  else if (args.length != 0 && args[0].equalsIgnoreCase("add")) {
			
			if (args.length != 2) {
				
				plugin.sms(s, "&cIncorrect usage! &7/nfz add <region>");
				return true;
			}
			
			String regionToAdd = args[1];
			
			if (NoFlyZone.regions == null) {
				
				NoFlyZone.regions = new ArrayList<String>();
			}
			
			for (String region : NoFlyZone.regions) {
				
				if (region.equalsIgnoreCase(regionToAdd)) {
					
					plugin.sms(s, regionToAdd+" &cis already a NoFlyZone!");
					return true;
				}
			}

			NoFlyZone.regions.add(regionToAdd);
			plugin.getConfig().set("no_fly_regions", NoFlyZone.regions);
			plugin.saveConfig();
			plugin.sms(s, "&8&m+----------------+");
			plugin.sms(s, regionToAdd+" &ahas been marked as a NoFlyZone!");
			plugin.sms(s, "&8&m+----------------+");
			return true;
			
		}  else if (args.length != 0 && args[0].equalsIgnoreCase("remove")) {
			
			if (args.length != 2) {
				
				plugin.sms(s, "&cIncorrect usage! &7/nfz remove <region>");
				return true;
			}
			
			String regionToRemove = args[1];
			
			if (NoFlyZone.regions == null || NoFlyZone.regions.isEmpty()) {
				plugin.sms(s, "&8&m+----------------+");
				plugin.sms(s, "&cNo regions loaded to remove!");
				plugin.sms(s, "&8&m+----------------+");
				return true;
			}
			
			boolean contains = false;
			
			for (String region : NoFlyZone.regions) {
				
				if (region.equalsIgnoreCase(regionToRemove)) {
					regionToRemove = region;
					contains = true;
					break;
				}
			}
			
			if (!contains) {
				plugin.sms(s, "&8&m+----------------+");
				plugin.sms(s, "&cNo region loaded by the name of &f"+regionToRemove);
				plugin.sms(s, "&8&m+----------------+");
				return true;
			}
			
			NoFlyZone.regions.remove(regionToRemove);
			plugin.getConfig().set("no_fly_regions", NoFlyZone.regions);
			plugin.saveConfig();
			plugin.sms(s, "&8&m+----------------+");
			plugin.sms(s, regionToRemove+" &ais no longer a NoFlyZone");
			plugin.sms(s, "&8&m+----------------+");
			return true;
			
		} else {
			
			plugin.sms(s, "&cIncorrect usage! Use /noflyzone help!");
		}
		return true;
	}
	


}
