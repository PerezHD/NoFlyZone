package me.clip.noflyzone;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class NoFlyZone extends JavaPlugin {

	protected NoFlyConfig cfg;
	
	protected static WorldGuardPlugin worldguard = null;
	
	protected static List<String> regions = null;
	
	protected static String message = null;
	
	@Override
	public void onEnable() {
		
		if (hookWorldGuard()) {
			getLogger().info("Hooked into WorldGuard "+worldguard.getDescription().getVersion());
			
			cfg = new NoFlyConfig(this);
			cfg.loadDefaultConfiguration();
			
			regions = cfg.noFlyRegions();
			message = cfg.noFlyMessage();
			
			new NoFlyListener(this);
			
			getCommand("noflyzone").setExecutor(new NoFlyCommands(this));
			
		} else {
			getLogger().warning("Could not hook into WorldGuard! Disabling NoFlyZone!");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {
		regions = null;
		message = null;
		worldguard = null;
	}
	
	public boolean hookWorldGuard() {
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			worldguard = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		}
		return worldguard != null;
	}
	
	public static String getRegion(Location l) {
		
		if (worldguard == null) {
			return null;
		}
		
		ApplicableRegionSet regions = worldguard.getRegionManager(l.getWorld()).getApplicableRegions(l);
		
		if (regions == null) {
			return null;
		}
		
		Iterator<ProtectedRegion> iter = regions.iterator();
			
		ProtectedRegion reg = null;
		
		while (iter.hasNext()) {
			
			reg = iter.next();
			return reg.getId();
			
		}
		
		return null;
	}
	
	public void sms(CommandSender p, String msg) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
}
