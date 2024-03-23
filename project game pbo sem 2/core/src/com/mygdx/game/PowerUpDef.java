package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PowerUpDef {
    float movementSpeed;

    Texture powerUpDefTexture;

    Rectangle powerUpDefRectangle;

    public PowerUpDef(float xPosition, float yPosition, float width, float height,  float movementSpeed, Texture powerUpDefTexture) {
        powerUpDefRectangle = new Rectangle(xPosition, yPosition, width, height);
        this.movementSpeed = movementSpeed;
        this.powerUpDefTexture = powerUpDefTexture;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(powerUpDefTexture, powerUpDefRectangle.x, powerUpDefRectangle.y, powerUpDefRectangle.width, powerUpDefRectangle.height);
    }
}
