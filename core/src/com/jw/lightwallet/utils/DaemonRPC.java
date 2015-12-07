package com.jw.lightwallet.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DaemonRPC {
	public String 	address;
	String			response;
	String			getlastblock;
	DaemonResponse	daemonresponse;
	
	public DaemonRPC () {
		address = "http://node.moneroclub.com:8880/json_rpc";
		getlastblock = "{\"jsonrpc\":\"2.0\",\"id\":\"test\",\"method\":\"getlastblockheader\",\"params\":[]}";
	}
	
	public DaemonResponse getinfo () {		
		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl(address);
		httpGet.setContent(getlastblock);		

		Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		                response = httpResponse.getResultAsString();
		                System.out.print(response);
		                JsonObject gsonresponse = new Gson().fromJson(response, JsonObject.class);
		            	// Bitmonerod info variables
		            	String status = gsonresponse.getAsJsonObject("response").getAsJsonObject("status").getAsString();
		            	int	blockheight = gsonresponse.getAsJsonObject("response").getAsJsonObject("block_header").get("height").getAsInt();
		            	int	diff = gsonresponse.getAsJsonObject("response").getAsJsonObject("block_header").get("difficulty").getAsInt();
		            	float blockreward = gsonresponse.getAsJsonObject("response").getAsJsonObject("block_header").get("reward").getAsFloat();
		            	int	timestamp = gsonresponse.getAsJsonObject("response").getAsJsonObject("block_header").get("timestamp").getAsInt();
		            	daemonresponse = new DaemonResponse(status, blockheight, diff, timestamp, blockreward);
		        }
		 
		        public void failed(Throwable t) {
		                response = "failed";
		                //do stuff here based on the failed attempt
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
				}
		 });
		if (daemonresponse.status == "OK") {return daemonresponse;}
		else {return null;}
	}
}
