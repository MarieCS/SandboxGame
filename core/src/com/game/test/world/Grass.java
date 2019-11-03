package com.game.test.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.game.test.engine.OrderedSpriteBatch;

public class Grass {

    public static final float TREE_WIDTH = 1f;
    public static final float TREE_HEIGHT = 1f;
    private final Texture grassTexture;

    private Vector2 position;

    public Grass(Texture treeTexture, float x, float y) {
        this.grassTexture = treeTexture;
        this.position = new Vector2(x, y);
    }

    public void draw(OrderedSpriteBatch batch) {
        batch.draw(grassTexture, this.position.y, this.position.x, this.position.y, TREE_WIDTH, TREE_HEIGHT);
    }
}
