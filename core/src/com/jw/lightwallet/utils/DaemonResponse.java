package com.jw.lightwallet.utils;

public class DaemonResponse {
	String			status;
	int				blockheight;
	int				diff;
	int				hashrate;
	int				lastblocktime;
	float			blockreward;
	
	DaemonResponse (String status, int blockheight, int diff, int lastblocktime, float blockreward) {
		this.status = status;
		this.blockheight = blockheight;
		this.diff = diff;
		this.hashrate = diff/60;
		this.lastblocktime = lastblocktime;
		this.blockreward = blockreward;
	}

}
