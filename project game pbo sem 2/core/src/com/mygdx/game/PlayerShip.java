package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerShip extends SpaceShip{

    public PlayerShip(){

    }

    public PlayerShip(float xPosition, float yPosition, float width, float height, int health, float movementSpeed, Texture shipTexture, Texture laserTexture) {
        super(xPosition, yPosition, width, height, health, movementSpeed, shipTexture, laserTexture);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(shipTexture, shipRectangle.x, shipRectangle.y, shipRectangle.width, shipRectangle.height);
    }

    @Override
    public void movement(float x, float y){
        this.shipRectangle.x = shipRectangle.x + x;
        this.shipRectangle.y = shipRectangle.y + y;
        if (shipRectangle.x < 0)
            shipRectangle.x = 0;
        if (shipRectangle.x > 800 - shipRectangle.width)
            shipRectangle.x = 800 - shipRectangle.width;
        if (shipRectangle.y < shipRectangle.height / 2 - 20)
            shipRectangle.y = shipRectangle.height / 2 - 20;
        if (shipRectangle.y > 300 - shipRectangle.height/2)
            shipRectangle.y = 300 - shipRectangle.height/2;
    }

    // untuk gerak player ship menggunakan keyboard (Arrow Key / WASD)
    public void KeyboardMove(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            movement(-movementSpeed * Gdx.graphics.getDeltaTime(), 0f);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            movement(movementSpeed * Gdx.graphics.getDeltaTime(), 0f);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            movement(0f, -movementSpeed * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            movement(0f, movementSpeed * Gdx.graphics.getDeltaTime());
    }
}
