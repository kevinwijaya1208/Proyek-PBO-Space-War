package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class EnemyShip1 extends EnemyShip{

    Random random = new Random();
    Vector2 directionVector;

    public EnemyShip1(float xPosition, float yPosition, float width, float height, int health, float movementSpeed, int score, Texture shipTexture, Texture laserTexture) {
        super(xPosition, yPosition, width, height, health, movementSpeed, score, shipTexture, laserTexture);

        directionVector = new Vector2();
    }

    @Override
    void randomMove() {
        // gerak kiri-kanan random
        directionVector.x = (float) Math.sin((random.nextDouble() * 2 * Math.PI));
        directionVector.y = 0;
        movement(directionVector.x * Gdx.graphics.getDeltaTime() * movementSpeed,0);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(shipTexture, shipRectangle.x, shipRectangle.y, shipRectangle.width, shipRectangle.height);
    }

    @Override
    public void movement(float x, float y){
        this.shipRectangle.x = shipRectangle.x + x;
        this.shipRectangle.y = shipRectangle.y + y;
        // batasan geraknya
        if (shipRectangle.x < 0)
            shipRectangle.x = 0;
        if (shipRectangle.x > 800 - shipRectangle.width)
            shipRectangle.x = 800 - shipRectangle.width;
        if (shipRectangle.y < 320)
            shipRectangle.y = 320;
        if (shipRectangle.y > 640 - shipRectangle.height)
            shipRectangle.y = 640 - shipRectangle.height;
    }

}
