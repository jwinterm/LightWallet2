package com.jw.lightwallet.utils;

public class DaemonValues {
	
	int blockheight;
	int diff;
	int hashrate;
	int lastblocktime;
	int lastblockreward;
	String status;
	
	
	public int getBlockheight() {
		return blockheight;
	}
	public void setBlockheight(int blockheight) {
		this.blockheight = blockheight;
	}
	
	public int getDiff() {
		return diff;
	}
	public void setDiff(int diff) {
		this.diff = diff;
		this.hashrate = diff/60;
	}
	public int getHashrate() {
		return hashrate;
	}

	public int getLastblocktime() {
		return lastblocktime;
	}
	public void setLastblocktime(int lastblocktime) {
		this.lastblocktime = lastblocktime;
	}
	
	public int getLastblockreward() {
		return lastblockreward;
	}
	public void setLastblockreward(int lastblockreward) {
		this.lastblockreward = lastblockreward;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
