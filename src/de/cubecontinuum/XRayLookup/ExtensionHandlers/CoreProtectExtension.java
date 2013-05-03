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
	public CoreProtectExtension() {
		this.load();
	}

	@Override
	public void load() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");
		// Check that CoreProtect is loaded
		if (plugin instanceof CoreProtect && Double.parseDouble(plugin.getDescription().getVersion()) > 1.6) {
			this.coreprotect = ((CoreProtect)plugin).getAPI();
			
			if (this.coreprotect.isEnabled() == false) {
				XRayLookup.xraylookup.log("Coreprotect was found, but the CoreProtect API was disabled");
			}
			else {
				XRayLookup.xraylookup.log("Coreprotect was loaded");
				this.loaded = true;
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
			if (result.getActionId() == 0 && XRayLookup.xraylookup.getConfiguration().getWorlds().contains(result.worldName())) {
				ore.add(result.getTypeId());
			}
		}
		return ore;
	}
}
