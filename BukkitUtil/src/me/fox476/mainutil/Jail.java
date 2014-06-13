package me.fox476.mainutil;

import org.bukkit.event.Listener;

public class Jail implements Listener {
	public Jail(MainUtil plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

}