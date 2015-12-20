package com.jw.lightwallet.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jw.lightwallet.LightWallet;

public class WizardScreen extends AbstractScreen {
	
	// Layout stuff
	Stage				stage;
	Table				screenlayout;
	Skin				uiSkin;
	int 				padvalue;

	Image				logo;
	Texture				logotex;
	
	Table				textcontainer;
	Label				wizardlabel;
	String				wizardtext;
	
	Table				buttontable;
	TextButton			createbutton;
	TextButton			importkeysbutton;
	TextButton			importseedbutton;
	
		
	public WizardScreen(final LightWallet game) {
		super(game);
		
		uiSkin 			= new Skin(Gdx.files.internal("skin/uiskin.json"));
		padvalue = 20;
		
		stage 			= new Stage();
		Gdx.input.setInputProcessor(stage);
		
		screenlayout 	= new Table();
		
		logo 			= new Image(new Texture(Gdx.files.internal("logo.png")));

		
		wizardtext = "Hello. It seems this is the either the first time you are launching LightWallet or you have deleted your lightwallet.conf file. "
				+ "Please select from the options below whether you would like to create a new wallet, import a wallet from a .keys file, "
				+ "or import a wallet using your 25-word mnemonic seed.";
		
		wizardlabel		= new Label(wizardtext, uiSkin);
		wizardlabel.setWrap(true);
		textcontainer 	= new Table();
		textcontainer.add(wizardlabel).width(600);
		
		buttontable 	= new Table();
		createbutton 	= new TextButton("Create", uiSkin);
		createbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            game.setScreen(new CreateScreen(game));
	        }
	    });
		createbutton.pad(padvalue);
		
		importkeysbutton 	= new TextButton("Import .keys", uiSkin);
		importkeysbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	game.setScreen(new ImportKeysScreen(game));
	        }
	    });
		importkeysbutton.pad(padvalue);
		
		importseedbutton 	= new TextButton("Import seed", uiSkin);
		importseedbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	game.setScreen(new ImportSeedScreen(game));
	        }
	    });
		importseedbutton.pad(padvalue);
		
		buttontable.add(createbutton).width(150);
		buttontable.add(importkeysbutton).width(150);
		buttontable.add(importseedbutton).width(150);

		screenlayout.add(logo).pad(10).center().row();
		screenlayout.add(textcontainer).pad(padvalue).row();
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

}
