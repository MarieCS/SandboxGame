package com.game.test.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.game.test.engine.OrderedSpriteBatch;

public class Tree {

    public static final float TREE_WIDTH = 3f;
    public static final float TREE_HEIGHT = TREE_WIDTH * 1.2f;
    private final Texture treeTexture;

    private Vector2 position;

    public Tree(Texture treeTexture, int x, int y) {
        this.treeTexture = treeTexture;
        this.position = new Vector2(x, y);
    }

    public void draw(OrderedSpriteBatch batch) {
        batch.draw(treeTexture, this.position.y + 0.2f, this.position.x, this.position.y, TREE_WIDTH, TREE_HEIGHT);
    }
}
