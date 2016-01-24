package com.jw.lightwallet.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jw.lightwallet.LightWallet;

public class HistoryView {
	
	LightWallet	game;
	
	Table			historylayout;
	
	ScrollPane		scroller;
	Table			container;
	
	int 			padvalue;

	
	public HistoryView (LightWallet game) {	
		this.game		= game;
				
		historylayout	= new Table();
		padvalue		= 15;
		
		container		= new Table();
		scroller		= new ScrollPane(container, game.uiSkin);
		
		
		historylayout.add(new Label("Transaction history (click to copy txid)", game.uiSkin)).row();
		historylayout.add(new Label("Type | Amount | Transaction ID", game.uiSkin)).row();
		historylayout.add(scroller);

		
	}


	public Table getContainer() {
		return container;
	}


	public void setContainer(Table container) {
		this.container = container;
	}



}
