 package com.jw.lightwallet.daemon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.google.gson.Gson;
import com.jw.lightwallet.daemon.DaemonResponse;
import com.jw.lightwallet.utils.DaemonValues;

public class DaemonRPC {
	public String 	address;
	String			response;
	String			getlastblock;
	
	public DaemonRPC () {
		address = "http://node.moneroclub.com:8880/json_rpc";
		getlastblock = "{\"jsonrpc\":\"2.0\",\"id\":\"test\",\"method\":\"getlastblockheader\",\"params\":[]}";
	}
	
	public void getinfo (final DaemonValues daemonvalues) {
		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl(address);
		httpGet.setContent(getlastblock);		

		Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		                response = httpResponse.getResultAsString();
		                //System.out.print("1st time response" + response);
		                DaemonResponse daemonresponse = new Gson().fromJson(response, DaemonResponse.class);
		                //System.out.print("GSON response is" + daemonresponse.getId());
		                daemonvalues.setBlockheight(daemonresponse.getResult().getBlock_header().getHeight());
		                daemonvalues.setDiff(daemonresponse.getResult().getBlock_header().getDifficulty());
		                daemonvalues.setLastblocktime(daemonresponse.getResult().getBlock_header().getTimestamp());
		                daemonvalues.setLastblockreward(daemonresponse.getResult().getBlock_header().getReward());
		                daemonvalues.setStatus(daemonresponse.getResult().getStatus());
		        }
		 
		        public void failed(Throwable t) {
		                response = "failed";
		                //System.out.print("Failed response status is: " + response);
		                //do stuff here based on the failed attempt
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
				}
		 });
	}
}
