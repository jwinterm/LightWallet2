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
	Gson 			gson;
	DaemonResponse  daemonresponse = new DaemonResponse();
	
	public DaemonRPC () {
		// address = "http://node.moneroclub.com:8880/json_rpc";
		// address = "http://localhost:18081/json_rpc";
		getlastblock = "{\"jsonrpc\":\"2.0\",\"id\":\"test\",\"method\":\"getlastblockheader\",\"params\":[]}";
	}
	
	public void getinfo (final DaemonValues daemonvalues, String nodeaddress) {
		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl(nodeaddress + "/json_rpc");
		httpGet.setContent(getlastblock);		

		Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		                response = httpResponse.getResultAsString();
//		                System.out.println("1st time response" + response);
		                daemonvalues.setBlockheight(Integer.parseInt(response.split("height\": ")[1].split(",")[0]));
		                daemonvalues.setDiff(Long.parseLong(response.split("difficulty\": ")[1].split(",")[0]));
		                daemonvalues.setLastblocktime(Long.parseLong(response.split("difficulty\": ")[1].split(",")[0]));
		                daemonvalues.setLastblockreward(Long.parseLong(response.split("reward\": ")[1].split(",")[0]));
		                daemonvalues.setStatus("OK");
		        }
		 
		        public void failed(Throwable t) {
		                response = "failed";
		                System.out.print("Failed response status is: " + response);
		                //do stuff here based on the failed attempt
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
				}
		 });
	}
}
