package me.fox476.mainutil;

import org.bukkit.event.Listener;

public class SpamSecure implements Listener {
	public SpamSecure(MainUtil plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	private MainUtil plugin;
	private String name;
	private short duplicates = 0;
	private short lines = 0;
	private int kicks = 0;
	private long time = 0;
	private String message;
	
	public SpamSecure(MainUtil instance, String player){
		plugin = instance;
		name = player;
		kicks = plugin.getConfig().getInt("Spammer." + player, 0);
		plugin.debug(name + " has been registerd.");
	}
	
	private boolean kick(){
		kicks++;
		String cmd = plugin.getKickCommand();
		if (kicks >= plugin.getMaxKicks()){
			cmd = plugin.getBanCommand();
		}
		cmd = cmd.replaceAll("\\[user\\]", name);
		cmd = cmd.replaceAll("\\[kicks\\]", "[" + kicks + "/" + plugin.getMaxKicks() + "]");
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd);
		plugin.getConfig().set("spammers." + name, kicks);
		return true;
	}
	@SuppressWarnings("deprecation")
	public boolean addLine(String msg){
		if (plugin.getServer().getPlayer(name).hasPermission("SpamSecre.free")){
			return false;
		}
		if (message == null || message.equalsIgnoreCase(msg)){
			duplicates++;
			plugin.debug(">> " + name + ": duplicates message - " + duplicates + "/" + plugin.getMaxDuplicates());
		}else{
			duplicates = 0;
		}
		lines++;
		message = msg;
		if (duplicates >= plugin.getMaxDuplicates()){
			return kick();
		}
		if (System.currentTimeMillis() - time <= plugin.getTime()){
			plugin.debug(">> " + name + ": spam line - " + lines + "/" + plugin.getLines());
			if (lines >= plugin.getLines()){
				return kick();
			}
		}else {
			lines = 0;
			time = System.currentTimeMillis();
		}
		return false;
	}

}