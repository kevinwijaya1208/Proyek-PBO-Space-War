package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PowerUpAtt {

    float movementSpeed;

    Texture powerUpAttTexture;

    Rectangle powerUpRectangle;

    public PowerUpAtt(float xPosition, float yPosition, float width, float height, float movementSpeed, Texture powerUpAttTexture) {
        powerUpRectangle = new Rectangle(xPosition, yPosition, width, height);
        this.movementSpeed = movementSpeed;
        this.powerUpAttTexture = powerUpAttTexture;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(powerUpAttTexture, powerUpRectangle.x, powerUpRectangle.y, powerUpRectangle.width, powerUpRectangle.height);
    }

}
