package com.jw.lightwallet.utils;


public class Tx {
	
	public static enum	Type {SPENT, RECEIVED};
	public Type 		type;
	public long			amount;
	public String		txid;
	
	public Tx() {
		type = null;
		amount = 0;
		txid = null;
		
	}
	
	public static Tx StringToTx(String txstring) {
		Tx tx = new Tx();
		
		// Get tx type
		if (txstring.split(" money")[0].split(" ")[2].equals("Received")) {
			tx.type = Type.RECEIVED;
		}
		else {tx.type = Type.SPENT;}
		
		// Get tx amount
		tx.amount = (long) (Double.parseDouble(txstring.split(": ")[1].split(",")[0]) * 1e12);
		
		// Get tx id
		String temptxid = txstring.split("<")[1];
		tx.txid = temptxid.substring(0, temptxid.length()-1);
		
		return tx;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}


}
