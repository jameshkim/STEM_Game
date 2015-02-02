package com.stem.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class StemGame extends ApplicationAdapter {
	SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Texture blankStop;
	private Texture yellDiam;
	private	Texture	blackCheck;
	
	private Texture empRect;
	
	private Rectangle stopSign;
	private	Rectangle obstacle;
	private Vector3 touchPos;
	
	private Array<Rectangle> stopSigns;
	
	private Array<Rectangle> obstacles;

	//	private long lastObstacles;
	
	
	@Override
	public void create () {
		blankStop = new Texture(Gdx.files.internal("blank_stop.png"));
		empRect = new Texture(Gdx.files.internal("regrect.png"));
		yellDiam = new Texture(Gdx.files.internal("yellow_diamond.png"));
		blackCheck  = new Texture(Gdx.files.internal("lvlcheckmark.png"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		stopSign = new Rectangle();
		stopSign.x = 800 / 2 - 64 / 2;
		stopSign.y = 20;
		stopSign.width = 64;
		stopSign.height = 64;
		
		obstacle = new Rectangle();
		obstacle.x = 800 / 2 - 64 / 2;
		obstacle.y = 20;
		obstacle.width = 64;
		obstacle.height = 64;
		
		batch.begin();
		batch.draw(yellDiam, obstacle.x, obstacle.y);
		batch.draw(blankStop, stopSign.x, stopSign.y);
		batch.end();

		stopSigns = new Array<Rectangle>();
		spawnStopSigns();
		

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		camera.update();
		batch.setProjectionMatrix(camera.combined);	
		batch.begin();
		batch.draw(blankStop, stopSign.x, stopSign.y);
		for(Rectangle stopSignRect: stopSigns) {
			batch.draw(blankStop, stopSignRect.x, stopSignRect.y);
		}
		batch.end();
		
		if(Gdx.input.isTouched()) {
			touchPos = new Vector3();
	      	touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	     	camera.unproject(touchPos);
	     	if (touchPos.x > stopSign.getX() && touchPos.x < stopSign.getX() + stopSign.getWidth()) {
	     		if (touchPos.y > stopSign.getY() && touchPos.y < stopSign.getY() + stopSign.getHeight()) {
	     			
	     		}
            }
	     	stopSign.x = touchPos.x - 64 / 2;
		}			

		
//		Use for snowflake level
//		if(TimeUtils.nanoTime() - lastDropTime > 1000000000)  {
//			spawnRaindrop();
//		}
		
//		Use this to make snowflakes move
//		Iterator<Rectangle> iter = raindrops.iterator();
//		   while(iter.hasNext()) {
//		      Rectangle raindrop = iter.next();
//		      raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
//		      if(raindrop.y + 64 < 0) iter.remove();
//		}
	}
	
	private void spawnStopSigns() {
	      Rectangle blank_stop = new Rectangle();
	      blank_stop.x = MathUtils.random(0, 800-64);
	      blank_stop.y = MathUtils.random(0, 480-64);;
	      blank_stop.width = 64;
	      blank_stop.height = 64;
	      stopSigns.add(blank_stop);
	      lastStopSign = TimeUtils.nanoTime();
   }
	
	public void dispose() {
		blankStop.dispose();
		empRect.dispose();
		yellDiam.dispose();
		blackCheck.dispose();
		batch.dispose();
   }
	
   @Override
   public void resize(int width, int height) {
   }

   @Override
   public void pause() {
   }

   @Override
   public void resume() {
   }
}