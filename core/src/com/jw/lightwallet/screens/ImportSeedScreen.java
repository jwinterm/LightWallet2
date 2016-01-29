package com.jw.lightwallet.screens;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jw.lightwallet.LightWallet;
import com.jw.lightwallet.utils.Constants;

public class ImportSeedScreen extends AbstractScreen {
	
	Stage				stage;
	Table				screenlayout;
	int 				padvalue;
	
	Image				logo;
	Texture				logotex;

	Table				textcontainer;
	String				instruction;
	Label				instructionlabel;

	Table				inputtable;
	Label				namelabel;
	TextField			nametext;
	String				name;
	Label				seedlabel;
	TextField			seedtext;
	String				seed;
	Label				pwlabel;
	TextField			pwtext;
	String				pw;
	Label				pwrptlabel;
	TextField			pwrpttext;
	String				pwrpt;	
	Label				nodelabel;
	TextField			nodetext;
	
	Table				buttontable;
	TextButton			importbutton;
	TextButton			gobackbutton;

	public ImportSeedScreen(final LightWallet game) {
		super(game);
		
		padvalue = 20;
		
		stage 			= new Stage();
		Gdx.input.setInputProcessor(stage);
		
		screenlayout 	= new Table();
		
		logo 			= new Image(new Texture(Gdx.files.internal("logo.png")));
		//logo.scaleBy(0.02f);
		
		instruction		= "Please enter a name for your wallet, the 25 word mnemonic seed to import in the seed field, "
				+ " a password to unlock the wallet in the password field, "
				+ "and then repeat the password to make sure you have it correct, this will be used to encrypt your newly restored wallet files. "
				+ "The default bitmonerod node is set for moneroclub, if you want to run a local daemon change the node field to http://localhost:18081.";
		
		instructionlabel= new Label(instruction, game.uiSkin);
		instructionlabel.setWrap(true);
		textcontainer 	= new Table();
		textcontainer.add(instructionlabel).width(600);
		
		inputtable		= new Table();
		
		namelabel		= new Label("Wallet name: ", game.uiSkin);
		nametext		= new TextField("", game.uiSkin);
		
		seedlabel		= new Label("Wallet seed: ", game.uiSkin);
		seedtext		= new TextField("", game.uiSkin);
		
		pwlabel			= new Label("Password: ", game.uiSkin);
		pwtext			= new TextField("", game.uiSkin);
		pwtext.setPasswordMode(true);
		pwtext.setPasswordCharacter((char) 42);
		
		pwrptlabel			= new Label("Repeat password: ", game.uiSkin);
		pwrpttext			= new TextField("", game.uiSkin);
		pwrpttext.setPasswordMode(true);
		pwrpttext.setPasswordCharacter((char) 42);

		nodelabel		= new Label("Node address: ", game.uiSkin);
		nodetext		= new TextField(Constants.moneroclubdmn, game.uiSkin);
		
		inputtable.add(namelabel).width(200);
		inputtable.add(nametext).width(400).row();		
		inputtable.add(seedlabel).width(200);
		inputtable.add(seedtext).width(400).row();
		inputtable.add(pwlabel).width(200);
		inputtable.add(pwtext).width(400).row();
		inputtable.add(pwrptlabel).width(200);
		inputtable.add(pwrpttext).width(400).row();
		inputtable.add(nodelabel).width(200);
		inputtable.add(nodetext).width(400).row();
		
		buttontable		= new Table();
		
	    // Create check entries dialog
	    final Dialog checkdialog = new Dialog("Check your inputs!", game.uiSkin, "dialog") {
	        public void result(Object obj) {
	            System.out.println("result "+obj);
	        }
	    };
	    Label checklabel = new Label("Please check that you have entered a valid file name, that your seed is 25 words"
	    		+ ", and that the passwords you have input are the same, then try again.", game.uiSkin);
	    checklabel.setWrap(true);
	    checkdialog.add(checklabel).width(400).row();
	    checkdialog.button("Continue", true).addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            checkdialog.remove();
	        }
	    });
	    checkdialog.key(Keys.ENTER, true); //sends "true" when the ENTER key is pressed
		
		importbutton	= new TextButton("Import!", game.uiSkin);
		importbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	    	    name	= nametext.getText();
	    	    seed	= seedtext.getText();
	    	    pw		= pwtext.getText();
	    	    pwrpt	= pwrpttext.getText();
        		System.out.println(seed + ", " + pw);
        		if (name.length() > 0 && seed.trim().split("\\s+").length == 25 && pw.equals(pwrpt)) {
        			ImportWallet(seed, pw, game.simplewalletloc);
        		} else {checkdialog.show(stage);}
	        }
	    });	
		
		gobackbutton	= new TextButton("Cancel", game.uiSkin);
		gobackbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            game.setScreen(new WizardScreen(game));
	        }
	    });
		
		buttontable.add(importbutton).width(150);
		buttontable.add(gobackbutton).width(150);
		
		screenlayout.add(logo).pad(10).width(200).height(70).center().row();
		screenlayout.add(textcontainer).pad(padvalue).row();
		screenlayout.add(inputtable).pad(padvalue).row();
		screenlayout.add(buttontable).pad(padvalue).row();
		
		screenlayout.setFillParent(true);
		stage.addActor(screenlayout);
		
		
	}
	
	public void show() {
		super.show();
			
	}
	
	public void render(float delta) {
		super.render(delta);

		stage.draw();
		stage.act(delta);
		
	}
	
	public void dispose() {
		super.dispose();
		
	}
	
	public void ImportWallet(String path, String pw, String simplewalletloc) {
		
		// Initialize variables local to ImportWallet function		
		int 		i 		= 0;
	    String 		str 	= null;    
	    
	    // Create success and failure dialogs to show based on outcome
	    Dialog successdialog = new Dialog("Success!", game.uiSkin, "dialog") {
	        public void result(Object obj) {
	            System.out.println("result "+obj);
	        }
	    };
	    Label successlabel = new Label("", game.uiSkin);
	    successlabel.setWrap(true);
	    successdialog.add(successlabel).width(400).row();
	    successdialog.button("Continue", true).addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            game.setScreen(new MainScreen(game));
	            stage.dispose();
	        }
	    });
	    successdialog.key(Keys.ENTER, true); //sends "true" when the ENTER key is pressed

	    Dialog failuredialog = new Dialog("Utter failure!", game.uiSkin, "dialog") {
	        public void result(Object obj) {
	            System.out.println("result "+obj);
	        }
	    };
	    Label failurelabel = new Label("Wallet import failed. Click to start over.", game.uiSkin);
	    failurelabel.setWrap(true);
	    failuredialog.add(failurelabel).width(400).row();
	    failuredialog.button("Continue", true).addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            game.setScreen(new WizardScreen(game));
	            stage.dispose();
	        }
	    });
	    failuredialog.key(Keys.ENTER, true); //sends "true" when the ENTER key is pressed
	    
	        
        try {
            Process wp = Runtime.getRuntime().exec("simplewallet --restore-deterministic-wallet --daemon-address http://localhost:66666");
            Writer wr = new OutputStreamWriter( wp.getOutputStream() );
            BufferedReader rd = new BufferedReader( new InputStreamReader( wp.getInputStream() ) );
            
            System.out.println("Here is the standard output of the command:\n");
            
            while (true) {
            	str = rd.readLine();
	            System.out.println(i + " " + str);
	            i += 1;
	            if (i > 1 && str.contains("Wallet")) {
	            	break;
	            }
            }
            
            wr.write( name + "\n" );
            wr.flush();
            wr.write( pw + "\n" );
            wr.flush();
            wr.write( seed + "\n" );
            wr.flush();
            
            game.walletvalues.setName(name);
            game.walletvalues.setPw(pw);
            game.walletvalues.setSeed(seed);
            game.walletvalues.setNode(nodetext.getText());
            
            while (true) {
            	str = rd.readLine();
	            System.out.println(i + " " + str);
                if (str.contains("new wallet:")) {
                	game.walletvalues.setAddress(str.split("wallet: ")[1]);
                }
                if (str.contains("View key:")) {
                	game.walletvalues.setViewkey(str.split(": ")[1]);
                }
	            i += 1;
	            if (i > 5 && str.contains("****")) {
	            	break;
	            }
            }

        	str = rd.readLine();
            i += 1;
            
            wr.write( "exit\n" );
            wr.flush();
            
            PrintWriter infowriter = new PrintWriter(name + "info.txt", "UTF-8");
            infowriter.println("Wallet name: " + name);
            infowriter.println("Wallet pw: " + pw);
            infowriter.println("Wallet address: " + game.walletvalues.getAddress());
            infowriter.println("Wallet view key: " + game.walletvalues.getViewkey());
            infowriter.println("Wallet seed: " + game.walletvalues.getSeed());
            infowriter.close();
            
            PrintWriter confwriter = new PrintWriter("lightwallet.conf", "UTF-8");
            confwriter.println("Wallet name: " + name);
            confwriter.println("Wallet address: " + game.walletvalues.getAddress());
            confwriter.println("Wallet view key: " + game.walletvalues.getViewkey());
            confwriter.println("Node address: " + nodetext.getText());
            confwriter.close();
            
            PrintWriter txwriter = new PrintWriter(name + "tx.txt", "UTF-8");
            txwriter.println("Transaction history (Type | Amount | Tx ID): ");
            txwriter.close();
            
            wp.destroy();
            
            successlabel.setText("Wallet successfully imported. " + game.walletvalues.getName() + " file is your binary wallet file. "
	    		+ game.walletvalues.getName() + ".keys is your keys file; "
	    		+ "the keys file is password protected and you can restore your binary wallet file with it. "
	    		+ " A text file named " + game.walletvalues.getName() + "info.txt has also been created. "
	    		+ "This file contains your wallet recovery seed and view key. "
	    		+ "Please encrypt this file or print it and delete it. Your wallet seed allows wallet recovery and bypasses your password."
	    		+ "The text file " + game.walletvalues.getName() + "txs.txt is empty, but will contain your tx history after syncing the wallet. "
	    		+ "A file named lightwallet.conf has also been created; this file specifies the wallet and node currently in use.");
    	    successdialog.show(stage);
        }
        
        catch (Exception e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
	    	failuredialog.show(stage);
	    	return;
        }
        
	    Gdx.app.log(LightWallet.LOG, "Address is: " + game.walletvalues.getAddress());
	    Gdx.app.log(LightWallet.LOG, "Seed is: " + game.walletvalues.getSeed());
	}

}
