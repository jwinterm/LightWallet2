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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jw.lightwallet.LightWallet;

public class PasswordScreen extends AbstractScreen {
	
	Stage				stage;
	Table				screenlayout;
	Skin				uiSkin;
	int 				padvalue;
	
	Image				logo;
	Texture				logotex;

	Table				textcontainer;
	String				instruction;
	Label				instructionlabel;

	Table				inputtable;

	Label				pwlabel;
	TextField			pwtext;
	String				pw;

	Label				nodelabel;
	TextField			nodetext;
	String				node;
	
	Table				buttontable;
	TextButton			createbutton;
	
	
	public PasswordScreen(final LightWallet game) {
		super(game);
		System.out.print("CreateScreen creating.");
		
		uiSkin 			= new Skin(Gdx.files.internal("skin/uiskin.json"));
		padvalue = 20;
		
		stage 			= new Stage();
		Gdx.input.setInputProcessor(stage);
		
		screenlayout 	= new Table();
		
		logo 			= new Image(new Texture(Gdx.files.internal("logo.png")));
		//logo.scaleBy(0.02f);
		
		instruction		= "Please enter your password in the password field below for the wallet" + game.walletvalues.getName() + ". "
				+ "The node listed in lightwallet.conf is listed below as well; you may update it now if you like.";
		
		instructionlabel= new Label(instruction, uiSkin);
		instructionlabel.setWrap(true);
		textcontainer 	= new Table();
		textcontainer.add(instructionlabel).width(600);
		
		inputtable		= new Table();

		pwlabel			= new Label("Password: ", uiSkin);
		pwtext			= new TextField("", uiSkin);
		pwtext.setPasswordMode(true);
		pwtext.setPasswordCharacter((char) 42);

		nodelabel		= new Label("Node address: ", uiSkin);
		nodetext		= new TextField(game.walletvalues.getNode(), uiSkin);

		inputtable.add(pwlabel).width(200);
		inputtable.add(pwtext).width(400).row();

		inputtable.add(nodelabel).width(200);
		inputtable.add(nodetext).width(400).row();
		
		buttontable		= new Table();
		createbutton	= new TextButton("Proceed", uiSkin);
		createbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	pw		= pwtext.getText();
	        	node	= nodetext.getText();
	        	game.walletvalues.setPw(pw);
	        	game.walletvalues.setNode(node);
	        	game.setScreen(new MainScreen(game));
	        	}
	        });	
		buttontable.add(createbutton).width(150);
		
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

}
