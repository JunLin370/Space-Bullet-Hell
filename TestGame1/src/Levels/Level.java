package Levels;

import java.awt.Graphics;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import GameClass.Game.STATE;
import abstrackSuperClasses.GameObject;
import abstrackSuperClasses.Ship;
import enemyShips.BasicEnemyShip;
import enemyShips.Boss1;
import enemyShips.ShotGunEnemyShip;


public abstract class Level {
	
	protected int timer, adder;
	protected Handler handler;
	protected Game game;
	public static int score;
	protected boolean gameWin;
	
	public Level(Handler hander, Game game) {
		this.handler = hander;
		this.game = game;
		score = 0;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	protected void gameOver() {
		for (int i = 0; i < handler.object.size(); i++) {	//check all objects
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectID.Player1) {	//if object's id is Gun1
				Ship tempShip = (Ship) tempObject;
				if (tempShip.getHealth() <= 0) {
					game.gameState = STATE.gameOver;
				}
			}
		}//End for
	}
	
	protected void formationXLine(int number, int placement, int health) {
		for (int i = 0; i < number; i++) {
			handler.addObject(new BasicEnemyShip( (placement + (i*30) - 10), -1 * (Game.HEIGHT/4), ObjectID.BasicEnemy, handler, health));
		}
	}
	
	protected void formationYLine(int number, int placement, int health) {
		for (int i = 0; i < number; i++) {
			handler.addObject(new BasicEnemyShip( placement - 10, -1 * (Game.HEIGHT/4) - (i*40), ObjectID.BasicEnemy, handler, health));
		}
	}
	
	protected void formationTri(int placement, int health) {
		basicEnemy(placement - 10, (-1)* ((Game.HEIGHT/4) + 100), health);
		basicEnemy(placement + 50  - 10, (-1)* ((Game.HEIGHT/4) + 100), health);
		basicEnemy(placement + 100  - 10, (-1)* ((Game.HEIGHT/4) + 100), health);
		basicEnemy(placement + 25  - 10, (-1)* ((Game.HEIGHT/4) + 50), health);
		basicEnemy(placement + 75  - 10, (-1)* ((Game.HEIGHT/4) + 50), health);
		basicEnemy(placement + 50  - 10, (-1)* ((Game.HEIGHT/4)), health);
	}
	
	protected void basicEnemy(int x, int health) {
		handler.addObject(new BasicEnemyShip(x,(-1)*(Game.HEIGHT/4), ObjectID.BasicEnemy, handler, health));
	}
	
	protected void shotGunEnemy(int x, int health) {
		handler.addObject(new ShotGunEnemyShip(x,(-1)*(Game.HEIGHT/4), ObjectID.BasicEnemy, handler, health));
	}
	
	protected void basicEnemy(int x, int y, int health) {
		handler.addObject(new BasicEnemyShip(x, y, ObjectID.BasicEnemy, handler, health));
	}
	
	protected void shotGunEnemy(int x, int y, int health) {
		handler.addObject(new ShotGunEnemyShip(x, y, ObjectID.BasicEnemy, handler, health));
	}
	
	public void reset() {
		timer = 0;
		adder = 0;
		gameWin = false;
	}
	
	public boolean getGameWin() {
		return gameWin;
	}

	public void setGameWin(boolean gameWin) {
		this.gameWin = gameWin;
	}
	
}
