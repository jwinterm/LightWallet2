package com.jw.lightwallet.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.jw.lightwallet.LightWallet;

public class TransactionView {
	
	LightWallet	game;
	
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


	public TransactionView (LightWallet game) {
		this.game		= game;
				
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
		
		mixinlabel 		= new Label("Mixin level: ", game.uiSkin);
		mixinvalue		= new TextField("", game.uiSkin);
		txlayout.add(mixinlabel).pad(padvalue).width(100);
		txlayout.add(mixinvalue).pad(padvalue).width(50).row();
		
		idlabel 		= new Label("Payment id: ", game.uiSkin);
		idvalue			= new TextField("", game.uiSkin, "tinytext");
		txlayout.add(idlabel).pad(padvalue).width(100);
		txlayout.add(idvalue).pad(padvalue).width(600).row();
		
		sendbtn			= new TextButton("Payola!", game.uiSkin);
		txlayout.add(new Label("", game.uiSkin));
		txlayout.add(sendbtn);

		
	}

}
