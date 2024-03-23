package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {
    private Texture menuScreen;
    final SpaceWar game;
    OrthographicCamera camera;
    private Music gameMusic;

    public MainMenuScreen(final SpaceWar game){
        this.game = game;
        menuScreen = new Texture(Gdx.files.internal("backgroundMenu.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("MusicGame.mp3"));
        gameMusic.setLooping(true);
        gameMusic.play();
    }


    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Menampilkan gambar serta teks ke layar MainMenuScreen
        game.batch.begin();
        game.batch.draw(menuScreen,0,0);
        game.font.draw(game.batch, "Welcome to Space War", 245, 380);
        game.font.draw(game.batch, "Tap anywhere to begin", 245, 330);
        game.batch.end();

        if(Gdx.input.isTouched()){
            gameMusic.stop();
            gameMusic.dispose();
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
