package me.fox476.mainutil;

import org.bukkit.event.Listener;

public class ChatControl implements Listener {
	public ChatControl(MainUtil plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
}