package com.jw.lightwallet.utils;

public class BalanceValues {
	
	long		unlockedbalance;
	long		lockedbalance;
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
	public Long getLockedbalance() {
		return lockedbalance;
	}
	public void setLockedbalance(long lockedbalance) {
		this.lockedbalance = lockedbalance;
	}

}
