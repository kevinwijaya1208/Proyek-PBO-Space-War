package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public abstract class EnemyShip extends SpaceShip{
    int score;

    public EnemyShip(int score){
        this.score=score;
    }

    public EnemyShip(float xPosition, float yPosition, float width, float height, int health, float movementSpeed, int score, Texture shipTexture, Texture laserTexture) {
        super(xPosition, yPosition, width, height, health, movementSpeed, shipTexture, laserTexture);
        this.score = score;
    }

    abstract void randomMove();
}
