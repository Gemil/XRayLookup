package de.cubecontinuum.XRayLookup.ExtensionHandlers;

import java.util.List;

import de.cubecontinuum.XRayLookup.OreLookup;
import de.cubecontinuum.XRayLookup.XRayLookup;

public abstract class BasicExtension {
	protected XRayLookup plugin;
	protected boolean loaded = false;
	public abstract void load();
	public abstract void unload();
	public abstract OreLookup lookup(String player, Integer time, List<Integer> restrict);
	public boolean isEnabled() {
		return this.loaded;
	}
	
}
