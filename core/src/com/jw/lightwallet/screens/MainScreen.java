package com.jw.lightwallet.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.AtomicQueue;
import com.badlogic.gdx.utils.Timer;
import com.jw.lightwallet.LightWallet;
import com.jw.lightwallet.daemon.DaemonRPC;
import com.jw.lightwallet.utils.BalanceValues;
import com.jw.lightwallet.utils.Constants;
import com.jw.lightwallet.utils.DaemonValues;
import com.jw.lightwallet.wallet.BalanceRPC;


public class MainScreen extends AbstractScreen {
	// Interface classes
	DaemonRPC			daemonrpc;
	DaemonValues		daemonvalues;
	
	BalanceRPC			balancerpc;
	BalanceValues		balancevalues;
	
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
	TransactionView		transactionview;
	HistoryView			historyview;

	// Timers
	Timer				daemontimer;
	Timer				wallettimer;
	
	// Wallet thread and queue stuff
	Process				wp;
	BufferedReader		wr;
	AtomicQueue<String>	wq;
	

	public MainScreen(final LightWallet game) {
		super(game);
		
		daemontimer 	= new Timer();
		wallettimer		= new Timer();
		
		wq 				= new AtomicQueue<String>(10);
				
		Skin uiSkin 	= new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		daemonrpc 		= new DaemonRPC();
		daemonvalues 	= new DaemonValues();
		
		balancerpc		= new BalanceRPC();
		balancevalues	= new BalanceValues();

		stage 			= new Stage();
		Gdx.input.setInputProcessor(stage);
		
		screenlayout 	= new Table();
		viewcontainer 	= new Table();
		
		logo 			= new Image(new Texture(Gdx.files.internal("logo.png")));

		buttonrow		= new Table();
		daemonbutton 	= new TextButton("Daemon", uiSkin);
		daemonbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            System.out.println("Daemon button Pressed");
	            viewcontainer.removeActor(walletview.walletlayout);
	            viewcontainer.removeActor(transactionview.txlayout);
	            viewcontainer.add(daemonview.daemonlayout).expand().bottom();
	        }
	    });
		walletbutton 	= new TextButton("Wallet", uiSkin);
		walletbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            System.out.println("Wallet button Pressed");
	            viewcontainer.removeActor(daemonview.daemonlayout);
	            viewcontainer.removeActor(transactionview.txlayout);
	            viewcontainer.add(walletview.walletlayout).expand().bottom();
	        }
	    });		
		transferbutton 	= new TextButton("Transfer", uiSkin);
		transferbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            System.out.println("Transfer button Pressed");
	            viewcontainer.removeActor(daemonview.daemonlayout);
	            viewcontainer.removeActor(walletview.walletlayout);
	            viewcontainer.add(transactionview.txlayout).expand().bottom();
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

		daemonview 		= new DaemonView();
		walletview 		= new WalletView(game);
		transactionview	= new TransactionView();
		historyview		= new HistoryView();
		viewcontainer.add(daemonview.daemonlayout).expand().bottom();
		screenlayout.add(viewcontainer);
		
		screenlayout.setFillParent(true);
		stage.addActor(screenlayout);
	}
	
	public void show() {
		super.show();
		
		new Thread(new Runnable() {
			   @Override
			   public void run() {
			      // do something important here, asynchronously to the rendering thread
				   try {
					Gdx.app.log(LightWallet.LOG, "Command is: " + "simplewallet --wallet-file " + game.walletvalues.getName() 
							   + " --password " + game.walletvalues.getPw() + " --daemon-address " + game.walletvalues.getNode()
							   + " --rpc-bind-port 19091 --log-level 2");
					wp = Runtime.getRuntime().exec("simplewallet --wallet-file " + game.walletvalues.getName() 
							   + " --password " + game.walletvalues.getPw() + " --daemon-address " + game.walletvalues.getNode()
							   + " --rpc-bind-port 19091 --log-level 2");
					wr = new BufferedReader(new InputStreamReader(wp.getInputStream()));
					while (true) {
						String str = wr.readLine();
						wq.put(str);
					}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			      // post a Runnable to the rendering thread that processes the result
			      Gdx.app.postRunnable(new Runnable() {
			         @Override
			         public void run() {
			            // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
			            
			         }
			      });
			   }
			}).start();
		
		// Timer task to get network info from daemon
		daemontimer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				daemonrpc.getinfo(daemonvalues);
				daemonview.Update(daemonvalues);
			}
		}, 1f, 10f);
		
		// Timer task to get output from simplewallet logging and check balances
		wallettimer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				String queuepoll = wq.poll();
				Gdx.app.log(LightWallet.LOG, "Queue result: " + queuepoll);
				try{
					
					if (queuepoll != null && queuepoll.contains("height")) {
						String height = queuepoll.split("height: ")[1].split(",")[0];
						walletview.syncvalue.setText(height + " / " + daemonvalues.getBlockheight());
					}
				} catch (NullPointerException e){e.printStackTrace();}
				
				balancerpc.getinfo(balancevalues);
				
			}
		}, 1f, 1f);
		
	}
	
	public void render(float delta) {
		super.render(delta);

		stage.draw();
		stage.act(delta);
		
	}
	
	public void dispose() {
		super.dispose();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		super.hide();
		wp.destroy();
		
	}
	
	
	

}
