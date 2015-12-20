package com.jw.lightwallet;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.jw.lightwallet.screens.MainScreen;
import com.jw.lightwallet.screens.WizardScreen;
import com.jw.lightwallet.utils.WalletValues;

public class LightWallet extends Game {
	
	public static final String 	LOG = LightWallet.class.getSimpleName(); // Logging constant
	
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
			simplewalletloc = dir + "\\" + "simplewallet.exe";
			System.out.println(simplewalletloc);
		}
		
		// Initialize logger
		Gdx.app.log(LightWallet.LOG, "Launching application in " + dir + " on " + os);
		
		// Check if config file exists, if not launch setup wizard
		File f = new File("lightwallet.conf");
		if(f.exists() && !f.isDirectory()) { 
			setScreen(new MainScreen(this));
		} 
		else {setScreen(new WizardScreen(this));}
		
	}


}
