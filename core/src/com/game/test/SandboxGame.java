package com.game.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
		cam.position.set(0, 0, 0);
        //cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
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

		Vector2 pp = player.getPlayerPosition();
		cam.position.set(pp.x, pp.y, 0);
		cam.update();

		float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
		float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

		cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 30 - effectiveViewportWidth / 2f);
		cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 30 - effectiveViewportHeight / 2f);
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
