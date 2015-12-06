package com.jw.lightwallet.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jw.lightwallet.LightWallet;
import com.jw.lightwallet.utils.DaemonRPC;

public class MainScreen extends AbstractScreen {
	// Interface classes
	DaemonRPC		daemonrpc;
	
	// Layout stuff
	Table			screenlayout;
	TextButton		daemonbutton;
	TextButton		walletbutton;
	Table			daemonlayout;
	
	// Bitmonerod info variables
	int				blockheight;
	int				diff;
	int				hashrate;
	float			blockreward;

	public MainScreen(LightWallet game) {
		super(game);
		
		daemonrpc = new DaemonRPC();

	}
	
	public void show() {
		super.show();
		
	}

}
