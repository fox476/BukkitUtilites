package me.fox476.mainutil;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MainUtil extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		this.getLogger().info("Strating up Bukkit Utilites!");
		new KeepInv(this);
		new ItemDropCleaner(this);
	}
	@Override
	public void onDisable(){
		this.getLogger().info("shuting down Bukkit Utilites!");
	}

}