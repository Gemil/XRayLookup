package de.cubecontinuum.XRayLookup;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.cubecontinuum.XRayLookup.ExtensionHandlers.BasicExtension;
import de.cubecontinuum.XRayLookup.ExtensionHandlers.CoreProtectExtension;
import de.cubecontinuum.XRayLookup.ExtensionHandlers.LogBlockExtension;
import de.cubecontinuum.XRayLookup.ExtensionHandlers.Metrics;


public class XRayLookup extends JavaPlugin {
	public static XRayLookup xraylookup;
	private Logger log = Logger.getLogger("Minecraft");
	private BasicExtension lookup;
	private XRayConfig config;
	
	private List<Integer> searchblocks = Arrays.asList(1,14,15,16,21,56,73,74,87,129,153); 
	
	public XRayLookup() {
		super();
		XRayLookup.xraylookup = this;
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
		
		if (this.lookup.isEnabled()) {
			Bukkit.getServer().getPluginManager().registerEvents(new XRayListener(), this);
			this.log(this.getName()+" loaded successfully");
		}
		else {
			this.log("Couldn't find CoreProtect or LogBlock");
		}
	}
	public void onDisable(){ 
		this.log(this.getName()+" has been disabled");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("xraylookup")){
			String target;
			if (args.length == 0 && sender instanceof Player) {
				target = sender.getName();
			}
			else {
				target = args[0];
			}
			OreLookup ores = lookup.lookup(target, this.config.getLookuptime(), this.searchblocks);
			this.sendData((Player)sender, ores);
			return true;
		} 
		return false; 
	}
	public void sendData(Player player, OreLookup ores) {
		DecimalFormat f = new DecimalFormat("#0.000");
		player.sendMessage(ChatColor.GOLD+"### Ore Rates from "+ChatColor.BLUE+ores.getPlayer()+ChatColor.GOLD+" ###");
		player.sendMessage(ChatColor.DARK_GRAY+"Stone: "+ores.getStone()+" ### Netherrack: "+ores.getNetherrack());
		player.sendMessage(ChatColor.DARK_AQUA+"Diamondrate: "+((ores.getDiamondRate() > this.config.getRate_diamond())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getDiamondRate())+"% ("+ores.getDiamond()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Emeraldrate: "+((ores.getEmeraldRate() > this.config.getRate_emerald())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getEmeraldRate())+"% ("+ores.getEmerald()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Goldrate: "+((ores.getGoldRate() > this.config.getRate_gold())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getGoldRate())+"% ("+ores.getGold()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Ironrate: "+((ores.getIronRate() > this.config.getRate_iron())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getIronRate())+"% ("+ores.getIron()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Redstonerate: "+((ores.getRedstoneRate() > this.config.getRate_redstone())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getRedstoneRate())+"% ("+ores.getRedstone()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Lapisrate: "+((ores.getLapisRate() > this.config.getRate_lapis())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getLapisRate())+"% ("+ores.getLapis()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Coalrate: "+((ores.getCoalRate() > this.config.getRate_coal())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getCoalRate())+"% ("+ores.getCoal()+")");
		player.sendMessage(ChatColor.DARK_AQUA+"Quartzrate: "+((ores.getQuartzRate() > this.config.getRate_quartz())?ChatColor.RED:ChatColor.GREEN)+f.format(ores.getQuartzRate())+"% ("+ores.getQuartz()+")");

	}

	private void loadConfig() {
		this.config = new XRayConfig();
	}
	private void loadDependencies() {

		this.lookup = new CoreProtectExtension();
		if (!this.lookup.isEnabled()) {
			this.lookup = new LogBlockExtension();
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
