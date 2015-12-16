package com.jw.lightwallet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.jw.lightwallet.LightWallet;
import com.jw.lightwallet.daemon.DaemonRPC;
import com.jw.lightwallet.utils.Constants;
import com.jw.lightwallet.utils.DaemonValues;


public class MainScreen extends AbstractScreen {
	// Interface classes
	DaemonRPC			daemonrpc;
	DaemonValues		daemonvalues;
	
	// Layout stuff
	Stage				stage;
	Table				screenlayout;
	
	Image				logo;
	Texture				logotex;
	
	Table				buttonrow;
	TextButton			daemonbutton;
	TextButton			walletbutton;
	TextButton			transferbutton;
	TextButton			txhistorybutton;
	
	Table				viewcontainer;
	DaemonView			daemonview;
	WalletView			walletview;

	// Timer
	Timer			timer;

	public MainScreen(final LightWallet game) {
		super(game);
		
		timer = new Timer();
		
		Skin uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		daemonrpc = new DaemonRPC();
		daemonvalues = new DaemonValues();

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		screenlayout = new Table();
		viewcontainer = new Table();
		
		logo 			= new Image(new Texture(Gdx.files.internal("logo.png")));

		buttonrow		= new Table();
		daemonbutton = new TextButton("Daemon", uiSkin);
		daemonbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            System.out.println("Daemon button Pressed");
	            viewcontainer.removeActor(walletview.walletlayout);
	            viewcontainer.add(daemonview.daemonlayout).expand().bottom();
	        }
	    });
		walletbutton = new TextButton("Wallet", uiSkin);
		walletbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            System.out.println("Wallet button Pressed");
	            viewcontainer.removeActor(daemonview.daemonlayout);
	            viewcontainer.add(walletview.walletlayout).expand().bottom();
	        }
	    });		
		transferbutton = new TextButton("Transfer", uiSkin);
		transferbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            System.out.println("Transfer button Pressed");
	        }
	    });		
		txhistorybutton = new TextButton("History", uiSkin);
		txhistorybutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            System.out.println("History button Pressed");
	        }
	    });		
		
		buttonrow.add(daemonbutton).width(Constants.WORLD_WIDTH/4);
		buttonrow.add(walletbutton).width(Constants.WORLD_WIDTH/4);
		buttonrow.add(transferbutton).width(Constants.WORLD_WIDTH/4);
		buttonrow.add(txhistorybutton).width(Constants.WORLD_WIDTH/4);
		screenlayout.add(buttonrow).row();
		
		screenlayout.add(logo).pad(10).center().row();

		daemonview = new DaemonView();
		walletview = new WalletView();
		viewcontainer.add(daemonview.daemonlayout).expand().bottom();
		screenlayout.add(viewcontainer);
		
		screenlayout.setFillParent(true);
		stage.addActor(screenlayout);
	}
	
	public void show() {
		super.show();
		
		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				daemonrpc.getinfo(daemonvalues);
				daemonview.Update(daemonvalues);
			}
		}, 1f, 5f);
		
	}
	
	public void render(float delta) {
		super.render(delta);

		stage.draw();
		stage.act(delta);
		
	}
	
	public void dispose() {
		super.dispose();
		
	}
	

}
