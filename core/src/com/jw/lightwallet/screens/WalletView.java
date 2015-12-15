package com.jw.lightwallet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class WalletView {

	Table		walletlayout;
	
	Label		addresslabel;
	Label		addressvalue;
	Label		unlockedlabel;
	Label		unlockedvalue;
	Label		lockedlabel;
	Label		lockedvalue;		
	
	int 		padvalue;


	public WalletView () {
		
		Skin uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		walletlayout 	= new Table();
		padvalue		= 15;

		addresslabel 		= new Label("Address: ", uiSkin);
		addressvalue		= new Label("assssssssssssssddddddddddddddddddddddddddddssssssssssssssssssssssssssssssssffffffffffffffffd", uiSkin);
		walletlayout.add(addresslabel).pad(padvalue);
		walletlayout.add(addressvalue).pad(padvalue).row();
		
	}
}
