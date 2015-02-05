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
	private float totalTime;
	final levelResult lvlRslt;

	
	public NextLevelScreen(final StemGame gam, levelResult lvlRsltInstance) {
        this.game = gam;
        this.lvlRslt = lvlRsltInstance;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

		retry = new Texture(Gdx.files.internal("re_try.png"));
		next = new Texture(Gdx.files.internal("next.png"));

		totalTime = 0;
		
		
    }
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
		Gdx.app.log("ErrorCheckTag", "" + lvlRslt.gameTime);

        float minutes = (float)Math.floor(lvlRslt.gameTime / 60.0f);
        float seconds = lvlRslt.gameTime - minutes * 60.0f;
        game.batch.begin();
		game.tapFont.draw(game.batch, "Level : " + lvlRslt.gameLevel, 190, 325);
		game.tapFont.draw(game.batch, "Level Score : " + String.format("%.0fm%.0fs", minutes, seconds), 190, 250);
//		game.batch.draw(retry, 500, 200, 100, 200);
//		game.batch.draw(next, 500, 100, 100, 200);
        game.batch.end();

        totalTime += delta;
        if(totalTime > 1.0f) {
        
	        if (Gdx.input.isTouched()) {			
	        	lvlRslt.setLvl(lvlRslt.gameLevel + 1);
	            game.setScreen(new GameScreen(game, lvlRslt));
	            dispose();
	        }
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
