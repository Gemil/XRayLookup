package de.cubecontinuum.XRayLookup.ExtensionHandlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import de.cubecontinuum.XRayLookup.OreLookup;
import de.cubecontinuum.XRayLookup.XRayLookup;
import de.diddiz.LogBlock.BlockChange;
import de.diddiz.LogBlock.LogBlock;
import de.diddiz.LogBlock.QueryParams;
import de.diddiz.LogBlock.QueryParams.BlockChangeType;
import de.diddiz.util.Block;

public class LogBlockExtension extends BasicExtension{
	private LogBlock logblock;


	public LogBlockExtension (XRayLookup plugin) {
		this.plugin = plugin;
		this.load();
	}
	@Override
	public void load() {
		logblock = (LogBlock) Bukkit.getServer().getPluginManager().getPlugin("LogBlock");
		
		if (logblock != null) {
			this.loaded = true;
			plugin.log("LogBlock was loaded");
		}
	}

	@Override
	public void unload() {
		this.logblock = null;
		this.loaded = false;
		
	}

	@Override
	public OreLookup lookup(String player, Integer time, List<Integer> restrict) {
		List<Block> blocks = new ArrayList<Block>();
		int i = 0;
		for (int value : restrict){
			blocks.add(i,new Block(value, -1));
			i++;
		}
		OreLookup ore = new OreLookup(player);
		for (String w : plugin.getConfiguration().getWorlds()) {
			QueryParams params = new QueryParams(this.logblock);
			params.bct = BlockChangeType.DESTROYED;
			params.since = time / 60;
			params.limit = -1;
			params.setPlayer(player);
			params.types = blocks;
			params.world = Bukkit.getWorld(w);
			
			params.needType = true;

			try {
			    for (BlockChange bc : this.logblock.getBlockChanges(params)) {
		    		ore.add(bc.replaced);
			    }
			} 
			catch (SQLException ex) {
				plugin.log("Unexpected Exception while fetching Data from LogBlock: "+ex.getMessage());
			}
		}
		return ore;
	}

}
