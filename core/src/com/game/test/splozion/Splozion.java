package com.game.test.splozion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Splozion {

    private float stateTime;
    float xpos;
    float ypos;
    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 9, FRAME_ROWS = 9;
    private static final float SPLOZION_WIDTH = 5f, SPLOZION_HEIGHT = 5f;

    private Texture spriteSheet;
    private Animation<TextureRegion> animation;

    private boolean isPlayed = false;

    public Splozion(String spriteSheet) {
        this.spriteSheet = new Texture(spriteSheet);
    }

    public void create() {
        TextureRegion textureRegion = new TextureRegion(spriteSheet);
        TextureRegion[][] tmp = textureRegion.split(textureRegion.getRegionWidth() / FRAME_COLS, textureRegion.getRegionHeight() / FRAME_ROWS);
        TextureRegion[] explosionFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                explosionFrames[index++] = tmp[i][j];
            }
        }

        animation = new Animation<TextureRegion>(0.03f, explosionFrames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public void draw(SpriteBatch batch, float deltaTime) {
        this.stateTime += deltaTime;

        if (animation.isAnimationFinished(this.stateTime)) {
            this.isPlayed = false;
        } else if (isPlayed) {
            TextureRegion frame = animation.getKeyFrame(stateTime, false);
            batch.draw(frame, xpos - (SPLOZION_WIDTH/2), ypos- (SPLOZION_HEIGHT/2), SPLOZION_WIDTH, SPLOZION_HEIGHT);
        }
    }

    public void setPlaying(boolean b, float xpos, float ypos) {
        this.isPlayed = b;
        this.stateTime = 0f;
        this.xpos = xpos;
        this.ypos = ypos;
    }
}
