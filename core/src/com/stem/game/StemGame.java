package com.stem.game;

import java.util.Iterator;
import java.util.Random;

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
	
//	Stop Signs
	private Texture blankStop;
	
//	Success images
	private	Texture	blackCheck;
	
//	Obstacles images
	private Texture yellDiam;
	private Texture empRect;
	
//	Rectangles used to logically represent objects	
	private Rectangle stopSign;
	private	Rectangle obstacle;
	private Rectangle blackCheckRect;
	
	private Vector3 touchPos;
	
	private Array<Rectangle> stopSigns;
	private Array<Rectangle> obstacles;

	//	private long snowFlakeTimes;
	
	@Override
	public void create () {
		blankStop = new Texture(Gdx.files.internal("blank_stop.png"));
		yellDiam = new Texture(Gdx.files.internal("yellow_diamond.png"));
		empRect = new Texture(Gdx.files.internal("regrect.png"));
		blackCheck  = new Texture(Gdx.files.internal("lvlcheckmark.png"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		stopSign = new Rectangle();
		stopSign.x = 5;
		stopSign.y = 5;
		stopSign.width = 50;
		stopSign.height = 50;
		
		obstacle = new Rectangle();
		obstacle.x = 20;
		obstacle.y = 50;
		obstacle.width = 10;
		obstacle.height = 10;
		
		blackCheckRect = new Rectangle();
		blackCheckRect.x = 800 / 2 - 64 / 2;
		blackCheckRect.y = 50;
		blackCheckRect.width = 64;
		blackCheckRect.height = 64;

		stopSigns = new Array<Rectangle>();
		obstacles = new Array<Rectangle>();
		Random randGen = new Random();
		for (int i = 0; i < randGen.nextInt(10); i++) {
			spawnStopSigns();
			spawnObstacles();
		}

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		camera.update();
		batch.setProjectionMatrix(camera.combined);	
		batch.begin();
		for(Rectangle stopSignRect: stopSigns) {
			batch.draw(blankStop, stopSignRect.x, stopSignRect.y, stopSignRect.getWidth(), stopSignRect.getHeight());
		}
		
//		Random randGen = new Random();
//		for(Rectangle obstacle: obstacles) {
//			if (randGen.nextInt(10) > 5) {
//				batch.draw(yellDiam, obstacle.x, obstacle.y, obstacle.getWidth(), obstacle.getHeight());
//			} else {
//				batch.draw(empRect, obstacle.x, obstacle.y, obstacle.getWidth(), obstacle.getHeight());
//			}	
//		}
		batch.end();
		
		if(Gdx.input.isTouched()) {
			touchPos = new Vector3();
	      	touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	     	camera.unproject(touchPos);
	     	if (touchPos.x > stopSign.getX() && touchPos.x < stopSign.getX() + stopSign.getWidth()) {
	     		if (touchPos.y > stopSign.getY() && touchPos.y < stopSign.getY() + stopSign.getHeight()) {
	     			batch.draw(blackCheck, stopSign.x, stopSign.y, stopSign.getWidth(), stopSign.getHeight());
	     		}
            }
//	     	stopSign.x = touchPos.x - 64 / 2;
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
	      blank_stop.width = 50;
	      blank_stop.height = 50;
	      stopSigns.add(blank_stop);
//	      lastStopSign = TimeUtils.nanoTime();
   }
	
	private void spawnObstacles() {
	      Rectangle empty_rect = new Rectangle();
	      empty_rect.x = MathUtils.random(0, 800-64);
	      empty_rect.y = MathUtils.random(0, 480-64);;
	      empty_rect.width = MathUtils.random(40, 60);
	      empty_rect.height = MathUtils.random(40, 60);
	      obstacles.add(empty_rect);
//	      lastStopSign = TimeUtils.nanoTime();
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
