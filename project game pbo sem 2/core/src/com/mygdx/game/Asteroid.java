package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Asteroid {
    int damage;
    float movementSpeed;

    Texture asteroidTexture;

    Rectangle asteroidRectangle;

    public Asteroid(float xPosition, float yPosition, float width, float height, int damage, float movementSpeed, Texture asteroidTexture) {
        asteroidRectangle = new Rectangle(xPosition, yPosition, width, height);
        this.damage = damage;
        this.movementSpeed = movementSpeed;
        this.asteroidTexture = asteroidTexture;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(asteroidTexture, asteroidRectangle.x, asteroidRectangle.y, asteroidRectangle.width, asteroidRectangle.height);
    }

}
