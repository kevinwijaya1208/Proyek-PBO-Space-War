package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class SpaceWar extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public FreeTypeFontGenerator fontGenerator;
    public FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        // untuk pake font yang jenisnya seperti yang sudah didownload
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fontSpace.TTF"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 20;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);
        this.setScreen(new MainMenuScreen(this));
    }

    public void render () {
        super.render();
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
