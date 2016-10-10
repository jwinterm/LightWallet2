package com.jw.lightwallet.daemon;

public class DaemonResponse {

	String id;
	String jsonrpc;
	DaemonResult result;
	

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
	public DaemonResult getResult() {
		return result;
	}
	public void setResult(DaemonResult result) {
		this.result = result;
	}

}