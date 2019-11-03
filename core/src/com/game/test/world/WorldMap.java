package com.game.test.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.game.test.engine.OrderedSpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class WorldMap {

    private List<Tree> treeList;
    private List<Grass> grassList;
    private Spawner spawner;
    private Texture treeTexture;
    private Texture grassTexture;
    private int[][] tiles;

    public List<Grass> getGrass() {
        return this.grassList;
    }

    public enum TexturesTiles {
        GRASS(1, new Texture("grass.png")),
        GROUND(2, new Texture("mountain_landscape_23.png"));

        Texture texture;
        int id;

        TexturesTiles(int id, Texture texture) {
            this.texture = texture;
        }

        public static Texture findById(int id) {
            if (GRASS.id == id) {
                return GRASS.texture;
            }
            return GROUND.texture;
        }
    }

    public WorldMap(int nbRow, int nbCol) {
        tiles = new int[nbRow][];
        for (int i = 0; i < nbRow; i++) {
            tiles[i] = new int[nbCol];

            for (int j = 0; j < nbCol; j++) {
                int rand = (int) (Math.random() * ((2 - 1) + 1));
                tiles[i][j] = rand;
            }
        }

        treeList = new ArrayList<Tree>();
        treeTexture = new Texture("tree.png");
        for (int i = 0; i < 30; i++) {
            treeList.add(new Tree(treeTexture, MathUtils.random(0, 55), MathUtils.random(0, 55)));
        }

        spawner = new Spawner(MathUtils.random(10, 10), MathUtils.random(10, 10));

        grassList = new ArrayList<Grass>();
        grassTexture = new Texture("grass_sprite.png");
        for (int i = 0; i < 50000; i++) {
            grassList.add(new Grass(grassTexture, MathUtils.random(0f, 59f), MathUtils.random(0f, 59f)));
        }
    }

    public void draw(OrderedSpriteBatch batch, OrthographicCamera cam, float deltaTime) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                batch.directDraw(TexturesTiles.findById(tiles[i][j]), i, j, 1, 1);
            }
        }
        for (Tree t : treeList) {
            t.draw(batch);
        }
        for (Grass g : grassList) {
            g.draw(batch);
        }

        spawner.draw(batch, deltaTime);
    }
}
