package com.game.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.game.test.collision.Collision;
import com.game.test.engine.OrderedSpriteBatch;
import com.game.test.player.PlayerCharacter;
import com.game.test.player.Pnj;
import com.game.test.world.WorldMap;

import java.util.List;

public class SandboxGame extends ApplicationAdapter {

	private static final float CAM_VIEWPORT_WIDTH = 20f;
	private static final float CAM_VIEWPORT_HEIGHT = 20f;

    private OrthographicCamera cam;
    private WorldMap worldMap;
	private PlayerCharacter player;
	private List<Pnj> pnjs = Pnj.pnjList;
	private OrderedSpriteBatch orderedSpriteBatch;
	private SpriteBatch batch;

	private Collision collision;

	private SpriteBatch guiBatch;
	private BitmapFont font;

	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(1280, 800);

		batch = new SpriteBatch();
		orderedSpriteBatch = new OrderedSpriteBatch(batch);
		guiBatch = new SpriteBatch();

		font = new BitmapFont();

		worldMap = new WorldMap(60, 60);
		player = new PlayerCharacter("charset-test.png");
		player.create(4, 0);
		pnjs.add(new Pnj());
		worldMap = new WorldMap(60, 60);

		collision = new Collision();
		collision.setPnj(pnjs);
		Gdx.graphics.setWindowedMode(1280, 800);


		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        cam = new OrthographicCamera(CAM_VIEWPORT_WIDTH, CAM_VIEWPORT_HEIGHT * (h / w));
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
		worldMap.draw(orderedSpriteBatch);
		pnjs.stream().forEach(p -> p.draw(orderedSpriteBatch, deltaTime));
		if (!Pnj.pnjToAdd.isEmpty()) {
			pnjs.addAll(Pnj.pnjToAdd);
			Pnj.pnjToAdd.stream().forEach(p -> p.draw(orderedSpriteBatch, deltaTime));
			Pnj.pnjToAdd.clear();
		}
		player.draw(orderedSpriteBatch, deltaTime);
		orderedSpriteBatch.render();
		batch.end();

		collision.verify();
		guiBatch.begin();
		font.getData().setScale(2f);
		font.draw(guiBatch, "Pokelda", 20, 30);
		guiBatch.end();

		Vector2 pp = player.getPlayerPosition();
		cam.position.set(pp.x, pp.y, 0);
		cam.update();

		float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
		float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

		cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 60 - effectiveViewportWidth / 2f);
		cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 60 - effectiveViewportHeight / 2f);
	}

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = CAM_VIEWPORT_WIDTH;
        cam.viewportHeight = CAM_VIEWPORT_HEIGHT * height/width;
        cam.update();
    }

    @Override
	public void dispose () {
		batch.dispose();
		player.dispose();
	}
}
