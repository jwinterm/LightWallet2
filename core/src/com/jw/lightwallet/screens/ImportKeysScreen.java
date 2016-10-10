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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

public class ImportKeysScreen extends AbstractScreen {

	Stage				stage;
	Table				screenlayout;
	int 				padvalue;
	
	Image				logo;
	Texture				logotex;

	Table				textcontainer;
	String				instruction;
	Label				instructionlabel;

	Table				inputtable;
	Label				pathlabel;
	TextField			pathtext;
	String				path;
	Label				pwlabel;
	TextField			pwtext;
	String				pw;
	Label				nodelabel;
	TextField			nodetext;
	
	Table				buttontable;
	TextButton			importbutton;
	TextButton			gobackbutton;
	
	
	public ImportKeysScreen(final LightWallet game) {
		super(game);
		
		padvalue = 20;
		
		stage 			= new Stage();
		Gdx.input.setInputProcessor(stage);
		
		screenlayout 	= new Table();
		
		logo 			= new Image(new Texture(Gdx.files.internal("assets/logo.png")));
		//logo.scaleBy(0.02f);
		
		instruction		= "Please enter the full path location of your .keys file to import in the file location field; , "
				+ "for instance: C:\\Users\\jw\\secretstuff\\wallet.keys or /home/jw/secretstuff/wallet.keys. "
				+ "Then please enter your password to unlock the wallet. "
				+ "The wallet keys file will be copied to the local directory and a new binary file will be created. "
				+ "The default bitmonerod node is set for moneroworld, if you want to run a local daemon change the node field to http://localhost:18081.";
		
		instructionlabel= new Label(instruction, game.uiSkin);
		instructionlabel.setWrap(true);
		textcontainer 	= new Table();
		textcontainer.add(instructionlabel).width(600);
		
		inputtable		= new Table();
		
		pathlabel		= new Label("Wallet path: ", game.uiSkin);
		pathtext		= new TextField("", game.uiSkin);
		
		pwlabel			= new Label("Password: ", game.uiSkin);
		pwtext			= new TextField("", game.uiSkin);
		pwtext.setPasswordMode(true);
		pwtext.setPasswordCharacter((char) 42);

		nodelabel		= new Label("Node address: ", game.uiSkin);
		nodetext		= new TextField(Constants.moneroclubdmn, game.uiSkin);
		
		inputtable.add(pathlabel).width(200);
		inputtable.add(pathtext).width(400).row();
		inputtable.add(pwlabel).width(200);
		inputtable.add(pwtext).width(400).row();
		inputtable.add(nodelabel).width(200);
		inputtable.add(nodetext).width(400).row();
		
		buttontable		= new Table();
		
		importbutton	= new TextButton("Import!", game.uiSkin);
		importbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	path 	= pathtext.getText();
	        	pw		= pwtext.getText();
        		System.out.println(path + ", " + pw);
        		ImportWallet(path, pw, game.simplewalletloc);
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
	    
	    File		file	= new File(path);
	    String		fname	= file.getName();
	    String		name	= null;
	    
	    
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
	    
	    
	    // Try to parse wallet path for name of wallet and if fail throw failure screen
	    try {
	    	name = fname.substring(0, fname.lastIndexOf('.'));
	    } 
	    catch (Exception e) {
	    	failuredialog.show(stage);
	    	e.printStackTrace();
	    	return;
	    }
	    
	    
	    try {
			Files.copy(file.toPath(), new File(file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
		} 
	    catch (IOException e) {
	    	failuredialog.show(stage);
	    	e.printStackTrace();
	    	return;			
		}

	    
        try {
            Process wp = Runtime.getRuntime().exec(simplewalletloc + " --wallet-file " + name + " --password " + pw + " --daemon-address http://localhost:66666");
            Writer wr = new OutputStreamWriter( wp.getOutputStream() );
            BufferedReader rd = new BufferedReader( new InputStreamReader( wp.getInputStream() ) );
            
            System.out.println("Here is the standard output of the command:\n");
            while (true) {
            	str = rd.readLine();
	            System.out.println(i + " " + str);
                if (str.contains("Opened wallet:")) {
                	game.walletvalues.setAddress(str.split("wallet: ")[1]);
                }
	            i += 1;
	            if (i > 3 && str.contains("****")) {
	            	break;
	            }
            }
        	str = rd.readLine();
        	str = rd.readLine();
        	str = rd.readLine();
        	str = rd.readLine();
        	str = rd.readLine();

            wr.write( "viewkey\n" );
            wr.flush();

        	str = rd.readLine();
//        	str = rd.readLine();
//            System.out.println(i + " " + str.split(": ")[1]);
            System.out.println(i + " " + str);
            i += 1;
//            game.walletvalues.setViewkey(str.split(": ")[1]);            
            game.walletvalues.setViewkey(str);            
        	str = rd.readLine();

            wr.write( "seed\n" );
            wr.flush();
            
//        	str = rd.readLine();
//            System.out.println(i + " " + str.substring(str.indexOf(":"), str.length()));
            System.out.println(i + " " + str);
            i += 1;
//            game.walletvalues.setSeed(str.substring(str.indexOf(":"), str.length()));
            game.walletvalues.setSeed(str);
            
            wr.write( "exit\n" );
            wr.flush();
            
            String rando = name + Double.toString(Math.random());
            MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            md.update(rando.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            
            game.walletvalues.setName(name);
            game.walletvalues.setPw(pw);
            game.walletvalues.setNode(nodetext.getText());
            game.walletvalues.setUserAgent(digest.toString());

            
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
            confwriter.println("User agent: " + digest.toString());
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
        
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
	    	failuredialog.show(stage);
	    	return;
        }
        
	    Gdx.app.log(LightWallet.LOG, "Address is: " + game.walletvalues.getAddress());
	    Gdx.app.log(LightWallet.LOG, "Seed is: " + game.walletvalues.getSeed());
	}

}
