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
		padvalue			= 15;

		addresslabel 			= new Label("Send to address: ", uiSkin);
		addressvalue			= new TextField("", uiSkin);
		txlayout.add(addresslabel).pad(padvalue);
		txlayout.add(addressvalue).pad(padvalue).row();
		
		amountlabel 			= new Label("Send amount XMR: ", uiSkin);
		amountvalue			= new TextField("", uiSkin);
		txlayout.add(amountlabel).pad(padvalue);
		txlayout.add(amountvalue).pad(padvalue).row();
		
		mixinlabel 			= new Label("Mixin level: ", uiSkin);
		mixinvalue			= new TextField("", uiSkin);
		txlayout.add(mixinlabel).pad(padvalue);
		txlayout.add(mixinvalue).pad(padvalue).row();
		
		idlabel 			= new Label("Payment id: ", uiSkin);
		idvalue			= new TextField("", uiSkin);
		txlayout.add(idlabel).pad(padvalue);
		txlayout.add(idvalue).pad(padvalue).row();

		
	}

}
