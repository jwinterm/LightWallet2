package com.jw.lightwallet.wallet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.google.gson.Gson;
import com.jw.lightwallet.utils.BalanceValues;


public class BalanceRPC {
	public String 	swaddress;
	String			response;
	String			getbalance;
	
	public BalanceRPC () {
		swaddress = "http://localhost:19091/json_rpc";
		getbalance = "{\"jsonrpc\":\"2.0\",\"id\":\"test\",\"method\":\"getbalance\",\"params\":[]}";
	}
	
	public void getinfo (final BalanceValues balancevalues) {
		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl(swaddress);
		httpGet.setContent(getbalance);		

		Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		                response = httpResponse.getResultAsString();
		                BalanceResponse balanceresponse = new Gson().fromJson(response, BalanceResponse.class);
		                balancevalues.setBalance(balanceresponse.getResult().getBalance());
		                balancevalues.setUnlockedbalance(balanceresponse.getResult().getUnlockedbalance());
		                balancevalues.setChecked(true);
		        }
		 
		        public void failed(Throwable t) {
		                response = "failed";
		                balancevalues.setChecked(false);
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
				}
		 });
	}
}
