package me.fox476.mainutil;

import java.util.logging.Logger;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class MainUtil extends JavaPlugin {
	public final Logger logger = Logger.getLogger("Minecraft");
	public static MainUtil plugin;
	
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
	}
	@Override
	public void onDisable(){
		PluginDescriptionFile PYF = this.getDescription();
		this.logger.info(PYF.getName() + PYF.getVersion() + " Version" + "Has Been Disable!");
		
	}

}