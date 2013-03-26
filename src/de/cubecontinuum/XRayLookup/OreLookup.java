package de.cubecontinuum.XRayLookup;

public class OreLookup {
	private int diamond = 0;
	private int emerald = 0;
	private int redstone = 0;
	private int coal = 0;
	private int iron = 0;
	private int quartz = 0;
	private int gold = 0;
	private int lapis = 0;
	
	private int stone = 0;
	private int netherrack = 0;
	
	private String player;
	public OreLookup (String p) {
		this.player = p;
	}
	public void add(int mcid) {
		if (mcid == 1) {
			this.stone++;
		}
		else if (mcid == 14) {
			this.gold++;
		}
		else if (mcid == 15) {
			this.iron++;
		}
		else if (mcid == 16) {
			this.coal++;
		}
		else if (mcid == 21) {
			this.lapis++;
		}
		else if (mcid == 56) {
			this.diamond++;
		}
		else if (mcid == 73 || mcid == 74) {
			this.redstone++;
		}
		else if (mcid == 87) {
			this.netherrack++;
		}
		else if (mcid == 129) {
			this.emerald++;
		}
		else if (mcid == 153) {
			this.quartz++;
		}
		else {
			XRayLookup.xraylookup.log("Unknown id: "+mcid);
		}
	}
	public int getNetherrack() {
		return netherrack;
	}
	public void setNetherrack(int netherrack) {
		this.netherrack = netherrack;
	}
	public int getDiamond() {
		return diamond;
	}
	public double getDiamondRate() {
		if (stone == 0) { 
			return 0; 
		}
		else {
			return (double) diamond/stone*100;
		}
	}
	public void setDiamond(int diamond) {
		this.diamond = diamond;
	}
	public int getEmerald() {
		return emerald;
	}
	public double getEmeraldRate() {
		if (stone == 0) { 
			return 0; 
		}
		else {
			return (double) emerald/stone*100;
		}
	}
	public void setEmerald(int emerald) {
		this.emerald = emerald;
	}
	public int getRedstone() {
		return redstone;
	}
	public double getRedstoneRate() {
		if (stone == 0) { 
			return 0; 
		}
		else {
			return (double) redstone/stone*100;
		}
	}
	public void setRedstone(int redstone) {
		this.redstone = redstone;
	}
	public int getCoal() {
		return coal;
	}
	public double getCoalRate() {
		if (stone == 0) { 
			return 0; 
		}
		else {
			return (double) coal/stone*100;
		}
	}
	public void setCoal(int coal) {
		this.coal = coal;
	}
	public int getIron() {
		return iron;
	}
	public double getIronRate() {
		if (stone == 0) { 
			return 0; 
		}
		else {
			return (double) iron/stone*100;
		}
	}
	public void setIron(int iron) {
		this.iron = iron;
	}
	public int getQuartz() {
		return quartz;
	}
	public double getQuartzRate() {
		if (netherrack == 0) { 
			return 0; 
		}
		else {
			return (double) quartz/netherrack*100;
		}
	}
	public void setQuartz(int quartz) {
		this.quartz = quartz;
	}
	public int getGold() {
		return gold;
	}
	public double getGoldRate() {
		if (stone == 0) { 
			return 0; 
		}
		else {
			return (double) gold/stone*100;
		}
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getLapis() {
		return lapis;
	}
	public double getLapisRate() {
		if (stone == 0) { 
			return 0; 
		}
		else {
			return (double) lapis/stone*100;
		}
	}
	public void setLapis(int lapis) {
		this.lapis = lapis;
	}
	public int getStone() {
		return stone;
	}
	public void setStone(int stone) {
		this.stone = stone;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}	
}
