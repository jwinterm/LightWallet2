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
	// Daemon layout
	Table			daemonlayout;

	public MainScreen(LightWallet game) {
		super(game);
		
		daemonrpc = new DaemonRPC();
		daemonrpc.getinfo();

	}
	
	public void show() {
		super.show();
		
	}
	
	public void render(float delta) {
		super.render(delta);
		
	}
	
	public void dispose() {
		
	}

}
