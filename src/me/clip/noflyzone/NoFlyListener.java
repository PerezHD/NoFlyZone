package me.clip.noflyzone;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoFlyListener implements Listener {
	
	NoFlyZone plugin;
	
	public NoFlyListener(NoFlyZone i) {
		plugin = i;
		Bukkit.getPluginManager().registerEvents(this, i);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		if (NoFlyZone.regions == null || NoFlyZone.regions.isEmpty()) {
			return;
		}
		
		if ((e.getFrom().getBlockX() != e.getTo().getBlockX()) || 
		    (e.getFrom().getBlockY() != e.getTo().getBlockY()) || 
		    (e.getFrom().getBlockZ() != e.getTo().getBlockZ())) {
			
			if (!e.getPlayer().isFlying()) {
				return;
			}
			
			if (NoFlyZone.getRegion(e.getTo()) == null) {
				return;
			}
			
			
			if (NoFlyZone.regions.contains(NoFlyZone.getRegion(e.getTo()))) {
				
				if (e.getPlayer().hasPermission("noflyzone.bypass")) {
					return;
				}
				
				e.getPlayer().setFlying(false);
				
				if (NoFlyZone.message != null && !NoFlyZone.message.isEmpty()) {
					plugin.sms(e.getPlayer(), NoFlyZone.message);
				}
				
			}
			
		}
		
	}

}
