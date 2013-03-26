package de.cubecontinuum.XRayLookup.ExtensionHandlers;

import java.util.List;

import de.cubecontinuum.XRayLookup.OreLookup;

public abstract class BasicExtension {

	protected boolean loaded = false;
	public abstract void load();
	public abstract void unload();
	public abstract OreLookup lookup(String player, Integer time, List<Integer> restrict);
	public boolean isEnabled() {
		return this.loaded;
	}
	
}
