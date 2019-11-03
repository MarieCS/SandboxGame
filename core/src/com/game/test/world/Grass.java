package com.game.test.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.test.engine.OrderedSpriteBatch;

public class Grass {

    public enum GrassState { OK, CRAME }

    public static final float GRASS_WIDTH = 1f;
    public static final float GRASS_HEIGHT = 1f;

    private final Texture grassTexture;
    private GrassState currentState;

    private Vector2 position;

    public Grass(Texture treeTexture, float x, float y) {
        this.grassTexture = treeTexture;
        this.position = new Vector2(x, y);
        this.currentState =  GrassState.OK;
    }

    public Vector2 getPositionCentered() {
        return new Vector2(this.position.x + GRASS_WIDTH /2, this.position.y);
    }

    public void setCrame() {
        this.currentState = GrassState.CRAME;
    }

    public void draw(OrderedSpriteBatch batch) {
        if (currentState.equals(GrassState.OK)) {
            batch.draw(grassTexture, this.position.x, this.position.y, GRASS_WIDTH, GRASS_HEIGHT);
            //updateHitbox();
            //batch.addHitbox(getHitbox());
        } /*else if (currentState.equals(GrassState.CRAME)) {
            System.exit(0);
            batch.draw(grassTexture, this.position.x, this.position.y, GRASS_WIDTH, GRASS_HEIGHT, new Color(1, 0, 0, 1));
        }*/
    }
}
