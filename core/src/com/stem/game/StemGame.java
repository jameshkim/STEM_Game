package com.stem.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class StemGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont titleFont;
    public BitmapFont byFont;
    public BitmapFont tapFont;

    public void create() {
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        
        titleFont = new BitmapFont(Gdx.files.internal("titleFont.fnt"));
        byFont = new BitmapFont(Gdx.files.internal("byFont.fnt"));
        tapFont = new BitmapFont(Gdx.files.internal("tapFont.fnt"));
        
        titleFont.setColor(Color.BLACK);
        tapFont.setColor(Color.BLACK);
        byFont.setColor(Color.BLACK);
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        titleFont.dispose();
        byFont.dispose();
        tapFont.dispose();
        super.dispose();
    }
}
