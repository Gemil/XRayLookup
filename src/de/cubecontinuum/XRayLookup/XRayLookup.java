package de.cubecontinuum.XRayLookup;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.cubecontinuum.XRayLookup.CommandHandlers.LookupAllCommand;
import de.cubecontinuum.XRayLookup.CommandHandlers.LookupCommand;

import de.cubecontinuum.XRayLookup.ExtensionHandlers.BasicExtension;
import de.cubecontinuum.XRayLookup.ExtensionHandlers.CoreProtectExtension;
import de.cubecontinuum.XRayLookup.ExtensionHandlers.LogBlockExtension;
import de.cubecontinuum.XRayLookup.ExtensionHandlers.Metrics;
import de.cubecontinuum.XRayLookup.ExtensionHandlers.PrismExtension;


public class XRayLookup extends JavaPlugin {

	private Logger log = Logger.getLogger("Minecraft");
	private BasicExtension lookup;
	private XRayConfig config;
	
	private List<Integer> searchblocks = Arrays.asList(1,14,15,16,21,56,73,74,87,129,153); 
	
	public XRayLookup() {
		super();

	}
	public void onEnable(){ 
		this.loadConfig();
		this.loadDependencies();
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
			this.log("Couldn't connect to http://mcstats.org");
		}
		try {
			getCommand("xraylookup").setExecutor(new LookupCommand(this));
			getCommand("xraylookupall").setExecutor(new LookupAllCommand(this));
		}
		catch (NullPointerException e) {
			this.log("Error on Commandregistration!");
		}
		if (this.lookup.isEnabled()) {
			Bukkit.getServer().getPluginManager().registerEvents(new XRayListener(this), this);
			this.log(this.getName()+" loaded successfully");
		}
		else {
			this.log("Couldn't find CoreProtect, LogBlock or Prism");
		}
	}
	public void onDisable(){ 
		this.log(this.getName()+" has been disabled");
	}

	public void sendData(Player player, OreLookup ores) {
		DecimalFormat f = new DecimalFormat("#0.000");
		player.sendMessage(ChatColor.GOLD+"### Ore Rates from "+ChatColor.BLUE+ores.getPlayer()+ChatColor.GOLD+" ###");
		player.sendMessage(ChatColor.DARK_GRAY+"Stone: "+ores.getStone()+" ### Netherrack: "+ores.getNetherrack());
		player.sendMessage(ChatColor.DARK_AQUA+"Probability of XRAY: "+(((ores.getDiamondRate()*10)+(ores.getGoldRate()*3)+(ores.getLapisRate()*10)+(ores.getIronRate()*1) > 100)?ChatColor.RED:ChatColor.GREEN)+ f.format((ores.getDiamondRate()*10)+(ores.getGoldRate()*3)+(ores.getLapisRate()*10)+(ores.getIronRate()*1))+"%");
		player.sendMessage(ChatColor.DARK_AQUA+"Diamond: "+((ores.getDiamondRate() > this.config.getRate_diamond())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getDiamondRate())+"% ("+ores.getDiamond()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Emerald: "+((ores.getEmeraldRate() > this.config.getRate_emerald())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getEmeraldRate())+"% ("+ores.getEmerald()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Gold: "+((ores.getGoldRate() > this.config.getRate_gold())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getGoldRate())+"% ("+ores.getGold()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Iron: "+((ores.getIronRate() > this.config.getRate_iron())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getIronRate())+"% ("+ores.getIron()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Redstone: "+((ores.getRedstoneRate() > this.config.getRate_redstone())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getRedstoneRate())+"% ("+ores.getRedstone()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Lapis: "+((ores.getLapisRate() > this.config.getRate_lapis())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getLapisRate())+"% ("+ores.getLapis()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Coal: "+((ores.getCoalRate() > this.config.getRate_coal())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getCoalRate())+"% ("+ores.getCoal()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Quartz: "+((ores.getQuartzRate() > this.config.getRate_quartz())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getQuartzRate())+"% ("+ores.getQuartz()+")");
	}

	private void loadConfig() {
		this.config = new XRayConfig(this);
	}
	private void loadDependencies() {
		this.lookup = new CoreProtectExtension(this);
		if (!this.lookup.isEnabled()) {
			this.lookup = new LogBlockExtension(this);
		}
		if (!this.lookup.isEnabled()) {
			this.lookup = new PrismExtension(this);
		}
	}
	public void log(String message) {
		this.log.info("["+this.getName()+"] "+message);
	}
	public XRayConfig getConfiguration() {
		return config;
	}
	public void setConfig(XRayConfig config) {
		this.config = config;
	}
	public List<Integer> getSearchblocks() {
		return searchblocks;
	}
	public void setSearchblocks(List<Integer> searchblocks) {
		this.searchblocks = searchblocks;
	}
	public BasicExtension getLookup() {
		return lookup;
	}
	public void setLookup(BasicExtension lookup) {
		this.lookup = lookup;
	}
}
