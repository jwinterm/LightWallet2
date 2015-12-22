package com.jw.lightwallet.wallet;

import com.jw.lightwallet.wallet.BalanceResult;

public class BalanceResponse {

	String id;
	String jsonrpc;
	BalanceResult result;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJsonrpc() {
		return jsonrpc;
	}
	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}
	public BalanceResult getResult() {
		return result;
	}
	public void setResult(BalanceResult result) {
		this.result = result;
	}

	
}
