package com.jw.lightwallet.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;

public class DaemonRPC {
	public String 	address;
	String			status;
	String			getlastblock;
	
	public DaemonRPC () {
		address = "http://node.moneroclub.com:8880/json_rpc";
		getlastblock = "{\"jsonrpc\":\"2.0\",\"id\":\"test\",\"method\":\"getlastblockheader\",\"params\":[]}";
	}
	
	public void getinfo () {		
		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl(address);
		httpGet.setContent(getlastblock);

		 Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		                status = httpResponse.getResultAsString();
		                System.out.print(status);
		        }
		 
		        public void failed(Throwable t) {
		                status = "failed";
		                //do stuff here based on the failed attempt
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
		 });
	}

}
