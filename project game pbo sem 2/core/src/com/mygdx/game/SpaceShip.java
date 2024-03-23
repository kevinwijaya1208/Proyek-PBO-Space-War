package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SpaceShip extends SpriteBatch implements InterfaceMovement {
    // characteristic
    int health;
    float movementSpeed;

    // position
    Rectangle shipRectangle;

    // Texture
    Texture shipTexture;
    Texture laserTexture;

    public SpaceShip(){

    }

    public SpaceShip(float xPosition, float yPosition, float width, float height, int health, float movementSpeed,
                     Texture shipTexture, Texture laserTexture) {
        this.health = health;
        this.movementSpeed = movementSpeed;
        shipRectangle = new Rectangle(xPosition, yPosition, width, height);
        this.shipTexture = shipTexture;
        this.laserTexture = laserTexture;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(shipTexture, shipRectangle.x, shipRectangle.y, shipRectangle.width, shipRectangle.height);
    }

    public void movement(float x, float y) {
        this.shipRectangle.x = shipRectangle.x + x;
        this.shipRectangle.y = shipRectangle.y + y;
    }

}
