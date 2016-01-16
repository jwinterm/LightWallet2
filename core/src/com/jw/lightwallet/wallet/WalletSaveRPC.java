package com.jw.lightwallet.wallet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.google.gson.Gson;
import com.jw.lightwallet.utils.WalletSaveValues;


public class WalletSaveRPC {
	public String 	swaddress;
	String			response;
	String			store;
	
	public WalletSaveRPC () {
		swaddress = "http://localhost:19091/json_rpc";
		store = "{\"jsonrpc\":\"2.0\",\"id\":\"test\",\"method\":\"store\",\"params\":[]}";
	}
	
	public void trysave (final WalletSaveValues walletsavevalues) {
		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl(swaddress);
		httpGet.setContent(store);		

		Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		                response = httpResponse.getResultAsString();
		                //System.out.print("Balance response: " + response);


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
