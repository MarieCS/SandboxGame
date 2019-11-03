package com.game.test.splozion;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.game.test.engine.OrderedSpriteBatch;

public class Crater {

    private static final float CRATER_WIDHT = 2;
    private static final float CRATER_HEIGHT = 1.2f;
    private static final float START_SHADE = 10f;
    private Texture crater;
    private Vector2 position;
    private float livingTime;
    private static float shader = 0.05f;
    private float currentAlpha;


    public Crater(Texture craterTexture, float x, float y) {
        this.crater = craterTexture;
        this.position = new Vector2(x, y);
        this.currentAlpha = 1;
    }

    public boolean isStillAlive() {
        return currentAlpha > 0;
    }

    public void draw(OrderedSpriteBatch batch, float deltaTime) {
        this.livingTime += deltaTime;

        if (livingTime >= START_SHADE) {
            currentAlpha = currentAlpha - (currentAlpha * shader);
        }

        batch.directDraw(crater,
                this.position.x - (CRATER_WIDHT / 2),
                this.position.y - (CRATER_HEIGHT / 2),
                CRATER_WIDHT, CRATER_HEIGHT,
                new Color(1, 1, 1, currentAlpha));
    }
}
