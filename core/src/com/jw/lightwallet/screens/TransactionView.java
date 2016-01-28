package com.jw.lightwallet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.gson.Gson;
import com.jw.lightwallet.LightWallet;
import com.jw.lightwallet.daemon.DaemonResponse;
import com.jw.lightwallet.tx.TxResponse;
import com.jw.lightwallet.utils.BalanceValues;

public class TransactionView {
	
	LightWallet		game;
	Stage			stage;
	BalanceValues 	balancevalues;
	
	Table			txlayout;
	
	Label			addresslabel;
	TextField		addressvalue;
	Label			amountlabel;
	TextField		amountvalue;
	Label			mixinlabel;
	TextField		mixinvalue;
	Label			idlabel;
	TextField		idvalue;
	
	TextButton		sendbtn;
	String			response;
	String			failresponse;
	
	int 			padvalue;


	public TransactionView (final LightWallet game, final Stage stage, final BalanceValues balancevalues) {
		this.game		= game;
		this.stage		= stage;
		this.balancevalues = balancevalues;
				
		txlayout 		= new Table();
		padvalue		= 15;

		addresslabel 	= new Label("Send to\naddress: ", game.uiSkin);
		addressvalue	= new TextField("", game.uiSkin, "tinytext");
		txlayout.add(addresslabel).pad(padvalue).width(60);
		txlayout.add(addressvalue).pad(padvalue).width(650).row();
		
		amountlabel 	= new Label("Send amount XMR: ", game.uiSkin);
		amountvalue		= new TextField("", game.uiSkin);
		txlayout.add(amountlabel).pad(padvalue).width(100);
		txlayout.add(amountvalue).pad(padvalue).width(100).row();
		
		mixinlabel 		= new Label("Mixin level (default 4, minimum 2): ", game.uiSkin);
		mixinvalue		= new TextField("4", game.uiSkin);
		txlayout.add(mixinlabel).pad(padvalue).width(100);
		txlayout.add(mixinvalue).pad(padvalue).width(50).row();
		
		idlabel 		= new Label("Payment id: ", game.uiSkin);
		idvalue			= new TextField("", game.uiSkin, "tinytext");
		txlayout.add(idlabel).pad(padvalue).width(100);
		txlayout.add(idvalue).pad(padvalue).width(600).row();
		
		sendbtn			= new TextButton("Payola!", game.uiSkin);
		txlayout.add(new Label("", game.uiSkin));
		txlayout.add(sendbtn).width(400);
		
		// Setup popup for bad addresses
	    final Dialog badaddress = new Dialog("Bad address!", game.uiSkin, "dialog") {
	        public void result(Object obj) {
	            System.out.println("result "+obj);
	        }
	    };
	    Label badaddresslabel = new Label("Monero addresses begin with a '4' and are 95 characters long.", game.uiSkin);
	    badaddresslabel.setWrap(true);
	    badaddress.add(badaddresslabel).width(400).row();
	    badaddress.button("Continue", true).addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            badaddress.remove();
	        }
	    });
	    badaddress.key(Keys.ENTER, true); //sends "true" when the ENTER key is pressed

		// Setup popup for bad amount
	    final Dialog badamount = new Dialog("Bad amount!", game.uiSkin, "dialog") {
	        public void result(Object obj) {
	            System.out.println("result "+obj);
	        }
	    };
	    Label badamountlabel = new Label("Amount must be greater than 0.01 and less than current unlocked balance (minus fee).", game.uiSkin);
	    badamountlabel.setWrap(true);
	    badamount.add(badamountlabel).width(400).row();
	    badamount.button("Continue", true).addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	badamount.remove();
	        }
	    });
	    badamount.key(Keys.ENTER, true); //sends "true" when the ENTER key is pressed
	    
		// Setup popup for bad mixin
	    final Dialog badmixin = new Dialog("Bad mixin!", game.uiSkin, "dialog") {
	        public void result(Object obj) {
	            System.out.println("result "+obj);
	        }
	    };
	    Label badmixinlabel = new Label("Mixin must be an integer between 2 and 99.", game.uiSkin);
	    badmixinlabel.setWrap(true);
	    badmixin.add(badmixinlabel).width(400).row();
	    badmixin.button("Continue", true).addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	badmixin.remove();
	        }
	    });
	    badmixin.key(Keys.ENTER, true); //sends "true" when the ENTER key is pressed

		// Setup popup for bad tx id
	    final Dialog badpayid = new Dialog("Bad payment ID!", game.uiSkin, "dialog") {
	        public void result(Object obj) {
	            System.out.println("result "+obj);
	        }
	    };
	    Label badpayidlabel = new Label("Payment ID must be 64 characters and only contain 0-9 and a-f.", game.uiSkin);
	    badpayidlabel.setWrap(true);
	    badpayid.add(badpayidlabel).width(400).row();
	    badpayid.button("Continue", true).addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	badpayid.remove();
	        }
	    });
	    badpayid.key(Keys.ENTER, true); //sends "true" when the ENTER key is pressed
	    
		sendbtn.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	// Setup flags to check each tx input
	        	boolean isAddressGood = false;
	        	boolean isAmountGood = false;
	        	boolean isMixinGood = false;
	        	boolean isIdGood = false;
	        	
	        	// Check address and make badaddress popup if bad
	        	String address = addressvalue.getText();
	        	if (address.length() == 95) {
	        		if (address.substring(0, 1).equals("4")) {
	        			isAddressGood = true;
	        		} else {
		        		badaddress.show(stage);
		        	}
	        	} else {
	        		badaddress.show(stage);
	        	}
	        	
	        	// Check amount and make badamount popup if amount is non-numeric or too big
	        	long amount = 0;
	        	try {
	        		amount = (long)(Double.parseDouble(amountvalue.getText())*1e12);
	        		} catch (java.lang.NumberFormatException e) {
	        			System.out.print("No amount :(");
	        		}
	        	if (amount > 2e10 && amount < balancevalues.getUnlockedbalance()-1e10) {
	        		System.out.println("Amount is: " + amount + ", and balance is: " + balancevalues.getUnlockedbalance());
	        		isAmountGood = true;
	        	} else {
	        		badamount.show(stage);
	        	}
	        	
	        	int mixin = 4;
	        	try {
	        		mixin = Integer.parseInt(mixinvalue.getText());
	        		} catch (java.lang.NumberFormatException e) {
	        			System.out.print("No mixin :(");
	        		}
	        	if (mixin > 1 && mixin < 100) {
	        		isMixinGood = true;
	        	} else {
	        		badmixin.show(stage);
	        	}
	        	
	        	String id = idvalue.getText();
	        	if (id.length() == 0 || (id.length() == 64 && id.matches("\\p{XDigit}+"))) {
	        		isIdGood = true;
	        	} else {
	        		badpayid.show(stage);
	        	}
	        	
	        	System.out.println(address + "\n" + amount + "\n" + mixin + "\n" + id);
	        	System.out.println(String.format("%d", amount));
	        	System.out.println(isAddressGood + " " + isAmountGood + " " + isMixinGood + " " + isIdGood);
	    		String swaddress = "http://localhost:19091/json_rpc";
	    		String getbalance = String.format("{\"jsonrpc\":\"2.0\",\"id\":\"test\",\"method\":\"transfer\",\"params\":{"
	    				+ "\"destinations\":[{\"amount\":%d,\"address\":\"%s\"}],\"fee\":100000000000,\"mixin\":%d,\"unlock_time\":0,"
	    				+ "\"payment_id\":\"%s\"}", (long)(amount), address, mixin, id);
	    		
	    		if (isAddressGood && isAmountGood && isMixinGood && isIdGood) {
	    			System.out.println("Starting RPC call...");
	    			System.out.println(getbalance);
	    			HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
	    			httpGet.setUrl(swaddress);
	    			httpGet.setContent(getbalance);
	    			httpGet.setTimeOut(2500);
	    			
	    			Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
	    		        public void handleHttpResponse(HttpResponse httpResponse) {
	    		                response = httpResponse.getResultAsString();
	    		                System.out.println("Success response is: " + response);
	    		                TxResponse txresponse = new Gson().fromJson(response, TxResponse.class);
	    		                
	    		                System.out.println(txresponse.getResult().getTx_hash());
	    		                
	    		                String txid = txresponse.getResult().getTx_hash().substring(1, txresponse.getResult().getTx_hash().length()-1);;
    		                	
	    		                System.out.println("Tx id is: " + txid);
	    		                System.out.println("Tx id length is: " + txid.length());

	    		                if (txid.length() == 64) {
	    		            		// Setup popup for successful tx
	    		                	final String finaltxid = txid;
	    		            	    final Dialog goodtxpopup = new Dialog("Succcessful transaction!", game.uiSkin, "dialog") {
	    		            	        public void result(Object obj) {
	    		            	            System.out.println("result "+obj);
	    		            	        }
	    		            	    };
	    		            	    Label goodtxpopuplabel = new Label("Successfully completed tx with tx is:\n" + txid, game.uiSkin);
	    		            	    goodtxpopuplabel.setWrap(true);
	    		            	    goodtxpopup.add(goodtxpopuplabel).width(400).row();
	    		            	    goodtxpopup.button("Copy tx id", true).addListener(new ClickListener() {
	    		            	        @Override
	    		            	        public void clicked (InputEvent event, float x, float y) {
	    		            	        	Gdx.app.getClipboard().setContents(finaltxid);
	    		            	        }
	    		            	    });
	    		            	    goodtxpopup.button("Continue", true).addListener(new ClickListener() {
	    		            	        @Override
	    		            	        public void clicked (InputEvent event, float x, float y) {
	    		            	        	goodtxpopup.remove();
	    		            	        }
	    		            	    });	    		            	    
	    		            	    goodtxpopup.key(Keys.ENTER, true); //sends "true" when the ENTER key is pressed
	    		            	    goodtxpopup.show(stage);
	    		                }	    		                
	    		        }
	    		 
	    		        public void failed(Throwable t) {
	    		                failresponse = "failed";
	    		                System.out.println("Failed response status is: " + failresponse);
	    		                
	    		                final Dialog errorpopup = new Dialog("Failed transaction!", game.uiSkin, "dialog") {
    		            	        public void result(Object obj) {
    		            	            System.out.println("result "+obj);
    		            	        }
    		            	    };
    		            	    Label errorpopuplabel = new Label("Transaction failed, please try again, maybe with a smaller amount.", game.uiSkin);
    		            	    errorpopuplabel.setWrap(true);
    		            	    errorpopup.add(errorpopuplabel).width(400).row();
    		            	    errorpopup.button("Continue", true).addListener(new ClickListener() {
    		            	        @Override
    		            	        public void clicked (InputEvent event, float x, float y) {
    		            	        	errorpopup.remove();
    		            	        }
    		            	    });	    		            	    
    		            	    errorpopup.key(Keys.ENTER, true); //sends "true" when the ENTER key is pressed
    		            	    errorpopup.show(stage);
	    		        }

	    				@Override
	    				public void cancelled() {
	    					// TODO Auto-generated method stub
	    				}
	    		 });
	    		}
	        }
	    });	
		
	}

}
