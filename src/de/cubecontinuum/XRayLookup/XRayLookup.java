package de.cubecontinuum.XRayLookup;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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
import de.cubecontinuum.XRayLookup.ExtensionHandlers.PrismExtension;


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
			this.log("Couldn't find CoreProtect, LogBlock or Prism");
		}
	}
	public void onDisable(){ 
		this.log(this.getName()+" has been disabled");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		String target = null;
		if (args.length == 0 && sender instanceof Player) {
			target = sender.getName();
		}
		else {
			target = args[0];
		}
		
		if(cmd.getName().equalsIgnoreCase("xraylookup")){
			OreLookup ores = lookup.lookup(target, this.config.getLookuptime(), this.searchblocks);
			this.sendData((Player)sender, ores);
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("xraylookupall")) {
			Map<String, Double> top = new HashMap<String, Double>();
			for (Player player : Bukkit.getOnlinePlayers()) {
				OreLookup tmp = lookup.lookup(player.getName(), this.config.getLookuptime(), Arrays.asList(1,56)); 
				top.put(player.getName(), tmp.getDiamondRate());
			}

			// Sortieren der Werte
			Set<String> keys = top.keySet();
			TreeMap<Double, Set<String>> treeMap = new TreeMap<Double, Set<String>>();
			for (String key : keys) {
	            double value = top.get(key);
	            Set<String> values;
	            if(treeMap.containsKey(value)){
	                values = treeMap.get(value);
	                values.add(key);
	            } 
	            else {
	                values = new HashSet<String>();
	                values.add(key);
	            }
	            
	            treeMap.put(value, values);
	        }
			
			// Ausgabe der Werte
			Set<Double> treeValues = treeMap.keySet();
			List<Double> reverseKeys = new LinkedList<Double>(treeValues);
	        Collections.reverse(reverseKeys);
	        
	        sender.sendMessage(ChatColor.GOLD+"### Diamond Rates from all "+ChatColor.BLUE+"Online Players"+ChatColor.GOLD+" ###");
	        DecimalFormat f = new DecimalFormat("#0.000");
	        int i = 1;
	        for (Double doub : reverseKeys) {
        		Set<String> values = treeMap.get(doub);
        		String temp = "";
	            for (String string : values) {
	            	temp = temp+" " + string;
	            }
	            sender.sendMessage(ChatColor.DARK_AQUA+"TOP "+i+":"+temp+ChatColor.DARK_GRAY+" (Rate: "+f.format(doub)+"%)");
	            i++;
	        }
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
		if (!this.lookup.isEnabled()) {
			this.lookup = new PrismExtension();
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
