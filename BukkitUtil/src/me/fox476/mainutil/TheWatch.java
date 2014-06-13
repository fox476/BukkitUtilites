package me.fox476.mainutil;

import org.bukkit.event.Listener;

public class TheWatch implements Listener {
	public TheWatch(MainUtil plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

}