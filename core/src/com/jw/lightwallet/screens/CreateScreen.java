package com.jw.lightwallet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.jw.lightwallet.LightWallet;

public class CreateScreen extends AbstractScreen {
	
	Stage				stage;
	Table				screenlayout;
	Skin				uiSkin;
	int 				padvalue;
	
	Image				logo;
	Texture				logotex;

	String				instruction;
	Label				instructionlabel;
	Table				textcontainer;
	
	Label				namelabel;
	TextField			nametext;
	Label				pwlabel;
	TextField			pwtext;
	Label				pwrptlabel;
	TextField			pwrpttext;
	
	TextButton			createbutton;
	TextButton			gobackbutton;
	

	public CreateScreen(final LightWallet game) {
		super(game);
		
		uiSkin 			= new Skin(Gdx.files.internal("skin/uiskin.json"));
		padvalue = 20;
		
		stage 			= new Stage();
		Gdx.input.setInputProcessor(stage);
		
		screenlayout 	= new Table();
		
		logo 			= new Image(new Texture(Gdx.files.internal("logo.png")));

		
		instruction		= "Please enter a name for your wallet in the \"Wallet name\" field. This is typically wallet.bin, "
				+ "but you can make it whatever you like (as long as it is alphanumberic and periods only). "
				+ "Then please enter a strong password for your wallet that you will use to unlock, and then repeat it to ensure it is correct. "
				+ "Your password can be any combination of characters and symbols on your keyboard (I think).";
		
		instructionlabel= new Label(instruction, uiSkin);
		instructionlabel.setWrap(true);
		textcontainer 	= new Table();
		textcontainer.add(instructionlabel).width(600);
		
		screenlayout.add(logo).pad(10).center().row();
		screenlayout.add(textcontainer).pad(padvalue).row();
		//screenlayout.add(buttontable).pad(padvalue).row();
		
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


}
