package com.game.test.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.game.test.engine.OrderedSpriteBatch;
import com.game.test.player.Monster;

public class Spawner {

    public static final float SPAWNER_WIDTH = 4f;
    public static final float SPAWNER_HEIGHT = SPAWNER_WIDTH * 1.2f;
    private static final float timeToRespawn = 5;
    private static final int MAX_MONSTERS = 8;
    private final Texture spawnerTexture;

    private Vector2 position;
    private float timerSwpan;

    public Spawner(int x, int y) {
        this.spawnerTexture = new Texture("spawner.png");
        this.position = new Vector2(x, y);

        spwanMonster();

    }

    public void draw(OrderedSpriteBatch batch, float deltaTime) {
        timerSwpan += deltaTime;
        if (timerSwpan >= timeToRespawn) {
            spwanMonster();
        }
        batch.draw(spawnerTexture, this.position.y + 0.2f, this.position.x, this.position.y, SPAWNER_WIDTH, SPAWNER_HEIGHT);
    }

    private void spwanMonster() {
        timerSwpan = 0;
        if (Monster.MONSTER_LIST.size() < MAX_MONSTERS) {
            int randMonsterType = MathUtils.random(7);
            Monster.createMonster(Monster.MonsterType.values()[randMonsterType], position.x + (SPAWNER_WIDTH / 2), position.y);
        }
    }
}
