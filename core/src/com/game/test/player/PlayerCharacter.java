package com.game.test.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class PlayerCharacter {

    public enum Direction { UP, RIGHT, DOWN, LEFT };

    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 3, FRAME_ROWS = 4;
    private static final int SPRITE_WIDTH = 24, SPRITE_HEIGHT = 32;

    private Texture spriteSheet;
    private List<Animation<TextureRegion>> walkAnimations;

    private Direction currentDirection = Direction.UP;
    private float xpos, ypos;
    private float stateTime;

    public PlayerCharacter(String spriteSheet) {
        this.spriteSheet = new Texture(spriteSheet);
        this.walkAnimations = new ArrayList<>();
    }

    public PlayerCharacter create(final int srcRow, final int srcCol) {
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(this.spriteSheet, SPRITE_WIDTH, SPRITE_HEIGHT);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] upWalkFrames = new TextureRegion[3];
        TextureRegion[] rightWalkFrames = new TextureRegion[3];
        TextureRegion[] downWalkFrames = new TextureRegion[3];
        TextureRegion[] leftWalkFrames = new TextureRegion[3];

        int index = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
            upWalkFrames[index] = tmp[srcRow][srcCol + i];
            rightWalkFrames[index] = tmp[srcRow + 1][srcCol + i];
            downWalkFrames[index] = tmp[srcRow + 2][srcCol + i];
            leftWalkFrames[index] = tmp[srcRow + 3][srcCol + i];
            index++;
        }

        walkAnimations.clear();
        walkAnimations.add(new Animation<TextureRegion>(0.25f, upWalkFrames));
        walkAnimations.add(new Animation<TextureRegion>(0.25f, rightWalkFrames));
        walkAnimations.add(new Animation<TextureRegion>(0.25f, downWalkFrames));
        walkAnimations.add(new Animation<TextureRegion>(0.25f, leftWalkFrames));

        walkAnimations.get(0).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        walkAnimations.get(1).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        walkAnimations.get(2).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        walkAnimations.get(3).setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        stateTime = 0f;

        return this;
    }

    public void draw(SpriteBatch batch, float deltaTime) {

        this.stateTime += deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.currentDirection = Direction.UP;
            this.ypos = ypos + 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.currentDirection = Direction.RIGHT;
            this.xpos = xpos + 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.currentDirection = Direction.DOWN;
            this.ypos = ypos - 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.currentDirection = Direction.LEFT;
            this.xpos = xpos - 2;
        } else {
            this.stateTime = 0.25f;

        }
        setCharracter();
        TextureRegion currentFrame = null;
        switch (currentDirection){
            case UP: {
                currentFrame = walkAnimations.get(0).getKeyFrame(stateTime, true);
                break;
            }
            case RIGHT: {
                currentFrame = walkAnimations.get(1).getKeyFrame(stateTime, true);
                break;
            }
            case DOWN: {
                currentFrame = walkAnimations.get(2).getKeyFrame(stateTime, true);
                break;
            }
            case LEFT: {
                currentFrame = walkAnimations.get(3).getKeyFrame(stateTime, true);
                break;
            }
            default: {

            }
        }
        batch.draw(currentFrame, this.xpos, this.ypos); // Draw current frame at (50, 50)
    }

    private void setCharracter() {
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1)) {
            create(0, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2)) {
            create(0, 3);
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_3)) {
            create(0, 6);
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
            create(0, 9);
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5)) {
            create(4, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) {
            create(4, 3);
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_7)) {
            create(4, 6);
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)) {
            create(4, 9);
        }

    }


    public void dispose() {
        this.spriteSheet.dispose();
    }
}
