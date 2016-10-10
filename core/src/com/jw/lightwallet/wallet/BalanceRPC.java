package com.jw.lightwallet.wallet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.google.gson.Gson;
import com.jw.lightwallet.utils.BalanceValues;
import com.jw.lightwallet.LightWallet;



public class BalanceRPC {
	LightWallet		game;
	public String 	swaddress;
	String			response;
	String			getbalance;
	
	public BalanceRPC (LightWallet game) {
		this.game = game;
		swaddress = "http://localhost:19091/json_rpc";
		getbalance = "{\"jsonrpc\":\"2.0\",\"id\":\"test\",\"method\":\"getbalance\",\"params\":[]}";
	}
	
	public void getinfo (final BalanceValues balancevalues) {
		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl(swaddress);
		httpGet.setContent(getbalance);
		httpGet.setHeader("User-Agent", game.walletvalues.getUserAgent());

		Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		                response = httpResponse.getResultAsString();
//		                Gdx.app.log(LightWallet.LOG, "Checked: " + balancevalues.getChecked());
//		                Gdx.app.log(LightWallet.LOG, "Balance is: " + response);
//		                BalanceResponse balanceresponse = new Gson().fromJson(response, BalanceResponse.class);
//		                balancevalues.setBalance(balanceresponse.getResult().getBalance());
//		                balancevalues.setUnlockedbalance(balanceresponse.getResult().getUnlockedbalance());
//		                Gdx.app.log(LightWallet.LOG, response.split("\"balance\": ")[1].split(",")[0]);
		                balancevalues.setBalance(Long.parseLong(response.split("\"balance\": ")[1].split(",")[0]));
//		                Gdx.app.log(LightWallet.LOG, response.split("unlocked_balance\": ")[1].split("}")[0].replaceAll("\\s+",""));
		                balancevalues.setUnlockedbalance(Long.parseLong(response.split("unlocked_balance\": ")[1].split("}")[0].replaceAll("\\s+","")));
		                Gdx.app.log(LightWallet.LOG, "Setting checked true.");
		                balancevalues.setChecked(true);
		        }
		 
		        public void failed(Throwable t) {
		                response = "failed";
		                Gdx.app.log(LightWallet.LOG, "Failed - setting checked false.");
		                Gdx.app.log(LightWallet.LOG, t.getMessage());
		                balancevalues.setChecked(false);
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
				}
		 });
	}
}
