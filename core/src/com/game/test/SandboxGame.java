package com.game.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.test.player.PlayerCharacter;
import com.game.test.world.WorldMap;

public class SandboxGame extends ApplicationAdapter {

    private OrthographicCamera cam;
    private WorldMap worldMap;
	private PlayerCharacter player;
	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new PlayerCharacter("charset-test.png");
		player.create(4, 0);
		worldMap = new WorldMap(60, 60);
		Gdx.graphics.setWindowedMode(1280,800);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        cam = new OrthographicCamera(30, 30 * (h / w));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();


    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);

		float deltaTime = Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
		   System.exit(0);
        }

		batch.begin();
        worldMap.draw(batch);
		player.draw(batch, deltaTime);
		batch.end();
	}

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 30f;
        cam.viewportHeight = 30f * height/width;
        cam.update();
    }

    @Override
	public void dispose () {
		batch.dispose();
		player.dispose();
	}
}
