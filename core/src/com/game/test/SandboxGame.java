package com.game.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.test.player.PlayerCharacter;

public class SandboxGame extends ApplicationAdapter {

	private PlayerCharacter player;
	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new PlayerCharacter("charset-test.png");
		player.create(4, 0);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float deltaTime = Gdx.graphics.getDeltaTime();

		batch.begin();
		player.draw(batch, deltaTime);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
	}
}
