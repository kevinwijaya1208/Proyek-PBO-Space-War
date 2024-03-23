package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Laser {

    int damage;
    float movementSpeed;

    Texture laserTexture;

    Rectangle laserRectangle;

    public Laser(float xPosition, float yPosition, float width, float height, int damage, float movementSpeed, Texture laserTexture){
        laserRectangle = new Rectangle(xPosition,yPosition,width, height);
        this.damage = damage;
        this.movementSpeed = movementSpeed;
        this.laserTexture = laserTexture;
    }

    public void draw(SpriteBatch batch){
        batch.draw(laserTexture,laserRectangle.x ,laserRectangle.y,laserRectangle.width,laserRectangle.height);
    }

}
