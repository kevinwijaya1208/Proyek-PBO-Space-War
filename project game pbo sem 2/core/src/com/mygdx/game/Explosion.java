package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Explosion {
    private Animation<TextureRegion> explosionAnimation;
    private float explosionTimer;

    private Rectangle explosionBox; //pakai rectangle karena di dalam rectangle ada posisi x,y ukuran width dan height

    public Explosion(Texture texture, Rectangle explosionBox, float totalTimeAnimation) {
        this.explosionBox = explosionBox;

        //membagi texture (ada 4 x 4 frame)
        TextureRegion[][] textureRegion2D = TextureRegion.split(texture, 64, 64);

        //convert to 1d array
        TextureRegion[] textureRegion1D = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < 4; i++) { //i < 4 karena array 2d nya 4 x 4
            for (int j = 0; j < 4; j++) {
                textureRegion1D[index] = textureRegion2D[i][j];
                index++;
            }
        }
        explosionAnimation = new Animation<>(totalTimeAnimation / 16, textureRegion1D);
        explosionTimer = 0;
    }

    public void update(float deltaTime) {
        explosionTimer += deltaTime;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(explosionAnimation.getKeyFrame(explosionTimer), explosionBox.x,
                explosionBox.y, explosionBox.width, explosionBox.height);
    }

    public boolean selesai() {
        //untuk mnegecek apakah animasi sudah selesai atau belum
        return explosionAnimation.isAnimationFinished(explosionTimer);
    }
}
