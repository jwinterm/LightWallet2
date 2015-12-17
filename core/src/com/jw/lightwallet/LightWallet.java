package com.jw.lightwallet;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.jw.lightwallet.screens.CreateScreen;
import com.jw.lightwallet.screens.ImportKeysScreen;
import com.jw.lightwallet.screens.ImportSeedScreen;
import com.jw.lightwallet.screens.MainScreen;
import com.jw.lightwallet.screens.WizardScreen;

public class LightWallet extends Game {
	
	public static final String 	LOG = LightWallet.class.getSimpleName(); // Logging constant
	
	public String				dir;

	// Set screen methods
	public MainScreen getMainScreen(){
		return new MainScreen(this);
	}
	public WizardScreen getWizardScreen(){
		return new WizardScreen(this);
	}
	public CreateScreen getCreateScreen(){
		return new CreateScreen(this);
	}
	public ImportKeysScreen getImportKeysScreen(){
		return new ImportKeysScreen(this);
	}
	public ImportSeedScreen getImportSeedScreen(){
		return new ImportSeedScreen(this);
	}
	
	@Override
	public void create () {
		// Initialize logger
		Gdx.app.log(LightWallet.LOG, "Launching application");
		
		// Get directory where program launched
		dir = System.getProperty("user.dir");
		
		// Check if config file exists, if not launch setup wizard
		File f = new File("lightwallet.conf");
		if(f.exists() && !f.isDirectory()) { 
			setScreen(getMainScreen());
		} 
		else {setScreen(getWizardScreen());}
		
	}


}
