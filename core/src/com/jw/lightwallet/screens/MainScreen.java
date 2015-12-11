package com.jw.lightwallet.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.jw.lightwallet.LightWallet;
import com.jw.lightwallet.daemon.DaemonRPC;
import com.jw.lightwallet.utils.DaemonValues;

public class MainScreen extends AbstractScreen {
	// Interface classes
	DaemonRPC		daemonrpc;
	DaemonValues	daemonvalues;
	// Layout stuff
	Table			screenlayout;
	TextButton		daemonbutton;
	TextButton		walletbutton;
	// Daemon layout
	Table			daemonlayout;
	Image			logo;
	Label			statuslabel;
	Label			statusvalue;
	Label			blockheightlabel;
	Label			blockheightvalue;
	Label			difflabel;
	Label			diffvalue;
	Label			hashratelabel;
	Label			hashratevalue;
	Label			lastblocktimelabel;
	Label			lastblocktimevalue;
	Label			rewardlabel;
	Label			rewardvalue;
	// Wallet layout
	// Transfer layout
	// Tx history layout
	// Timer
	Timer			timer;

	public MainScreen(LightWallet game) {
		super(game);
		timer = new Timer();
		daemonrpc = new DaemonRPC();
		daemonvalues = new DaemonValues();
		daemonrpc.getinfo(daemonvalues);

	}
	
	public void show() {
		super.show();
		
		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				System.out.print(daemonvalues.getBlockheight());
			}
		}, 5f, 5f);
		
	}
	
	public void render(float delta) {
		super.render(delta);
		
	}
	
	public void dispose() {
		
	}
	

}
