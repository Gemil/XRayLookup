package de.cubecontinuum.XRayLookup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class XRayListener implements Listener {
	@EventHandler
	public void onOreBreak(PlayerJoinEvent e) {
		this.lookup(e);
	}
	@EventHandler
	public void onOreBreak(PlayerQuitEvent e) {
		this.lookup(e);
	}
	private void lookup(PlayerEvent e){
		if (XRayLookup.xraylookup.getLookup().isEnabled()) {
			OreLookup ores = XRayLookup.xraylookup.getLookup().lookup(e.getPlayer().getName(), XRayLookup.xraylookup.getConfiguration().getLookuptime(), XRayLookup.xraylookup.getSearchblocks());
			if ((XRayLookup.xraylookup.getConfiguration().getRate_diamond() < ores.getDiamondRate() ||
				XRayLookup.xraylookup.getConfiguration().getRate_emerald() < ores.getEmeraldRate() ||
				XRayLookup.xraylookup.getConfiguration().getRate_gold() < ores.getGoldRate() ||
				XRayLookup.xraylookup.getConfiguration().getRate_iron() < ores.getIronRate() ||
				XRayLookup.xraylookup.getConfiguration().getRate_lapis() < ores.getLapisRate() ||
				XRayLookup.xraylookup.getConfiguration().getRate_redstone() < ores.getRedstoneRate() ||
				XRayLookup.xraylookup.getConfiguration().getRate_coal() < ores.getCoalRate())
				&& ores.getStone() > XRayLookup.xraylookup.getConfiguration().getLookupcount()) {
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if (p.hasPermission("xraylookup.recieve")) {
							XRayLookup.xraylookup.sendData(p, ores);
					}
				}
			}
		}
	}

}
