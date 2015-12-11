package com.jw.lightwallet.daemon;

public class BlockHeader {
	
	int 		depth;
	int 		difficulty;
	String 		hash;
	int 		height;
	int			major_version;
	int			minor_version;
	int			nonce;
	boolean		orphan_status;
	String		prev_hash;
	int			reward;
	int			timestamp;
	
	
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getMajor_version() {
		return major_version;
	}
	public void setMajor_version(int major_version) {
		this.major_version = major_version;
	}
	public int getMinor_version() {
		return minor_version;
	}
	public void setMinor_version(int minor_version) {
		this.minor_version = minor_version;
	}
	public int getNonce() {
		return nonce;
	}
	public void setNonce(int nonce) {
		this.nonce = nonce;
	}
	public boolean isOrphan_status() {
		return orphan_status;
	}
	public void setOrphan_status(boolean orphan_status) {
		this.orphan_status = orphan_status;
	}
	public String getPrev_hash() {
		return prev_hash;
	}
	public void setPrev_hash(String prev_hash) {
		this.prev_hash = prev_hash;
	}
	public int getReward() {
		return reward;
	}
	public void setReward(int reward) {
		this.reward = reward;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	
	

}
