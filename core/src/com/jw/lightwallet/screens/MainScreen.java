package com.jw.lightwallet.screens;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.AtomicQueue;
import com.jw.lightwallet.LightWallet;
import com.jw.lightwallet.daemon.DaemonRPC;
import com.jw.lightwallet.utils.BalanceValues;
import com.jw.lightwallet.utils.Constants;
import com.jw.lightwallet.utils.DaemonValues;
import com.jw.lightwallet.utils.Tx;
import com.jw.lightwallet.utils.WalletSaveValues;
import com.jw.lightwallet.wallet.BalanceRPC;
import com.jw.lightwallet.wallet.WalletSaveRPC;


public class MainScreen extends AbstractScreen {
	// Interface classes
	DaemonRPC				daemonrpc;
	DaemonValues			daemonvalues;
	
	BalanceRPC				balancerpc;
	BalanceValues			balancevalues;
	
	WalletSaveRPC			walletsaverpc;
	WalletSaveValues		walletsavevalues;
	
	// Layout stuff
	Stage					stage;
	Table					screenlayout;
	
	Image					logo;
	Texture					logotex;
	
	Table					buttonrow;
	TextButton				daemonbutton;
	TextButton				walletbutton;
	TextButton				transferbutton;
	TextButton				txhistorybutton;
	
	Table					viewcontainer;
	DaemonView				daemonview;
	WalletView				walletview;
	TransactionView			transactionview;
	HistoryView				historyview;

	// Timers
	boolean					fivesectimer;
	boolean					tensectimer;
	boolean					sixtysectimer;
	
	// Wallet thread and queue stuff
	Process					wp;
	BufferedReader			wr;
	AtomicQueue<String>		wq;
	
	// Transaction processing stuff
	ArrayList<Tx>			txlist;
	int						txcount;
	double					calcbalance;
	DecimalFormat 			df;
	

	public MainScreen(final LightWallet game) {
		super(game);

		wq 				= new AtomicQueue<String>(1000);
		txlist			= new ArrayList<Tx>();
		txcount			= 0;
		calcbalance		= 0;
		df 				= new DecimalFormat("#.############");
						
		Skin uiSkin 	= new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		daemonrpc 		= new DaemonRPC();
		daemonvalues 	= new DaemonValues();
		
		balancerpc		= new BalanceRPC();
		balancevalues	= new BalanceValues();
		
		walletsaverpc	= new WalletSaveRPC(game);
		walletsavevalues= new WalletSaveValues();

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
	            viewcontainer.removeActor(viewcontainer.getChildren().get(0));
	            viewcontainer.add(daemonview.daemonlayout);
	        }
	    });
		walletbutton 	= new TextButton("Wallet", uiSkin);
		walletbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            viewcontainer.removeActor(viewcontainer.getChildren().get(0));
	            viewcontainer.add(walletview.walletlayout);
	        }
	    });		
		transferbutton 	= new TextButton("Transfer", uiSkin);
		transferbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            viewcontainer.removeActor(viewcontainer.getChildren().get(0));
	            viewcontainer.add(transactionview.txlayout);
	        }
	    });		
		txhistorybutton = new TextButton("History", uiSkin);
		txhistorybutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            viewcontainer.removeActor(viewcontainer.getChildren().get(0));
	            viewcontainer.add(historyview.historylayout);
	        }
	    });		
		
		buttonrow.add(daemonbutton).width(Constants.WORLD_WIDTH/4);
		buttonrow.add(walletbutton).width(Constants.WORLD_WIDTH/4);
		buttonrow.add(transferbutton).width(Constants.WORLD_WIDTH/4);
		buttonrow.add(txhistorybutton).width(Constants.WORLD_WIDTH/4);
		screenlayout.add(buttonrow).top().row();
		
		screenlayout.add(logo).pad(5).center().row();

		daemonview 		= new DaemonView(game);
		walletview 		= new WalletView(game, walletsaverpc);
		transactionview	= new TransactionView(game, stage, balancevalues);
		historyview		= new HistoryView(game);
		viewcontainer.add(daemonview.daemonlayout).expand().fill();
		screenlayout.add(viewcontainer);
		
		// viewcontainer.setFillParent(true);
		screenlayout.setFillParent(true);
		screenlayout.top();
		// screenlayout.setDebug(true);
		stage.addActor(screenlayout);
		
		// Check tx file
		dumptxlist();
	}
	
	public void show() {
		super.show();
		launchsimplewallet();
	}
	
	
	public void render(float delta) {
		super.render(delta);

		stage.draw();
		stage.act(delta);
		
		updatetimers(delta);
		runtasks();
		update();

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
	
	public void updatetimers(float delta) {
		if (accum5s > 5) { fivesectimer = true; }
		if (accum10s > 10) { tensectimer = true; }
		if (accum60s > 60) { sixtysectimer = true; }
	}
	
	public void runtasks() {
		// Read simplewallet queue to check for transactions
		readwalletqueue();
		if (fivesectimer == true) {
			// Timer task to check balance from simplewallet
			balancerpc.getinfo(balancevalues);
			walletview.lockedvalue.setText(df.format(balancevalues.getLockedbalance()/1e12) + " XMR");
			walletview.unlockedvalue.setText(df.format(balancevalues.getUnlockedbalance()/1e12) + " XMR");
			if (balancevalues.isChecked()) {
				walletview.lockedvalue.setStyle(game.uiSkin.get("greenlabel", LabelStyle.class));
				walletview.unlockedvalue.setStyle(game.uiSkin.get("greenlabel", LabelStyle.class));
			} else {
				walletview.lockedvalue.setStyle(game.uiSkin.get("redlabel", LabelStyle.class));
				walletview.unlockedvalue.setStyle(game.uiSkin.get("redlabel", LabelStyle.class));
			}
			fivesectimer = false;
			accum5s = 0;
		}
		if (tensectimer == true) {
			// Timer task to get network info from daemon
			daemonrpc.getinfo(daemonvalues, game.walletvalues.getNode());
			daemonview.Update(daemonvalues);
			tensectimer = false;
			accum10s = 0;
		}
		if (sixtysectimer == true) {
			// Timer task to try and save wallet every minute
			walletsaverpc.trysave(walletview.getSavewalletlabel());
			dumptxlist();
			sixtysectimer = false;
			accum60s = 0;
		}
	}
	
	public void update() {
		
	}
	
	// Launch simplewallet in its own thread
	public void launchsimplewallet() {
		new Thread(new Runnable() {
			   @Override
			   public void run() {
				   // Setup simplewallet command with the specified file, password, and node address
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
						// Gdx.app.log(LightWallet.LOG, str);
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
	}
	
	public void readwalletqueue () {
		// Poll wallet thread and check block height and if transaction data is in output
		String queuepoll = wq.poll();
		try{
			// If the string contains "height" then update the daemon and wallet screen blockheight
			if (queuepoll != null && queuepoll.contains("height:")) {
				String height = queuepoll.split("height: ")[1].split(",")[0];
				walletview.syncvalue.setText(height + " / " + daemonvalues.getBlockheight());
				if (height.equals(Integer.toString(daemonvalues.getBlockheight()))) {
					walletview.syncvalue.setStyle(game.uiSkin.get("greenlabel", LabelStyle.class));
				} else {walletview.syncvalue.setStyle(game.uiSkin.get("default", LabelStyle.class));}
			}
			else if (queuepoll != null && queuepoll.contains("height ")) {
				String height = queuepoll.split("height ")[1].split(",")[0];
				walletview.syncvalue.setText(height + " / " + daemonvalues.getBlockheight());
				if (height.equals(Integer.toString(daemonvalues.getBlockheight()))) {
					walletview.syncvalue.setStyle(game.uiSkin.get("greenlabel", LabelStyle.class));
				} else {walletview.syncvalue.setStyle(game.uiSkin.get("default", LabelStyle.class));}
			}
			// If the string contains "money" then create a tx and add it to the txlist
			else if (queuepoll != null && queuepoll.contains("money")) {
				try {
					txlist.add(Tx.StringToTx(queuepoll));
					} catch (java.lang.NumberFormatException e) {System.out.println("Not enough moneys");}
			}
			// If the string contains "invalid password" then show popup and return to password screen
			else if (queuepoll != null && queuepoll.contains("invalid password")) {
				wp.destroy();
			    Dialog failuredialog = new Dialog("Password incorrect!", game.uiSkin, "dialog") {
			        public void result(Object obj) {
			            System.out.println("result "+obj);
			        }
			    };
			    Label failurelabel = new Label("Simplewallet reports you entered an invalid password, click to try again.", game.uiSkin);
			    failurelabel.setWrap(true);
			    failuredialog.add(failurelabel).width(400).row();
			    failuredialog.button("Continue", true).addListener(new ClickListener() {
			        @Override
			        public void clicked (InputEvent event, float x, float y) {
			            game.setScreen(new PasswordScreen(game));
			            stage.dispose();
			        }
			    });
			    failuredialog.key(Keys.ENTER, true); //sends "true" when the ENTER key is pressed
			    failuredialog.show(stage);
			}
		} catch (NullPointerException e){e.printStackTrace();}
	}
	

	public void dumptxlist() {
		boolean newtx = true;  // this is a flag to reset whenever txid or type changes
		Tx temptx = new Tx();
		// Cycle through list of transactions from queue in last 60 s
		if (txlist.size() > 0) {
			for (int i = 0; i < txlist.size(); i++) {
				// If newtx flag then create a new temporary tx
				if (newtx == true) { 
					temptx = new Tx();
					temptx.type = txlist.get(i).type;
					temptx.amount = txlist.get(i).amount;
					temptx.txid = txlist.get(i).txid;
				}
				// If newtx flag is false then append amount to temp tx
				else if (newtx == false) {
					temptx.amount += txlist.get(i).amount;
				}
				
				newtx = false; // Change flag then run check in next if statement

				// If the next tx is different or at the end of list, write current one to file and set flag
				if ((i < txlist.size()-1 && 
						(!(txlist.get(i).txid.toString().equals(txlist.get(i+1).txid.toString())) ||
								(txlist.get(i).type != txlist.get(i+1).type)))
						|| i == txlist.size()-1) {
					PrintWriter txout;
					try {
						txout = new PrintWriter(new BufferedWriter(new FileWriter(game.walletvalues.getName() + "tx.txt", true)));
						txout.println(temptx.type + " | " + df.format(temptx.amount) + " | " + temptx.txid);
						txout.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					newtx = true;					
				}
			}
		
			// Clear tx list so don't rewrite same txs
			txlist.clear();
		}
		
		// Read lines from tx file into history view and make buttons
		try (BufferedReader br = new BufferedReader(new FileReader(game.walletvalues.getName() + "tx.txt"))) {
			String 	line;
			int		linenum = 0;
			while ((line = br.readLine()) != null) {				
				if (linenum > 0) {
					if (txcount < linenum) {
						final TextButton tmpbtn = new TextButton(line, game.uiSkin, "tinytext");
						tmpbtn.addListener(new ClickListener() {
					        @Override
					        public void clicked (InputEvent event, float x, float y) {
					        	Gdx.app.getClipboard().setContents(tmpbtn.getText().toString().split(" | ")[4]);
					        }
					    });
						historyview.getContainer().add(tmpbtn).fillX().row();
						if (line.split(" | ")[0].equals("RECEIVED")) {
							calcbalance += Double.parseDouble(tmpbtn.getText().toString().split(" | ")[2]);
						} else {
							calcbalance -= Double.parseDouble(tmpbtn.getText().toString().split(" | ")[2]);
						}
						walletview.calcedvalue.setText(df.format(calcbalance) + " XMR");
						txcount += 1;
					}
				}
				linenum += 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
