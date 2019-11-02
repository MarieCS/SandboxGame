package com.game.test.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldMap {


    public enum TexturesTiles {
        GRASS(1, new Texture("grass.png"));

        Texture texture;
        int id;

        TexturesTiles(int id, Texture texture) {
            this.texture = texture;

        }

        public static Texture findById(int id) {
            return GRASS.texture;
        }
    }


    int[][] tiles;


    public WorldMap(int nbRow, int nbCol) {
        tiles = new int[nbRow][];
        for (int i = 0; i < nbRow; i++) {
            tiles[i] = new int[nbCol];

            for (int j = 0; j < nbCol; j++) {
                tiles[i][j] = TexturesTiles.GRASS.id;

            }

        }
    }


    public void draw(SpriteBatch batch) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {

                batch.draw(TexturesTiles.findById(tiles[i][j]), i, j, 1, 1);

            }
        }
    }

}
