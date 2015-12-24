package com.jw.lightwallet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.jw.lightwallet.LightWallet;

public class TransactionView {
	
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


	public TransactionView () {
		
		Skin uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		txlayout 		= new Table();
		padvalue		= 15;

		addresslabel 	= new Label("Send to address: ", uiSkin);
		addressvalue	= new TextField("", uiSkin, "tinytext");
		txlayout.add(addresslabel).pad(padvalue).width(100);
		txlayout.add(addressvalue).pad(padvalue).width(600).row();
		
		amountlabel 	= new Label("Send amount XMR: ", uiSkin);
		amountvalue		= new TextField("", uiSkin);
		txlayout.add(amountlabel).pad(padvalue).width(100);
		txlayout.add(amountvalue).pad(padvalue).width(100).row();
		
		mixinlabel 		= new Label("Mixin level: ", uiSkin);
		mixinvalue		= new TextField("", uiSkin);
		txlayout.add(mixinlabel).pad(padvalue).width(100);
		txlayout.add(mixinvalue).pad(padvalue).width(50).row();
		
		idlabel 		= new Label("Payment id: ", uiSkin);
		idvalue			= new TextField("", uiSkin, "tinytext");
		txlayout.add(idlabel).pad(padvalue).width(100);
		txlayout.add(idvalue).pad(padvalue).width(600).row();
		
		sendbtn			= new TextButton("Payola!", uiSkin);
		txlayout.add(sendbtn).row();

		
	}

}
