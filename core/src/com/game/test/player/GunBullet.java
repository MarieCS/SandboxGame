package com.game.test.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GunBullet {
    private Vector2 position = new Vector2(0, 0);
    private static final float POKEBALL_WIDTH = 0.5f, POKEBALL_HEIGHT = 0.5f;
    private Direction currentDirection = Direction.UP;
    private static final float velocity = 10;

    private Texture spriteSheet;
    private boolean isLaunched = false;


    public GunBullet(String spriteSheet) {
        this.spriteSheet = new Texture(spriteSheet);
    }

    public void draw(SpriteBatch batch, float deltaTime) {
        if (isLaunched) {
            switch (currentDirection) {
                case UP:
                    this.position.y += velocity * deltaTime;
                    break;
                case RIGHT:
                    this.position.x += velocity * deltaTime;
                    break;
                case DOWN:
                    this.position.y -= velocity * deltaTime;
                    break;
                case LEFT:
                    this.position.x -= velocity * deltaTime;
                    break;
            }

            batch.draw(spriteSheet, position.x, position.y, POKEBALL_WIDTH, POKEBALL_HEIGHT);
        }


    }

    public void create(float xpos, float ypos, Direction direction) {
        this.position.x = xpos;
        this.position.y = ypos;
        this.currentDirection = direction;
        this.isLaunched = true;
    }
}
