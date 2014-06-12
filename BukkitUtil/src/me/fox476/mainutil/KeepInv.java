package me.fox476.mainutil;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class KeepInv implements Listener {
	public KeepInv(MainUtil plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	// might be backwards
	@EventHandler(priority = EventPriority.HIGHEST) public void onPlayerDeathEvent(PlayerDeathEvent event){
		if (event.getEntity().hasPermission("keepinv.enable")){
			event.getEntity().sendMessage(ChatColor.GREEN + "KeepInv");
		}else{
			Integer InventorySize = event.getEntity().getInventory().getSize();
			Integer i = 0;
			for (i=0; i<InventorySize; i++){
				ItemStack item = event.getEntity().getInventory().getItem(i);
				if(item != null)event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), item);
			}
			event.getEntity().getInventory().clear();
		}
	
	}
	
	@EventHandler(priority = EventPriority.HIGHEST) public void onPlayerJoin(PlayerJoinEvent event){
		event.getPlayer().getWorld().setGameRuleValue("keepInventory", "true");
	}
	
	@EventHandler(priority = EventPriority.HIGHEST) public void onPlayerTeleport(PlayerTeleportEvent event){
		event.getTo().getWorld().setGameRuleValue("keepInventory", "true");
	}

}