package com.jw.lightwallet.tx;

public class TxResponse {
	
	String 			id;
	String			jsonrpc;
	TxResult		result;
	TxError			error;
	
	
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
	public TxResult getResult() {
		return result;
	}
	public void setResult(TxResult result) {
		this.result = result;
	}
	public TxError getError() {
		return error;
	}
	public void setError(TxError error) {
		this.error = error;
	}
	
	

}
