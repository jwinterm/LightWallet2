package com.jw.lightwallet.wallet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.jw.lightwallet.LightWallet;


public class WalletSaveRPC {
	LightWallet		game;
	public String 	swaddress;
	String			response;
	String			store;
	
	public WalletSaveRPC (LightWallet game) {
		this.game = game;
		swaddress = "http://localhost:19091/json_rpc";
		store = "{\"jsonrpc\":\"2.0\",\"id\":\"test\",\"method\":\"store\",\"params\":[]}";
//		store = "{\"jsonrpc\":\"2.0\",\"id\":\"test\",\"user-agent\":" + game.walletvalues.getUserAgent() + ",\"method\":\"store\",\"params\":[]}";
	}
	
	public void trysave (final Label savestatuslabel) {
		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl(swaddress);
		httpGet.setContent(store);
		httpGet.setHeader("User-Agent", game.walletvalues.getUserAgent());

		Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
		        public void handleHttpResponse(HttpResponse httpResponse) {
		                response = httpResponse.getResultAsString();
//		                System.out.print("Save response: " + response);
		                savestatuslabel.setText("Successfully saved");
		                savestatuslabel.setStyle(game.uiSkin.get("greenlabel", LabelStyle.class));
		        }
		 
		        public void failed(Throwable t) {
		                response = "failed";
		                savestatuslabel.setText("Save failed");
		                savestatuslabel.setStyle(game.uiSkin.get("redlabel", LabelStyle.class));
		        }

				@Override
				public void cancelled() {
					// TODO Auto-generated method stub
				}
		 });
	}
}
