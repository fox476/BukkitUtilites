package me.fox476.mainutil.Listener;

import me.fox476.mainutil.MainUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
	private MainUtil plugin;
	
	public PlayerListener(MainUtil instance){
		this.plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	void playerChat(PlayerChatEvent event){
		event.setCancelled(plugin.addLine(event.getPlayer().getName(), event.getMessage()));
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	void playerCommand(PlayerCommandPreprocessEvent event){
		plugin.addLine(event.getPlayer().getName(), event.getMessage());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	void playerQuit(PlayerQuitEvent event){
		plugin.unregister(event.getPlayer().getName());
	}
	
}