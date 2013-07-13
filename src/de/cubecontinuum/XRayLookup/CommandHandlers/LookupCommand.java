package de.cubecontinuum.XRayLookup.CommandHandlers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cubecontinuum.XRayLookup.OreLookup;
import de.cubecontinuum.XRayLookup.XRayLookup;

public class LookupCommand implements CommandExecutor {
	
	private XRayLookup plugin;
	
	public LookupCommand(XRayLookup plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player s = (Player)sender;
		if (plugin.getLookup().isEnabled()) {	
			String target = null;
			if (args.length == 0 && sender instanceof Player) {
				target = sender.getName();
			}
			else {
				target = args[0];
			}
			final String p = target;
			
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable(){
	
				@Override
				public void run() {
					OreLookup ores = plugin.getLookup().lookup(p, plugin.getConfiguration().getLookuptime(), plugin.getSearchblocks());
					plugin.sendData(s, ores);
					
				}
				
			});
		}
		else {
			s.sendMessage(ChatColor.RED+"Error! No Logging Plugin was loaded");
		}
		return true;
	}
}
