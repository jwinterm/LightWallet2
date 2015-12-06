package com.jw.lightwallet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.jw.lightwallet.screens.MainScreen;

public class LightWallet extends Game {
	public static final String LOG = LightWallet.class.getSimpleName(); // Logging constant

	
	@Override
	public void create () {
		Gdx.app.log(LightWallet.LOG, "Launching application");
		setScreen(new MainScreen(this));
	}


}
