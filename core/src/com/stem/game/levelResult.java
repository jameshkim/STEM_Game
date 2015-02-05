package com.stem.game;

import com.badlogic.gdx.math.Rectangle;

public class levelResult {
	protected float gameTime;
	protected int gameLevel;
	
	public levelResult() {
		this.gameTime = 0.0f;
		this.gameLevel = 1;
	}
	
	public void setLvl(int level) {
		this.gameLevel = level;
	}
	
	public void setGameTime(float gameTimeInstance) {
		this.gameTime = gameTimeInstance;
	}

}
