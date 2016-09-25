package com.jw.lightwallet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jw.lightwallet.screens.PasswordScreen;
import com.jw.lightwallet.screens.WizardScreen;
import com.jw.lightwallet.utils.WalletValues;

public class LightWallet extends Game {
	
	public static final String 	LOG = LightWallet.class.getSimpleName(); // Logging constant
	public Skin					uiSkin;
	
	public String				dir;
	public String				os;
	public String				simplewalletloc;
	
	public WalletValues			walletvalues;

	@Override
	public void create () {
		
		// Object to store wallet values
		walletvalues = new WalletValues();
		
		// Get directory where program launched
		dir = System.getProperty("user.dir");
		os = System.getProperty("os.name");
		
		if (os.contains("Windows")) {
			simplewalletloc = dir + "\\" + "monero-wallet-cli.exe";
			System.out.println(simplewalletloc);
		}
		
		// Initialize logger and user interface skin
		Gdx.app.log(LightWallet.LOG, "Launching application in " + dir + " on " + os);
		uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
		// Check if config file exists, if not launch setup wizard
		File f = new File("lightwallet.conf");
		
		if(f.exists() && !f.isDirectory()) { 
			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			    String line;
			    while ((line = br.readLine()) != null) {
				       if (line.contains("name: ")) {walletvalues.setName(line.split("name: ")[1]);}
				       if (line.contains("Wallet address: ")) {walletvalues.setAddress(line.split("address: ")[1]);}
				       if (line.contains("view key: ")) {walletvalues.setViewkey(line.split("view key: ")[1]);}
				       if (line.contains("Node address: ")) {walletvalues.setNode(line.split("address: ")[1]);}
				       if (line.contains("User agent: ")) {walletvalues.setUserAgent(line.split("User agent: ")[1]);}
			    }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Gdx.app.log(LightWallet.LOG, "Node address on startup is: " + walletvalues.getAddress());
			setScreen(new PasswordScreen(this));
		} 
		
		else {setScreen(new WizardScreen(this));}
		
	}


}
