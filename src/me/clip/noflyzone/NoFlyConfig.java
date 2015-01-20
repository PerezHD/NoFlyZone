package me.clip.noflyzone;

import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class NoFlyConfig {

	private NoFlyZone plugin;
	
	protected NoFlyConfig(NoFlyZone i) {
		plugin = i;
	}
	
	public void loadDefaultConfiguration() {
		FileConfiguration config = plugin.getConfig();
		config.options().header("NoFlyZone version: "+plugin.getDescription().getVersion()+" Main Configuration"+
				"\n" +
				"\nList regions below that will disable players from flying when they enter them" +
				"\n");
		config.addDefault("no_fly_regions", Arrays.asList(new String[] {
				"testflyregion"
		}));
		config.addDefault("no_fly_message", "&cNo flying here!");
		config.options().copyDefaults(true);
		plugin.saveConfig();
		plugin.reloadConfig();
	}
	
	protected List<String> noFlyRegions() {
		return plugin.getConfig().getStringList("no_fly_regions");
	}
	
	protected String noFlyMessage() {
		return plugin.getConfig().getString("no_fly_message");
	}
	
}
