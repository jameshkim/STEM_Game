package com.stem.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen {
	
	final StemGame game;
	final levelResult lvlRslt;
	OrthographicCamera camera;
	
	public MainMenuScreen(final StemGame gam) {
        this.game = gam;
        this.lvlRslt = new levelResult();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.titleFont.draw(game.batch, "Cognitive Constructor", 190, 325);
        game.byFont.draw(game.batch, "by the Se7en Wonders", 290, 250);
        game.tapFont.draw(game.batch, "Tap anywhere to begin!", 240, 175);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game, lvlRslt));
            dispose();
        }
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
