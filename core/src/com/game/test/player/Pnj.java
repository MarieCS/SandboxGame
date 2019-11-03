package com.game.test.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.game.test.engine.OrderedSpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pnj {
    public static final List<Pnj> pnjList = new ArrayList<>();
    public static final List<Pnj> pnjToAdd = new ArrayList<>();
    private static final int FRAME_COLS = 12, FRAME_ROWS = 8;
    public static final int SPRITE_WIDTH = 24, SPRITE_HEIGHT = 32;

    public static final float PNJ_WIDTH = 1f, PNJ_HEIGHT = 1.5f;
    public static final float PNJ_DEAD_WIDTH = 1.5f, PNJ_DEAD_HEIGHT = 1f;

    private static final float DIRECTION_TIMER_LIMIT = 1;
    private static final float VNR_TIMER_LIMIT = 2;
    private static final List<Direction> path = Arrays.asList(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT);

    private Texture spriteSheet;
    private Texture spriteDead;
    private TextureRegion deadLinkTexture;
    private List<Animation<TextureRegion>> walkAnimations;
    private float stateTime;
    private float frameDuration = 0.01f;
    private Direction currentDirection = Direction.UP;
    private float velocity = 5;
    private Vector2 position = new Vector2(0, 0);
    private float directionTimer = 0;
    private float vnrStateTimer = 0;
    private Etat etat;

    public Vector2 getPositionCentered() {
        return new Vector2(this.position.x + (PNJ_WIDTH/2), this.position.y + (PNJ_HEIGHT/2));
    }

    public enum Etat {
        NORMAL(5),
        VNR(10),
        DEAD( 0);

        float velocity;

        Etat(float velocity) {
            this.velocity = velocity;
        }


    }
    public Pnj() {
        spriteSheet = new Texture("pnj1.png");
        spriteDead = new Texture("dead_link.png");
        deadLinkTexture = new TextureRegion(spriteDead);

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

        etat = Etat.NORMAL;
    }

    public void draw(OrderedSpriteBatch batch, float deltaTime) {

        if (this.etat != Etat.DEAD) {
            this.stateTime += deltaTime;
            this.directionTimer += deltaTime;
            this.vnrStateTimer += deltaTime;


            if (directionTimer >= DIRECTION_TIMER_LIMIT) {
                currentDirection = path.get(MathUtils.random(0, 3));
                directionTimer = 0;
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
            }

            this.position.x = MathUtils.clamp(this.position.x, 0, 60 - PNJ_WIDTH);
            this.position.y = MathUtils.clamp(this.position.y, 0, 60 - PNJ_HEIGHT);

            if (this.position.x <= 0 || this.position.x >= 60 - PNJ_WIDTH
                    || this.position.y <= 0 || this.position.y >= 60 - PNJ_HEIGHT) {
                currentDirection = path.get(MathUtils.random(0, 3));
            }

            if (this.etat == Etat.VNR) {
                batch.draw(currentFrame, position.x, position.y, PNJ_WIDTH, PNJ_HEIGHT, new Color(1, 0, 0, 1));

                if (vnrStateTimer >= VNR_TIMER_LIMIT) {

                    setEtat(Etat.NORMAL);

                    addPnj(this.position);
                }
            } else {
                batch.draw(currentFrame, position.x, position.y, PNJ_WIDTH, PNJ_HEIGHT);
            }
        } else if(this.etat == Etat.DEAD) {
            batch.draw(deadLinkTexture, position.x, position.y, PNJ_DEAD_WIDTH, PNJ_DEAD_HEIGHT);
        }
    }

    public static void addPnj(Vector2 position) {
        Pnj newPnj = new Pnj();
        newPnj.setPosition(new Vector2(position.x, position.y));

        pnjToAdd.add(newPnj);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
        velocity = etat.velocity;
        vnrStateTimer = 0;
    }

    public float getVnrStateTimer() {
        return vnrStateTimer;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
