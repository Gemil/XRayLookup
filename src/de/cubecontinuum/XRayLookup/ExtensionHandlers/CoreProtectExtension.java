package de.cubecontinuum.XRayLookup.ExtensionHandlers;


import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.cubecontinuum.XRayLookup.OreLookup;
import de.cubecontinuum.XRayLookup.XRayLookup;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.coreprotect.CoreProtectAPI.ParseResult;

public class CoreProtectExtension extends BasicExtension{
	private CoreProtectAPI coreprotect;
	public CoreProtectExtension(XRayLookup plugin) {
		this.plugin = plugin;
		this.load();
	}

	@Override
	public void load() { 
		Plugin cp = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");
		// Check that CoreProtect is loaded
		if (cp != null && cp instanceof CoreProtect) {
			this.coreprotect = ((CoreProtect)cp).getAPI();
			if (coreprotect.isEnabled()==true){
				if (coreprotect.APIVersion() >= 2) {
					plugin.log("Coreprotect was loaded");
					this.loaded = true;
				}
				else {
					plugin.log("Coreprotect Version must be 2.0.8 or higher!");
				}
			}
			else {
				plugin.log("Coreprotect was found, but the CoreProtect API was disabled");
			}
		}	
	}

	@Override
	public void unload() {
		this.coreprotect = null;
		this.loaded = false;
	}

	@Override
	public OreLookup lookup(String player, Integer time, List<Integer> restrict) {
		OreLookup ore = new OreLookup(player);
		List<String[]> blocks = this.coreprotect.performLookup(player, time, 0, null,  restrict, null);
		for (String[] value : blocks){
			ParseResult result = this.coreprotect.parseResult(value);
			if (result.getActionId() == 0 && plugin.getConfiguration().getWorlds().contains(result.worldName())) {
				ore.add(result.getTypeId());
			}
		}
		return ore;
	}
}
