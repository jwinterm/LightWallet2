package com.jw.lightwallet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jw.lightwallet.LightWallet;

public class WalletView {

	Table		walletlayout;
	
	Label		namelabel;
	Label		namevalue;	
	TextButton	addresslabel;
	Label		addressvalue;
	Label		synclabel;
	Label		syncvalue;
	Label		unlockedlabel;
	Label		unlockedvalue;
	Label		lockedlabel;
	Label		lockedvalue;	
	TextButton	savewalletbtn;
	Label		savewalletlabel;
	
	int 		padvalue;


	public WalletView (final LightWallet game) {
		
		Skin uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		walletlayout 		= new Table();
		padvalue			= 16;

		namelabel 			= new Label("Name: ", uiSkin);
		namevalue			= new Label(game.walletvalues.getName(), uiSkin);
		walletlayout.add(namelabel).pad(padvalue);
		walletlayout.add(namevalue).pad(padvalue).row();
		
		addresslabel 		= new TextButton("Address (click to copy): ", uiSkin);
		addressvalue		= new Label(game.walletvalues.getAddress(), uiSkin);
		addressvalue.setFontScale(0.6f, 0.6f);
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
		
		savewalletbtn		= new TextButton("Save wallet\n(will attempt autosave every 60 s)", uiSkin);
		savewalletlabel		= new Label("Not saved :(", uiSkin, "redlabel");
		walletlayout.add(savewalletbtn);
		walletlayout.add(savewalletlabel);
		
		addresslabel.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	Gdx.app.getClipboard().setContents(game.walletvalues.getAddress());
	        }
	    });	
		
	}
}
