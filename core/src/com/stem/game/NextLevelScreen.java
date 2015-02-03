package com.stem.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class NextLevelScreen implements Screen {
	
	final StemGame game;
	float gameTime;
	OrthographicCamera camera;
	private Texture retry;
	private Texture next;
	
	public NextLevelScreen(final StemGame gam, float gameTime) {
        this.game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

		retry = new Texture(Gdx.files.internal("re_try.png"));
		next = new Texture(Gdx.files.internal("next.png"));
    }
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        float minutes = (float)Math.floor(gameTime / 60.0f);
        float seconds = gameTime - minutes * 60.0f;
        game.batch.begin();
		game.font.draw(game.batch, "Level Score : " + String.format("%.0fm%.0fs", minutes, seconds), 300, 480);
//		game.batch.draw(retry, 500, 200, 100, 200);
//		game.batch.draw(next, 500, 100, 100, 200);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
