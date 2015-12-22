package com.jw.lightwallet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jw.lightwallet.LightWallet;

public class WalletView {

	Table		walletlayout;
	
	Label		namelabel;
	Label		namevalue;	
	Label		addresslabel;
	Label		addressvalue;
	Label		synclabel;
	Label		syncvalue;
	Label		unlockedlabel;
	Label		unlockedvalue;
	Label		lockedlabel;
	Label		lockedvalue;		
	
	int 		padvalue;


	public WalletView (LightWallet game) {
		
		Skin uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		walletlayout 		= new Table();
		padvalue			= 15;

		namelabel 			= new Label("Name: ", uiSkin);
		namevalue			= new Label(game.walletvalues.getName(), uiSkin);
		walletlayout.add(namelabel).pad(padvalue);
		walletlayout.add(namevalue).pad(padvalue).row();
		
		addresslabel 		= new Label("Address: ", uiSkin);
		addressvalue		= new Label(game.walletvalues.getAddress(), uiSkin);
		addressvalue.setFontScale(0.2f, 0.2f);
		walletlayout.add(addresslabel).pad(padvalue);
		walletlayout.add(addressvalue).pad(padvalue).row();
		
		synclabel 			= new Label("Wallet sync: ", uiSkin);
		syncvalue			= new Label("0 / A lot", uiSkin, "redlabel");
		walletlayout.add(synclabel).pad(padvalue);
		walletlayout.add(syncvalue).pad(padvalue).row();
		
		unlockedlabel 		= new Label("Unlocked balance: ", uiSkin);
		unlockedvalue		= new Label("Loading...", uiSkin, "redlabel");
		walletlayout.add(unlockedlabel).pad(padvalue);
		walletlayout.add(unlockedvalue).pad(padvalue).row();
		
		lockedlabel 		= new Label("Locked balance: ", uiSkin);
		lockedvalue			= new Label("Loading...", uiSkin, "redlabel");
		walletlayout.add(lockedlabel).pad(padvalue);
		walletlayout.add(lockedvalue).pad(padvalue).row();
		
	}
}
