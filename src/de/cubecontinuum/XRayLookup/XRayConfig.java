package de.cubecontinuum.XRayLookup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

public class XRayConfig {
	private double rate_diamond = 3.0;
	private double rate_emerald = 5.0;
	private double rate_redstone = 15.0;
	private double rate_coal = 20.0;
	private double rate_iron = 15.0;
	private double rate_quartz = 1000.0;
	private double rate_gold = 5.0;
	private double rate_lapis = 15.0;
	private int lookuptime = 259200; // 72h
	private int lookupcount = 1000; 
	private List<String> worlds = new ArrayList<String>(Arrays.asList("world","world_nether")); // ();

	private final YamlConfiguration config = new YamlConfiguration();
	
	public XRayConfig() {
		File configDir = new File("plugins/"+XRayLookup.xraylookup.getName());
		if (!configDir.exists()) {
            configDir.mkdir();
        }
		File configFile = new File("plugins/"+XRayLookup.xraylookup.getName()+"/config.yml");

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (Exception e) {
                XRayLookup.xraylookup.log("Error occured on Config generation");
            }
        }
        if (this.loadConfig()) {
        	this.checkConfig();
        	this.convertData();
        }
        else {
        	this.checkConfig();
        	this.convertData();
        }
		
	}

	private void convertData() {
		this.rate_diamond = config.getDouble("rate_diamond", this.rate_diamond);
		this.rate_emerald = config.getDouble("rate_emerald", this.rate_emerald);
		this.rate_redstone = config.getDouble("rate_redstone", this.rate_redstone);
		this.rate_coal = config.getDouble("rate_coal", this.rate_coal);
		this.rate_iron = config.getDouble("rate_iron", this.rate_iron);
		this.rate_quartz = config.getDouble("rate_quartz", this.rate_quartz);
		this.rate_gold = config.getDouble("rate_gold", this.rate_gold);
		this.rate_lapis = config.getDouble("rate_lapis", this.rate_lapis);
		this.lookuptime = config.getInt("lookuptime", this.lookuptime);
		this.lookupcount = config.getInt("lookupcount", this.lookupcount);
		this.worlds = (List<String>) config.getList("worlds", this.worlds);
	}

	private void checkConfig() {
		Set<String> keys = config.getKeys(false);
		
		if (!keys.contains("rate_diamond")) {
            config.set("rate_diamond", this.rate_diamond);
        }
		if (!keys.contains("rate_emerald")) {
            config.set("rate_emerald", this.rate_emerald);
        }
		if (!keys.contains("rate_redstone")) {
            config.set("rate_redstone", this.rate_redstone);
        }
		if (!keys.contains("rate_coal")) {
            config.set("rate_coal", this.rate_coal);
        }
		if (!keys.contains("rate_iron")) {
            config.set("rate_iron", this.rate_iron);
        }
		if (!keys.contains("rate_quartz")) {
            config.set("rate_quartz", this.rate_quartz);
        }
		if (!keys.contains("rate_gold")) {
            config.set("rate_gold", this.rate_gold);
        }
		if (!keys.contains("rate_lapis")) {
            config.set("rate_lapis", this.rate_lapis);
        }
		if (!keys.contains("lookuptime")) {
            config.set("lookuptime", this.lookuptime);
        }
		if (!keys.contains("lookupcount")) {
            config.set("lookupcount", this.lookupcount);
        }
		if (!keys.contains("worlds")) {
            config.set("worlds", this.worlds);
        }
		try {
            config.save("plugins/"+XRayLookup.xraylookup.getName()+"/config.yml");
        } 
		catch (IOException ex) {
        	XRayLookup.xraylookup.log("Couldn't save config: " + ex.getMessage());
        }
	}

	private boolean loadConfig() {
		try {
            config.load("plugins/"+XRayLookup.xraylookup.getName()+"/config.yml");
            return true;
        } 
		catch (Exception ex) {
			XRayLookup.xraylookup.log("[QuickSignReloaded] Couldn't load config: " + ex.getMessage());
            return false;
        }
	}

	public double getRate_diamond() {
		return rate_diamond;
	}

	public void setRate_diamond(double rate_diamond) {
		this.rate_diamond = rate_diamond;
	}

	public double getRate_emerald() {
		return rate_emerald;
	}

	public void setRate_emerald(double rate_emerald) {
		this.rate_emerald = rate_emerald;
	}

	public double getRate_redstone() {
		return rate_redstone;
	}

	public void setRate_redstone(double rate_redstone) {
		this.rate_redstone = rate_redstone;
	}

	public double getRate_coal() {
		return rate_coal;
	}

	public void setRate_coal(double rate_coal) {
		this.rate_coal = rate_coal;
	}

	public double getRate_iron() {
		return rate_iron;
	}

	public void setRate_iron(double rate_iron) {
		this.rate_iron = rate_iron;
	}

	public double getRate_quartz() {
		return rate_quartz;
	}

	public void setRate_quartz(double rate_quartz) {
		this.rate_quartz = rate_quartz;
	}

	public double getRate_gold() {
		return rate_gold;
	}

	public void setRate_gold(double rate_gold) {
		this.rate_gold = rate_gold;
	}

	public double getRate_lapis() {
		return rate_lapis;
	}

	public void setRate_lapis(double rate_lapis) {
		this.rate_lapis = rate_lapis;
	}
	public int getLookuptime() {
		return lookuptime;
	}

	public void setLookuptime(int lookuptime) {
		this.lookuptime = lookuptime;
	}

	public int getLookupcount() {
		return lookupcount;
	}

	public void setLookupcount(int lookupcount) {
		this.lookupcount = lookupcount;
	}

	public List<String> getWorlds() {
		return worlds;
	}

	public void setWorlds(ArrayList<String> worlds) {
		this.worlds = worlds;
	}

}
