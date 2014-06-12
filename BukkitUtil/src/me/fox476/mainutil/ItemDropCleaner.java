package me.fox476.mainutil;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemDropCleaner implements Listener {

	public ItemDropCleaner(MainUtil plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void check(Entity E){
		 
		 }
		
	}