package com.jw.lightwallet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HistoryView {
	
	Table			historylayout;
	
	ScrollPane		scroller;
	Table			container;
	
	int 			padvalue;

	
	public HistoryView () {
		
		Skin uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		historylayout	= new Table();
		padvalue		= 15;
		
		container		= new Table();
		scroller		= new ScrollPane(container, uiSkin);
		
		
		historylayout.add(new Label("Transaction history (click to copy txid)", uiSkin)).row();
		historylayout.add(new Label("Type | Amount | Transaction ID", uiSkin)).row();
		historylayout.add(scroller).fill();

		
	}


	public Table getContainer() {
		return container;
	}


	public void setContainer(Table container) {
		this.container = container;
	}



}
