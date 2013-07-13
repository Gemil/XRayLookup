package de.cubecontinuum.XRayLookup.ExtensionHandlers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import me.botsko.prism.Prism;
import me.botsko.prism.actionlibs.ActionsQuery;
import me.botsko.prism.actionlibs.MatchRule;
import me.botsko.prism.actionlibs.QueryParameters;
import me.botsko.prism.actionlibs.QueryResult;
import me.botsko.prism.actions.Handler;

import org.bukkit.Bukkit;
import de.cubecontinuum.XRayLookup.OreLookup;
import de.cubecontinuum.XRayLookup.XRayLookup;

public class PrismExtension extends BasicExtension {
	private Prism prism;

	public PrismExtension(XRayLookup plugin) {
		this.plugin = plugin;
		this.load();
	}
	@Override
	public void load() {
		prism = (Prism) Bukkit.getServer().getPluginManager().getPlugin("Prism");
		
		if (prism != null) {
			this.loaded = true;
			plugin.log("Prism was loaded");
		}
	}

	@Override 
	public void unload() {
		this.prism = null;
		this.loaded = false;
	}

	@Override
	public OreLookup lookup(String player, Integer time, List<Integer> restrict) {
		OreLookup ore = new OreLookup(player);
		for (String w : plugin.getConfiguration().getWorlds()) {
			
			QueryParameters parameters = new QueryParameters();
			
			parameters.addActionType("block-break",MatchRule.INCLUDE);
			parameters.setWorld(w);
			
			String dateFrom = null;
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, -1 * time);
			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateFrom = form.format(cal.getTime());
			
			parameters.setSinceTime(dateFrom);
			parameters.addPlayerName(player);
			parameters.setLimit(-1); 
			parameters.setAllowNoRadius(true);
			for (int value : restrict){
				parameters.addBlockFilter(value, (byte) 0);
			}
			
			try {
				ActionsQuery aq = new ActionsQuery(this.prism);
				QueryResult lookupResult = aq.lookup( parameters );
				if(!lookupResult.getActionResults().isEmpty()){

					List<Handler> results = lookupResult.getActionResults();
					if (results != null) {
						for (Handler a: results) {
							ore.add(a.getBlockId(), a.getAggregateCount());
						}
					}				
				}
			}
			catch(NullPointerException ex) {
				plugin.log("Unexpected Exception while fetching Data from Prism: "+ex.getMessage());
			}	
		}
		return ore;
	}

}
