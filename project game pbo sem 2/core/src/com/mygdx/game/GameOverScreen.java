package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;


public class GameOverScreen implements Screen {
    private Texture gameOverScreen;
    final SpaceWar game;
    int score;
    OrthographicCamera camera;
    private Music gameMusic;

    public GameOverScreen(final SpaceWar game, int score){
        this.game = game;
        this.score = score;
        gameOverScreen = new Texture(Gdx.files.internal("backgroundMenu.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("MusicGame.mp3"));
        gameMusic.setLooping(true);
        gameMusic.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Menampilkan gambar serta teks ke layar GameOverScreen
        game.batch.begin();
        game.batch.draw(gameOverScreen,0,0);
        game.font.draw(game.batch, "Game Over", 320, 380);
        game.font.draw(game.batch, "Your Score: " + score, 270, 340);
        game.font.draw(game.batch, "Press Enter To Go Back To Main Menu", 130, 300);
        game.batch.end();

        // klik ENTER untuk balik ke MainMenuScreen
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            gameMusic.stop();
            gameMusic.dispose();
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

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
