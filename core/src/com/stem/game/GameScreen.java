package com.stem.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen, InputProcessor {
	
	static class screenObject {
		public Rectangle objPosit;
		public float  x;
		public float y;
		public float width;
		public float height;
		public Texture img;
		
		public screenObject(Rectangle objData) {
			this.objPosit = objData;
			this.x = objData.x;
			this.y = objData.y;
			this.width = objData.getWidth();
			this.height = objData.getHeight();
		}
		
		public void setTexture(Texture imgType) {
			this.img = imgType;
		}
	}
	
	final StemGame game;
	
//	public SpriteBatch batch;
	public BitmapFont font;
	private OrthographicCamera camera;
	
//	Stop Signs
	private Texture blankStop;
	private Texture stop1;
	private Texture stop2;
	private Texture stop3;
	private Texture stop4;
	private Texture trafficLight;
	
	
	
//	Success images
	private	Texture	blackCheck;
	private Texture smiley;
	
//	Obstacles images
	private Texture yellDiam;
	private Texture empRect;
	private Texture snowFlake;
	
//	Rectangles used to logically represent objects	
	private Rectangle stopSign;
	private	Rectangle obstacle;
	private Rectangle blackCheckRect;
	
	private Vector3 touchPos;
	
	List<Texture> lvlTwoStopSigns = new ArrayList<Texture>();
	private Array<screenObject> stopSigns;
	private Array<screenObject> obstacles;
	private Set<screenObject> successChk;
	private Array<Rectangle> snowflakes;
		
	private float gameTime;
	final levelResult lvlRslt;
	private int PULSE = 20;
	private int fluctVal = 0;
	private long lastDropTime;

	
	
	//	private long snowFlakeTimes;
	private String	timeElapsing;
	
	public GameScreen(final StemGame gam, final levelResult lvlRsltInstance) {
		this.game = gam;
		this.lvlRslt = lvlRsltInstance;
		
		blankStop = new Texture(Gdx.files.internal("blank_stop.png"));
		yellDiam = new Texture(Gdx.files.internal("yellow_diamond.png"));
		empRect = new Texture(Gdx.files.internal("regrect.png"));
		blackCheck  = new Texture(Gdx.files.internal("lvlcheckmark.png"));
		stop1 = new Texture(Gdx.files.internal("stop1.png"));
		stop2 = new Texture(Gdx.files.internal("stop2.png"));
		stop3 = new Texture(Gdx.files.internal("stop3.png"));
		stop4 = new Texture(Gdx.files.internal("stop4.png"));
		trafficLight = new Texture(Gdx.files.internal("trafficlight.png"));
		snowFlake = new Texture(Gdx.files.internal("snowflake.png"));
		smiley = new Texture(Gdx.files.internal("smiley.png"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
//		batch = new SpriteBatch();

		lvlTwoStopSigns.add(stop1);
		lvlTwoStopSigns.add(stop2);
		lvlTwoStopSigns.add(stop3);
		
		stopSigns = new Array<screenObject>();
		obstacles = new Array<screenObject>();
		successChk = new HashSet<screenObject>();
		snowflakes = new Array<Rectangle>();


		Random randGen = new Random();
		for (int i = 0; i < randGen.nextInt(10) + 2; i++) {
			spawnStopSigns();
		}
		for (int i = 0; i < randGen.nextInt(10) + 2; i++) {
			spawnObstacles();
			if  (8 < lvlRslt.gameLevel && lvlRslt.gameLevel <= 10) {
				spawnSnowflakes();
			}
		}
	}

	@Override
	public void render(float delta) {
		
//		Clears screen to make background white
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameTime += delta;
        float minutes = (float) Math.floor(gameTime / 60.0f);
        float seconds = gameTime - minutes * 60.0f;
        timeElapsing = String.format("%.0fm%.0fs", minutes, seconds);
        
		if  (8 < lvlRslt.gameLevel && lvlRslt.gameLevel <= 10) {
	        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) {
	        	spawnSnowflakes();
	        }
		}
        
//		Tells camera to update matrices
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);	
		
		game.batch.begin();
		game.byFont.draw(game.batch, "Time passed: " + timeElapsing, 0, 480);
		for(screenObject stopSignRect: stopSigns) {
			game.batch.draw(stopSignRect.img, stopSignRect.x, stopSignRect.y, stopSignRect.width, stopSignRect.height);
		}
		
		for(screenObject obstacle: obstacles) {
			if (4 < lvlRslt.gameLevel && lvlRslt.gameLevel <= 6 && obstacle.img == yellDiam) {
				obstacle.width = fluctValue(obstacle.width);
				obstacle.height = fluctValue(obstacle.height);
				game.batch.draw(obstacle.img, obstacle.x, obstacle.y, obstacle.width, obstacle.height);
			} else {
				game.batch.draw(obstacle.img, obstacle.x, obstacle.y, obstacle.width, obstacle.height);
			}
		}
		
		if (successChk.size() != 0) {
			for (screenObject chkMark: successChk)
			{
				if  (6 < lvlRslt.gameLevel && lvlRslt.gameLevel <= 8) {
					game.batch.draw(smiley, chkMark.x, chkMark.y, chkMark.width, chkMark.height);
				} else {
		 			game.batch.draw(blackCheck, chkMark.x, chkMark.y, chkMark.width, chkMark.height);
				}
			}
		}
		
		if  (8 < lvlRslt.gameLevel && lvlRslt.gameLevel <= 10) {
			for(Rectangle sflake: snowflakes) {
				game.batch.draw(snowFlake, sflake.x, sflake.y);
			}
		}
		
		
		game.batch.end();
		Gdx.app.log("ErrorCheckTag", "Fluct: " + fluctVal);

		fluctVal++;
		
//		if(Gdx.input.isTouched()) {
//	
//			touchPos = new Vector3();
//	      	touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//				Gdx.app.log("ErrorCheckTag", "touchPos" + Gdx.input.getX() + " -- " +  Gdx.input.getY());
//	     	camera.unproject(touchPos);
//		    
//	     	for(screenObject stopSignRect: stopSigns) {
//		     	if (touchPos.x > stopSignRect.x && touchPos.x < stopSignRect.x + stopSignRect.width) {
//		     		if (touchPos.y > stopSignRect.y && touchPos.y < stopSignRect.y + stopSignRect.height) {
//		     			successChk.add(stopSignRect);
//		     		}
//	            }
//	     	}
//		}
	    	
		if  (8 < lvlRslt.gameLevel && lvlRslt.gameLevel <= 10) {
			Iterator<Rectangle> iter = snowflakes.iterator();
			while(iter.hasNext()) {
				Rectangle sflake = iter.next();
				sflake.y -= 200 * Gdx.graphics.getDeltaTime();
				sflake.x = fluctValue(sflake.x);
				if(sflake.y + 64 < 0) iter.remove();
		   	}
		}
					
	
		if (stopSigns.size != 0 && stopSigns.size == successChk.size()) {
			lvlRslt.setGameTime(gameTime);
            game.setScreen(new NextLevelScreen(game, lvlRslt));	            	
        }
		

		
//		Use this to make snowflakes move
//		Iterator<Rectangle> iter = raindrops.iterator();
//		   while(iter.hasNext()) {
//		      Rectangle raindrop = iter.next();
//		      raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
//		      if(raindrop.y + 64 < 0) iter.remove();
//		}
	}
	
	private void spawnStopSigns() {
	      screenObject stopsign = new screenObject(new Rectangle());
	      Random randomizer = new Random();

	      stopsign.x = MathUtils.random(0, 800 - 80);
	      stopsign.y = MathUtils.random(0, 480 - 80);
	      stopsign.width = MathUtils.random(60, 80);
	      stopsign.height = MathUtils.random(60, 80);
	      
	      if (lvlRslt.gameLevel <= 2) {
	    	  stopsign.setTexture(blankStop);
	      } else if  (2 < lvlRslt.gameLevel && lvlRslt.gameLevel <= 4) {
	    	  Texture randomSign = lvlTwoStopSigns.get(randomizer.nextInt(lvlTwoStopSigns.size()));
	    	  stopsign.setTexture(randomSign);
	      } else if  (6 < lvlRslt.gameLevel && lvlRslt.gameLevel <= 8) {
	    	  stopsign.setTexture(trafficLight);
		      stopsign.width = MathUtils.random(70, 90);
		      stopsign.height = MathUtils.random(70, 90);
	      } else {
	    	  stopsign.setTexture(stop4);
	      }
	      stopSigns.add(stopsign);
//	      lastStopSign = TimeUtils.nanoTime();
   }
	

	
	private void spawnObstacles() {
		screenObject obstacleImg = new screenObject(new Rectangle());
		Random randGen = new Random();
		
		if (randGen.nextInt(6) > 2) {
			obstacleImg.setTexture(yellDiam);
		} else {
			obstacleImg.setTexture(empRect);
		}
		
		obstacleImg.x = MathUtils.random(0, 800 - 60);
	    obstacleImg.y = MathUtils.random(0, 480 - 60);
	    obstacleImg.width = MathUtils.random(50, 70);
	    obstacleImg.height = MathUtils.random(50, 70);
	    obstacles.add(obstacleImg);
	    
//	      lastStopSign = TimeUtils.nanoTime();
 }
	
	private float fluctValue(float val) {
		if (fluctVal < PULSE) {
			return (val + 1);
		} else if (fluctVal == PULSE) {
			PULSE *= -1;
			return (val + 1);
		} else {
			if ((fluctVal % 60) == 0) {
				fluctVal = PULSE;
				PULSE *= -1;
			}
			return (val - 1);
		}
	}
	
	private void spawnSnowflakes() {
	      Rectangle sflake = new Rectangle();
	      sflake.x = MathUtils.random(0, 800-60);
	      sflake.y = 480;
	      sflake.width = 50;
	      sflake.height = 50;
	      snowflakes.add(sflake);
	      lastDropTime = TimeUtils.nanoTime();
	   }
	
	
	public void dispose() {
		blankStop.dispose();
		empRect.dispose();
		yellDiam.dispose();
		blackCheck.dispose();
		game.batch.dispose();
		stop1.dispose(); 
		stop2.dispose(); 
		stop3.dispose(); 
		stop4.dispose(); 
		trafficLight.dispose(); 
		snowFlake.dispose();
		smiley.dispose(); 
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

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
//		Process user input		
		touchPos = new Vector3();
      	touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		Gdx.app.log("ErrorCheckTag", "touchPos" + Gdx.input.getX() + " -- " +  Gdx.input.getY());
     	camera.unproject(touchPos);
	    
     	for(screenObject stopSignRect: stopSigns) {
	     	if (touchPos.x > stopSignRect.x && touchPos.x < stopSignRect.x + stopSignRect.width) {
	     		if (touchPos.y > stopSignRect.y && touchPos.y < stopSignRect.y + stopSignRect.height) {
	     			successChk.add(stopSignRect);
	     		}
            }
     	}
     	
     	
	
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
