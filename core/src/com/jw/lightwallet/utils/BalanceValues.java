package com.jw.lightwallet.utils;

public class BalanceValues {
	
	long		unlockedbalance;
	long		balance;
	boolean		checked;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Long getUnlockedbalance() {
		return unlockedbalance;
	}
	public void setUnlockedbalance(long unlockedbalance) {
		this.unlockedbalance = unlockedbalance;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}

}
