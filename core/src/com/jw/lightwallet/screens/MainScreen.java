package com.jw.lightwallet.screens;

import java.io.IOException;

import com.jw.lightwallet.LightWallet;
import com.jw.lightwallet.utils.DaemonRPC;

public class MainScreen extends AbstractScreen {
	DaemonRPC		daemonrpc;

	public MainScreen(LightWallet game) {
		super(game);
		daemonrpc = new DaemonRPC();
		daemonrpc.getinfo();
		

	}

}
