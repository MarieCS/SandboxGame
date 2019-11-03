package com.game.test.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GunBullet {

    private static final float velocity = 10;
    private static final float POKEBALL_WIDTH = 0.5f, POKEBALL_HEIGHT = 0.5f;
    public static final List<GunBullet> pool = Arrays.asList(new GunBullet(), new GunBullet(), new GunBullet(), new GunBullet(), new GunBullet());

    private boolean isLaunched = false;
    private Vector2 position = new Vector2(0, 0);
    private Direction currentDirection = Direction.UP;
    private static final Texture spriteSheet = new Texture("pokeball.png");

    private GunBullet() {
    }

    public static void draw(SpriteBatch batch, float deltaTime) {

        for (GunBullet gunBullet : pool) {
            if (gunBullet.isLaunched) {

                switch (gunBullet.currentDirection) {
                    case UP:
                        gunBullet.position.y += velocity * deltaTime;
                        break;
                    case RIGHT:
                        gunBullet.position.x += velocity * deltaTime;
                        break;
                    case DOWN:
                        gunBullet.position.y -= velocity * deltaTime;
                        break;
                    case LEFT:
                        gunBullet.position.x -= velocity * deltaTime;
                        break;
                }

                if (gunBullet.position.x <= 0 || gunBullet.position.x >= 60 - POKEBALL_WIDTH
                        || gunBullet.position.y <= 0 || gunBullet.position.y >= 60 - POKEBALL_HEIGHT) {
                    gunBullet.isLaunched = false;
                } else {
                    batch.draw(spriteSheet, gunBullet.position.x, gunBullet.position.y, POKEBALL_WIDTH, POKEBALL_HEIGHT);
                }
            }


        }
    }

    public static void create(float xpos, float ypos, Direction direction) {

        Optional<GunBullet> opt = pool.stream().filter(g -> !g.isLaunched).findAny();

        if (opt.isPresent()) {
            GunBullet gunBullet = opt.get();
            gunBullet.position.x = xpos - (POKEBALL_WIDTH / 2);
            gunBullet.position.y = ypos - (POKEBALL_HEIGHT / 2);
            gunBullet.currentDirection = direction;
            gunBullet.isLaunched = true;
        }
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public Vector2 getPosition() {
        return position;
    }
}
