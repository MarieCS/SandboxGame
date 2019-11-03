package com.game.test.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pnj {
    private static final int FRAME_COLS = 12, FRAME_ROWS = 8;
    public static final int SPRITE_WIDTH = 24, SPRITE_HEIGHT = 32;
    public static final float PNJ_WIDTH = 1f, PNJ_HEIGHT = 1.5f;
    private Texture spriteSheet;
    private List<Animation<TextureRegion>> walkAnimations;
    private float stateTime;
    private float frameDuration = 0.01f;
    private Direction currentDirection = Direction.UP;
    private float velocity = 5;
    private Vector2 position = new Vector2(0, 0);
    private List<Direction> path = Arrays.asList(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT);
    private static final float directionTimer = 1;
    private float timer = 0;

    public Pnj() {
        spriteSheet = new Texture("pnj1.png");
        position.x = 0;
        position.y = 0;
        TextureRegion[][] tmp = TextureRegion.split(this.spriteSheet, SPRITE_WIDTH, SPRITE_HEIGHT);

        TextureRegion[] upWalkFrames = new TextureRegion[FRAME_COLS * 2];
        TextureRegion[] rightWalkFrames = new TextureRegion[FRAME_COLS * 2];
        TextureRegion[] downWalkFrames = new TextureRegion[FRAME_COLS * 2];
        TextureRegion[] leftWalkFrames = new TextureRegion[FRAME_COLS * 2];

        int index = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
            upWalkFrames[index] = tmp[0][i];
            rightWalkFrames[index] = tmp[1][i];
            downWalkFrames[index] = tmp[2][i];
            leftWalkFrames[index] = tmp[3][i];
            index++;
        }

        for (int i = 0; i < FRAME_COLS; i++) {
            upWalkFrames[index] = tmp[4][i];
            rightWalkFrames[index] = tmp[5][i];
            downWalkFrames[index] = tmp[6][i];
            leftWalkFrames[index] = tmp[7][i];
            index++;
        }

        walkAnimations = new ArrayList<>();

        walkAnimations.add(new Animation<>(frameDuration, upWalkFrames));
        walkAnimations.add(new Animation<>(frameDuration, rightWalkFrames));
        walkAnimations.add(new Animation<>(frameDuration, downWalkFrames));
        walkAnimations.add(new Animation<>(frameDuration, leftWalkFrames));

        walkAnimations.get(0).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        walkAnimations.get(1).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        walkAnimations.get(2).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        walkAnimations.get(3).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        stateTime = 0f;
    }

    public void draw(SpriteBatch batch, float deltaTime) {
        this.stateTime += deltaTime;
        this.timer += deltaTime;

        if (timer >= directionTimer) {
            currentDirection = path.get((path.indexOf(currentDirection) + 1) % 4);
            timer = 0;
        }


        TextureRegion currentFrame = null;
        switch (currentDirection) {
            case UP: {
                this.position.y = position.y + velocity * deltaTime;
                currentFrame = walkAnimations.get(0).getKeyFrame(stateTime, true);
                break;
            }
            case RIGHT: {
                this.position.x = position.x + velocity * deltaTime;
                currentFrame = walkAnimations.get(1).getKeyFrame(stateTime, true);
                break;
            }
            case DOWN: {
                this.position.y = position.y - velocity * deltaTime;
                currentFrame = walkAnimations.get(2).getKeyFrame(stateTime, true);
                break;
            }
            case LEFT: {
                this.position.x = position.x - velocity * deltaTime;
                currentFrame = walkAnimations.get(3).getKeyFrame(stateTime, true);
                break;
            }
            default: {

            }
        }

        batch.draw(currentFrame, position.x, position.y, PNJ_WIDTH, PNJ_HEIGHT);
    }

    public Vector2 getPosition() {
        return position;
    }
}
