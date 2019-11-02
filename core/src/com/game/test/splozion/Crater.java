package com.game.test.splozion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Crater {

    private static final float CRATER_WIDHT = 2;
    private static final float CRATER_HEIGHT = 1.2f;
    private Texture crater;
    private Vector2 position;

    public Crater(Texture craterTexture, float x, float y) {
        this.crater = craterTexture;
        this.position = new Vector2(x, y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(crater, this.position.x - (CRATER_WIDHT/2), this.position.y - (CRATER_HEIGHT/2), CRATER_WIDHT, CRATER_HEIGHT);
    }
}
