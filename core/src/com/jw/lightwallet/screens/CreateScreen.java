package com.jw.lightwallet.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jw.lightwallet.LightWallet;
import com.jw.lightwallet.utils.Constants;

public class CreateScreen extends AbstractScreen {
	
	Stage				stage;
	Table				screenlayout;
	Skin				uiSkin;
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
	Label				pwlabel;
	TextField			pwtext;
	String				pw;
	Label				pwrptlabel;
	TextField			pwrpttext;
	String				pwrpt;
	Label				nodelabel;
	TextField			nodetext;
	
	Table				buttontable;
	TextButton			createbutton;
	TextButton			gobackbutton;
	
	
	public CreateScreen(final LightWallet game) {
		super(game);
		System.out.print("CreateScreen creating.");
		
		uiSkin 			= new Skin(Gdx.files.internal("skin/uiskin.json"));
		padvalue = 20;
		
		stage 			= new Stage();
		Gdx.input.setInputProcessor(stage);
		
		screenlayout 	= new Table();
		
		logo 			= new Image(new Texture(Gdx.files.internal("logo.png")));
		//logo.scaleBy(0.02f);
		
		instruction		= "Please enter a name for your wallet in the \"Wallet name\" field. This is typically wallet.bin, "
				+ "but you can make it whatever you like (as long as it is alphanumberic and periods only). "
				+ "Then please enter a strong password for your wallet that you will use to unlock, and then repeat it to ensure it is correct. "
				+ "Your password can be any combination of characters and symbols on your keyboard (I think). "
				+ "The default bitmonerod node is set for moneroclub, if you want to run a local daemon change the node field to http://localhost:18081.";
		
		instructionlabel= new Label(instruction, uiSkin);
		instructionlabel.setWrap(true);
		textcontainer 	= new Table();
		textcontainer.add(instructionlabel).width(600);
		
		inputtable		= new Table();
		namelabel		= new Label("Wallet name: ", uiSkin);
		nametext		= new TextField("", uiSkin);
		pwlabel			= new Label("Password: ", uiSkin);
		pwtext			= new TextField("", uiSkin);
		pwtext.setPasswordMode(true);
		pwtext.setPasswordCharacter((char) 42);
		pwrptlabel		= new Label("Repeat password: ", uiSkin);
		pwrpttext		= new TextField("", uiSkin);
		pwrpttext.setPasswordMode(true);
		pwrpttext.setPasswordCharacter((char) 42);
		nodelabel		= new Label("Node address: ", uiSkin);
		nodetext		= new TextField(Constants.moneroclub, uiSkin);
		inputtable.add(namelabel).width(200);
		inputtable.add(nametext).width(400).row();
		inputtable.add(pwlabel).width(200);
		inputtable.add(pwtext).width(400).row();
		inputtable.add(pwrptlabel).width(200);
		inputtable.add(pwrpttext).width(400).row();
		inputtable.add(nodelabel).width(200);
		inputtable.add(nodetext).width(400).row();
		
		buttontable		= new Table();
		createbutton	= new TextButton("Create!", uiSkin);
		createbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	name 	= nametext.getText();
	        	pw		= pwtext.getText();
	        	pwrpt	= pwrpttext.getText();
	        	if (name.length() > 0 && pw.equals(pwrpt)) {
	        		System.out.println(name + ", " + pw + ", " + pwrpt);
	        		CreateWallet(name, pw, game.simplewalletloc);
	        	}
	            //game.setScreen(game.getCreateScreen());
	        }
	    });	
		gobackbutton	= new TextButton("Cancel", uiSkin);
		gobackbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            game.setScreen(new WizardScreen(game));
	        }
	    });
		buttontable.add(createbutton).width(150);
		buttontable.add(gobackbutton).width(150);
		
		screenlayout.add(logo).pad(10).width(200).height(50).center().row();
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
	
	public void CreateWallet(String name, String pw, String simplewalletloc) {
		
		int 		i 		= 0;
		boolean 	flag 	= false;
	    String 		str 	= null;
	    String		seed	= "";
	    
	    Dialog success_dialog = new Dialog("Success!", uiSkin, "dialog") {
	        public void result(Object obj) {
	            System.out.println("result "+obj);
	        }
	    };
	    Label success_label = new Label("Wallet successfully created. " + name + " file is your binary wallet file. "
	    		+ name + ".keys is your keys file; "
	    		+ "the keys file is password protected and you can restore your binary wallet file with it. " +
	    		" A text file named " + name + "info.txt has also been created. This file contains your wallet recovery seed and view key. " +
	    		"Please encrypt this file or print it and delete it. Your wallet seed allows wallet recovery and bypasses your password." +
	    		"A file named lightwallet.conf has also been created; this file specifies the wallet and node currently in use.", uiSkin);
	    success_label.setWrap(true);
	    success_dialog.add(success_label).width(400).row();
	    success_dialog.button("Continue", true).addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            game.setScreen(new MainScreen(game));
	            uiSkin.dispose();
	            stage.dispose();
	        }
	    });
	    success_dialog.key(Keys.ENTER, true); //sends "true" when the ENTER key is pressed



    	System.out.println("Starting creator");
	    
	        try {
	            Process tr = Runtime.getRuntime().exec("simplewallet --generate-new-wallet " + name + " --password " + pw);
	            Writer wr = new OutputStreamWriter( tr.getOutputStream() );
	            BufferedReader rd = new BufferedReader( new InputStreamReader( tr.getInputStream() ) );
	            
	            System.out.println("Here is the standard output of the command:\n");
	            while (true) {
	            	str = rd.readLine();
		            System.out.println(i + " " + str);
		            i += 1;
		            if (str.contains("Japanese")) {
		            	break;
		            }
	            }
	            wr.write( "0\n" );
	            wr.flush();

	            while (true) {
	            	str = rd.readLine();
	                System.out.println(i + " " + str);
	                i += 1;
	                if (str.contains("*****") && flag == true) {break;}
	                if (str.contains("*****")) {flag = true;}
	                if (str.contains("new wallet:")) {
	                	game.walletvalues.setAddress(str.split("wallet: ")[1]);
	                }
	                if (str.contains("view key:")) {
	                	game.walletvalues.setViewkey(str.split("key: ")[1]);
	                }
	                if (i >=23 && i <=26) {seed += str;}
	            }	            
	            
	            wr.write( "exit\n" );
	            wr.flush();
	            
	            game.walletvalues.setName(name);
	            game.walletvalues.setPw(pw);
	            game.walletvalues.setSeed(seed);
	            
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
	            
	    	    success_dialog.show(stage);
	        }
	        
	        catch (IOException e) {
	            System.out.println("exception happened - here's what I know: ");
	            e.printStackTrace();
	        }
		    Gdx.app.log(LightWallet.LOG, "Address is: " + game.walletvalues.getAddress());
		    Gdx.app.log(LightWallet.LOG, "Seed is: " + game.walletvalues.getSeed());
	}


}
