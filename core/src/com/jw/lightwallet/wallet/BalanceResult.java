package com.jw.lightwallet.wallet;

public class BalanceResult {
	
	long	unlockedbalance;
	long	lockedbalance;
	
	public long getUnlockedbalance() {
		return unlockedbalance;
	}
	public void setUnlockedbalance(long unlockedbalance) {
		this.unlockedbalance = unlockedbalance;
	}
	public long getLockedbalance() {
		return lockedbalance;
	}
	public void setLockedbalance(long lockedbalance) {
		this.lockedbalance = lockedbalance;
	}

}
