package me.fox476.mainutil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import me.fox476.mainutil.Listener.PlayerListener;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class MainUtil extends JavaPlugin {
	public final Logger logger = Logger.getLogger("Minecraft");
	public static MainUtil plugin;
	
	private boolean debug;                         // SpamSecure
	private String bcmd;                           // SpamSecure
	private String rcmd;                           // SpamSecure
	private String kcmd;                          // SpamSecure
	private int lines;                           // SpamSecure
	private int secounds;                        // SpamSecure
	private int kicks;                            // SpamSecure
	private int duplicates;                       // SpamSecure
	private Map<String, SpamSecure> SpamSecure = new HashMap<String, SpamSecure>();    // SpamSecure
	
	@Override
	public void onEnable() {
		PluginDescriptionFile PYF = this.getDescription();
		this.logger.info(PYF.getName() + " Description: " + PYF.getDescription() + PYF.getVersion() + " Version" + " Has Been Enable!");
		new KeepInv(this);
		new SpamSecure(this);
		new ItemDropCleaner(this);
		new ChatControl(this);
		new Jail(this);
		new TheWatch(this);
		
		PlayerListener pListener = new PlayerListener(this);
		getServer().getPluginManager().registerEvents(pListener, this);
		
	}
	@Override
	public void onDisable(){
		saveConfig();                                                                                   // SpamSecure
		SpamSecure.clear();                                                                            // SpamSecure
		
		PluginDescriptionFile PYF = this.getDescription();
		this.logger.info(PYF.getName() + PYF.getVersion() + " Version" + "Has Been Disable!");
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command commmand, String label, String[] args){
		if (args.length == 0){return false; }
		String sub = subCommand(args[0]);
		if (command.getName().equalsIgnoreCase("SpamSeucre")){
			if (!sender.hasPermission("Spam.spam" + sub) && !(sender instanceof ConsoleCommandSender)){
				return false;
			}
			if (sub.equalsIgnoreCase("debug")){
				debug = !debug;
				message(sender, "Sebug mode is now " + (debug ? "enabled" : "disabled") + ".");
				return true;
			}else if (sub.equalsIgnoreCase("reset") && args.length == 2){
				String cmd = rcmd;
				OfflinePlayer player;
				if (getServer().getPlayer(args[1]) == null){
					if ((player = getServer().getOfflinePlayer(args[1])) == null){
						message(sender, "That player died.");
						return true;
					}
				}else{
					player = getServer().getPlayer(args[1]);
				}
				cmd = cmd.replaceAll("\\[user//]", player.getName());
				message(sender, "Resetting user '" + player.getName() + "'...");
				getConfig().set("SpamSecure." + player.getName(), 0);
				getServer().dispatchCommand(getServer().getConsoleSender(), cmd);
				return true;
			}else if (sub.equalsIgnoreCase("version")){
				message(sender, "BukkitUtil" + this.getDescription().getVersion());
				message(sender, "by COW");
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	private String subCommand(String cmd){
		if (cmd.equalsIgnoreCase("debug") || cmd.equalsIgnoreCase("-d")){
			return "debug";
		}else if (cmd.equalsIgnoreCase("reset") || cmd.equalsIgnoreCase("-r")){
			return "reset";
		}else{
			return "version";
		}
	}
	public void message(CommandSender sender, String message){
		message = "[" + this + "] " + message;
		sender.sendMessage(message);
	}
	public void log(String message){
		message = "[" + this + "] " + message;
		getServer().getLogger().info(message);
	}
	public void debug(String message){
		if (debug){
			message = "[" + this + "] [Debug] " + message;
			getServer().getLogger().info(message);
		}
	}
	public void spamsecure(){
		for (Player player : getServer().getOnlinePlayers()){
			if (spammers.containsKey(player.getName())){contiune; }
			spammers.put(player.getName(), new Spammer(this, player.getName()));  // Spammer change to SpamSecure if needed
		}
		getConfig().setDefaults(YamlConfiguration.loadConfiguration(this.getClass().getResourceAsStream("/config.yml")));
		if (!new File(getDataFolder(), "config.yml").exists()){
			getConfig().options().copyDefaults(true);
		}
		this.debug = getConfig().getBoolean("debug", false );
		this.rcmd = getConfig().getString("reset_command", "pardon [user]");
		this.bcmd = getConfig().getString("ban_command", "kick [user] You have been banned for being a excessivly annoying cow.");
		this.kcmd = getConfig().getString("kick_command", "kick [user] Stop spamming. [kicks]");
		this.lines = getConfig().getInt("spam.lines", 3);
		this.secounds = getConfig().getInt("seconds", 1);
		this.kicks = getConfig().getInt("spam.kicks", 3);
		this.duplicates = getConfig().getInt("spam.duplicates", 3);
		
		getConfig().options().header(
				"SpamSecure" + this.getDescription().getVersion() + "\n" + "by COW\n" + "---\n" + "[user]: Evluates to the offending player\n" 
		         + "[kicks]: Evalutes to the number of kicks / total kicks before ban for the specified user\n" 
				 + "debug: Toggles additional (debugger) messages in the server.log\n" + "---\n" 
		         + "reset_command: Specifies the command to execute when resetting a user's infractions\n" 
				 + "ban_command: Specifies the command to execute on the server when the kick threshold has been reached\n" 
		         + "kick_command: Speifies the command to execute on the server when the spam threshold has been reached\n" + "---\n" + "spam:\n" 
				 + " lines: number of lines before the user is kicked (default is 3 lines of text)\n" 
		         + " seconds: number of seconds allowed before max lines is met and user is kicked (default is 1)\n" 
				 + " kicks: number of kicks before the user is banned (default is 3 kicks untill a ban)\n" 
		         + " duplicates: number of duplicate message before user is kicked (default is 3)"
				);
		        saveConfig();
	}
	public void unregister(String player){
		debug(player + " has been unregistered.");
		spammers.remove(player);        // spammers = spamsecure
	}
	public boolean addLine(String player, String message){
		if (!spammers.containsKey(player)){
			spammers.put(player, new Spamer(this, player));
		}
		return spammers.get(player).addLine(message);
	}
	public String getKickCommand(){
		return kcmd;
	}
	public String getBanCommand(){
		return bcmd;
	}
	public int getMaxKicks(){
		return kicks;
	}
	public int getLines(){
		return lines;
	}
	public int getMaxDuplicates(){
		return duplicates;
	}

}