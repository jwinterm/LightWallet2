package com.jw.lightwallet.utils;

public class DaemonValues {
	
	int 	blockheight;
	int 	diff;
	int 	hashrate;
	long 	lastblocktime;
	long	lastblockreward;
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
		this.hashrate = diff/120;
	}
	public float getHashrate() {
		return hashrate/1e6f;
	}

	public long getLastblocktime() {
		return lastblocktime;
	}
	public void setLastblocktime(long lastblocktime) {
		this.lastblocktime = lastblocktime;
	}
	
	public long getLastblockreward() {
		return lastblockreward;
	}
	public void setLastblockreward(long lastblockreward) {
		this.lastblockreward = lastblockreward;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
