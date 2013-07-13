package de.cubecontinuum.XRayLookup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class XRayListener implements Listener {
	
	private XRayLookup plugin;
	
	public XRayListener(XRayLookup plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void onOreBreak(PlayerJoinEvent e) {
		this.lookup(e);
	}
	@EventHandler
	public void onOreBreak(PlayerQuitEvent e) {
		this.lookup(e);
	}
	private void lookup(PlayerEvent e){
		if (plugin.getLookup().isEnabled()) {
			// Lookup in einem Asynchronen Task
			final String p = e.getPlayer().getName();
			final Player[] onlineplayer = Bukkit.getOnlinePlayers();

			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable(){

				@Override 
				public void run() {
					OreLookup ores = plugin.getLookup().lookup(p, plugin.getConfiguration().getLookuptime(), plugin.getSearchblocks());
					if ((plugin.getConfiguration().getRate_diamond() < ores.getDiamondRate() ||
						plugin.getConfiguration().getRate_emerald() < ores.getEmeraldRate() ||
						plugin.getConfiguration().getRate_gold() < ores.getGoldRate() ||
						plugin.getConfiguration().getRate_iron() < ores.getIronRate() ||
						plugin.getConfiguration().getRate_lapis() < ores.getLapisRate() ||
						plugin.getConfiguration().getRate_redstone() < ores.getRedstoneRate() ||
						plugin.getConfiguration().getRate_coal() < ores.getCoalRate())
						&& ores.getStone() > plugin.getConfiguration().getLookupcount()) {
						
						for(Player po : onlineplayer) {
							if (po.hasPermission("xraylookup.recieve")) {
									plugin.sendData(po, ores);
							}
						}
					}			
				}
				
			});

			
		}
	}
}
