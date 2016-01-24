package com.jw.lightwallet.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jw.lightwallet.LightWallet;

public class TransactionView {
	
	LightWallet	game;
	Stage		stage;
	
	Table		txlayout;
	
	Label		addresslabel;
	TextField	addressvalue;
	Label		amountlabel;
	TextField	amountvalue;
	Label		mixinlabel;
	TextField	mixinvalue;
	Label		idlabel;
	TextField	idvalue;
	
	TextButton	sendbtn;
	
	int 		padvalue;


	public TransactionView (LightWallet game, final Stage stage) {
		this.game		= game;
		this.stage		= stage;
				
		txlayout 		= new Table();
		padvalue		= 15;

		addresslabel 	= new Label("Send to address: ", game.uiSkin);
		addressvalue	= new TextField("", game.uiSkin, "tinytext");
		txlayout.add(addresslabel).pad(padvalue).width(100);
		txlayout.add(addressvalue).pad(padvalue).width(600).row();
		
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
		txlayout.add(sendbtn);
		
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
		
		sendbtn.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	// Setup flags to check each tx input
	        	boolean isAddressGood = false;
	        	boolean isAmountGood = false;
	        	boolean isMixinGood = false;
	        	boolean isIdGood = false;
	        	
	        	String address = addressvalue.getText();
	        	if (address.length() == 95) {
	        		if (address.substring(0, 1).equals('4')) {
	        			isAddressGood = true;
	        		}
	        	} else {
	        		badaddress.show(stage);
	        	}
	        	
	        	long amount = 0;
	        	try {
	        		amount = (long) ((long) Double.parseDouble(amountvalue.getText())*1e12);
	        		} catch (java.lang.NumberFormatException e) {
	        			System.out.print("No amount :(");
	        		}
	        	
	        	int mixin = 4;
	        	try {
	        		mixin = Integer.parseInt(mixinvalue.getText());
	        		} catch (java.lang.NumberFormatException e) {
	        			System.out.print("No mixin :(");
	        		}
	        	
	        	String id = idvalue.getText();
	        	
	        	System.out.print(address + "\n" + amount + "\n" + mixin + "\n" + id);
	        }
	    });	
		
	}

}
