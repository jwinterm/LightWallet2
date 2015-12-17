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
import com.jw.lightwallet.utils.CreateWallet;

public class CreateScreen extends AbstractScreen {
	
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
	Label				namelabel;
	TextField			nametext;
	String				name;
	Label				pwlabel;
	TextField			pwtext;
	String				pw;
	Label				pwrptlabel;
	TextField			pwrpttext;
	String				pwrpt;
	
	Table				buttontable;
	TextButton			createbutton;
	TextButton			gobackbutton;
	
	
	CreateWallet		createwallet;

	public CreateScreen(final LightWallet game) {
		super(game);
		System.out.print("CreateScreen creating.");
		
		uiSkin 			= new Skin(Gdx.files.internal("skin/uiskin.json"));
		padvalue = 20;
		
		stage 			= new Stage();
		Gdx.input.setInputProcessor(stage);
		
		screenlayout 	= new Table();
		
		logo 			= new Image(new Texture(Gdx.files.internal("logo.png")));
		//logo.scaleBy(0.02f);
		
		instruction		= "Please enter a name for your wallet in the \"Wallet name\" field. This is typically wallet.bin, "
				+ "but you can make it whatever you like (as long as it is alphanumberic and periods only). "
				+ "Then please enter a strong password for your wallet that you will use to unlock, and then repeat it to ensure it is correct. "
				+ "Your password can be any combination of characters and symbols on your keyboard (I think).";
		
		instructionlabel= new Label(instruction, uiSkin);
		instructionlabel.setWrap(true);
		textcontainer 	= new Table();
		textcontainer.add(instructionlabel).width(600);
		
		inputtable		= new Table();
		namelabel		= new Label("Wallet name: ", uiSkin);
		nametext		= new TextField("", uiSkin);
		pwlabel			= new Label("Password: ", uiSkin);
		pwtext			= new TextField("", uiSkin);
		pwtext.setPasswordMode(true);
		pwtext.setPasswordCharacter((char) 42);
		pwrptlabel		= new Label("Repeat password: ", uiSkin);
		pwrpttext		= new TextField("", uiSkin);
		pwrpttext.setPasswordMode(true);
		pwrpttext.setPasswordCharacter((char) 42);
		inputtable.add(namelabel).width(200);
		inputtable.add(nametext).width(400).row();
		inputtable.add(pwlabel).width(200);
		inputtable.add(pwtext).width(400).row();
		inputtable.add(pwrptlabel).width(200);
		inputtable.add(pwrpttext).width(400).row();
		
		buttontable		= new Table();
		createbutton	= new TextButton("Create!", uiSkin);
		createbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	        	name 	= nametext.getText();
	        	pw		= pwtext.getText();
	        	pwrpt	= pwrpttext.getText();
	        	if (name.length() > 0 && pw.equals(pwrpt)) {
	        		System.out.println(name + ", " + pw + ", " + pwrpt);
	        		createwallet = new CreateWallet(name, pw);
	        	}
	            //game.setScreen(game.getCreateScreen());
	        }
	    });	
		gobackbutton	= new TextButton("Cancel", uiSkin);
		gobackbutton.addListener(new ClickListener() {
	        @Override
	        public void clicked (InputEvent event, float x, float y) {
	            game.setScreen(game.getWizardScreen());
	        }
	    });
		buttontable.add(createbutton).width(150);
		buttontable.add(gobackbutton).width(150);
		
		screenlayout.add(logo).pad(10).width(200).height(50).center().row();
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
