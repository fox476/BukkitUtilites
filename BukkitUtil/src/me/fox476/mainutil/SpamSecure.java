package me.fox476.mainutil;

import org.bukkit.event.Listener;

public class SpamSecure implements Listener {
	public SpamSecure(MainUtil plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	

}