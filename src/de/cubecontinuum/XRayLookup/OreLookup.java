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
		this.add(mcid,1);
	}
	public void add(int mcid,int count) {
		if (mcid == 1) {
			this.stone = this.stone + count;
		}
		else if (mcid == 14) {
			this.gold = this.gold + count;
		}
		else if (mcid == 15) {
			this.iron = this.iron + count;
		}
		else if (mcid == 16) {
			this.coal = this.coal + count;
		}
		else if (mcid == 21) {
			this.lapis = this.lapis + count;
		}
		else if (mcid == 56) {
			this.diamond = this.diamond + count;
		}
		else if (mcid == 73 || mcid == 74) {
			this.redstone = this.redstone + count;
		}
		else if (mcid == 87) {
			this.netherrack = this.netherrack + count;
		}
		else if (mcid == 129) {
			this.emerald = this.emerald + count;
		}
		else if (mcid == 153) {
			this.quartz = this.quartz + count;
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
			double tmp = (double) diamond/stone*100;
			return (tmp > 100)?100.0:tmp;
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
			double tmp = (double) emerald/stone*100;
			return (tmp > 100)?100.0:tmp;
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
			double tmp = (double) redstone/stone*100;
			return (tmp > 100)?100.0:tmp;
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
			double tmp = (double) coal/stone*100;
			return (tmp > 100)?100.0:tmp;
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
			double tmp = (double) iron/stone*100;
			return (tmp > 100)?100.0:tmp;
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
			double tmp = (double) quartz/netherrack*100;
			return (tmp > 100)?100.0:tmp;
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
			double tmp = (double) gold/stone*100;
			return (tmp > 100)?100.0:tmp;
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
			double tmp = (double) lapis/stone*100;
			return (tmp > 100)?100.0:tmp;
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
