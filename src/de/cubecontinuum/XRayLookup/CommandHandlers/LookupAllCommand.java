package de.cubecontinuum.XRayLookup.CommandHandlers;

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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cubecontinuum.XRayLookup.OreLookup;
import de.cubecontinuum.XRayLookup.XRayLookup;

public class LookupAllCommand implements CommandExecutor {

	private XRayLookup plugin;
	
	public LookupAllCommand(XRayLookup plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player s = (Player)sender;
		if (plugin.getLookup().isEnabled()) {	
			final Player[] onlineplayer = Bukkit.getOnlinePlayers();
			
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable(){
				@Override
				public void run() {
					Map<String, Double> top = new HashMap<String, Double>();
					for (Player player : onlineplayer) {
						OreLookup tmp = plugin.getLookup().lookup(player.getName(), plugin.getConfiguration().getLookuptime(), Arrays.asList(1,56)); 
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
			        
			        s.sendMessage(ChatColor.GOLD+"### Diamond Rates from all "+ChatColor.BLUE+"Online Players"+ChatColor.GOLD+" ###");
			        DecimalFormat f = new DecimalFormat("#0.000");
			        int i = 1;
			        for (Double doub : reverseKeys) {
			    		Set<String> values = treeMap.get(doub);
			    		String temp = "";
			            for (String string : values) {
			            	temp = temp+" " + string;
			            }
			            s.sendMessage(ChatColor.DARK_AQUA+"TOP "+i+":"+temp+((doub > plugin.getConfiguration().getRate_diamond())?ChatColor.RED:ChatColor.DARK_GRAY)+" (Rate: "+f.format(doub)+"%)");
			            i++;
			        }
				}
			});
		}
		else {
			s.sendMessage(ChatColor.RED+"Error! No Logging Plugin was loaded");
		}
	    return true;
	}

}
