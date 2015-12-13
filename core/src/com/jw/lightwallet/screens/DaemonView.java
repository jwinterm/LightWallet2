package com.jw.lightwallet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jw.lightwallet.utils.Constants;
import com.jw.lightwallet.utils.DaemonValues;

public class DaemonView {
	
	Table		daemonlayout;
	
	Label		nodelabel;
	Label		nodevalue;
	Label		statuslabel;
	Label		statusvalue;
	Label		heightlabel;
	Label		heightvalue;
	Label		hashratelabel;
	Label		hashratevalue;
	Label		rewardlabel;
	Label		rewardvalue;

	
	public DaemonView () {
		
	Skin uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		
	daemonlayout 	= new Table();
		

	
	nodelabel 		= new Label("Node address: ", uiSkin);
	nodevalue		= new Label(null, uiSkin);
	daemonlayout.add(nodelabel).pad(20);
	daemonlayout.add(nodevalue).pad(20).row();
	
	statuslabel 	= new Label("Node status: ", uiSkin);
	statusvalue		= new Label(null, uiSkin);
	daemonlayout.add(statuslabel).pad(20);
	daemonlayout.add(statusvalue).pad(20).row();
	
	heightlabel 	= new Label("Block height: ", uiSkin);
	heightvalue		= new Label(null, uiSkin);
	daemonlayout.add(heightlabel).pad(20);
	daemonlayout.add(heightvalue).pad(20).row();
	
	hashratelabel 	= new Label("Network hashrate: ", uiSkin);
	hashratevalue	= new Label(null, uiSkin);
	daemonlayout.add(hashratelabel).pad(20);
	daemonlayout.add(hashratevalue).pad(20).row();
	
	rewardlabel 	= new Label("Last reward: ", uiSkin);
	rewardvalue		= new Label(null, uiSkin);
	daemonlayout.add(rewardlabel).pad(20);
	daemonlayout.add(rewardvalue).pad(20).row();
	}
	
	public void Update(DaemonValues daemonvalues) {
		nodevalue.setText(Constants.moneroclub);
		statusvalue.setText(daemonvalues.getStatus());
		heightvalue.setText(String.valueOf(daemonvalues.getBlockheight()));
		hashratevalue.setText(String.valueOf(daemonvalues.getHashrate()));
		rewardvalue.setText(String.valueOf(daemonvalues.getLastblockreward()));
	}

}
