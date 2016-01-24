package com.jw.lightwallet.wallet;

public class BalanceResult {
	
	long	unlocked_balance;
	long	balance;
	
	public long getUnlockedbalance() {
		return unlocked_balance;
	}
	public void setUnlockedbalance(long unlocked_balance) {
		this.unlocked_balance = unlocked_balance;
	}
	public long getLockedbalance() {
		return balance;
	}
	public void setLockedbalance(long balance) {
		this.balance = balance;
	}

}
