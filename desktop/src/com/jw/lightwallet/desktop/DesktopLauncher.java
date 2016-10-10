package com.jw.lightwallet.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jw.lightwallet.LightWallet;
import com.jw.lightwallet.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.DESKTOP_WIDTH;
		config.height = Constants.DESKTOP_HEIGHT;
		config.title = "Monero LightWallet";
		config.addIcon("assets/icon32.png", FileType.Internal);
		new LwjglApplication(new LightWallet(), config);
	}
}
