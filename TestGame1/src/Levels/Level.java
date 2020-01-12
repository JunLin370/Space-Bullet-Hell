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
	
	/* Constructor for this class. passes handler and game into this class, initialize score
	 * 
	 */
	public Level(Handler hander, Game game) {
		this.handler = hander;
		this.game = game;
		score = 0;
	}
	
	public abstract void tick();	//abstract tick method used by the levels to spawn enemies
	public abstract void render(Graphics g);	//abstract render method used by the levels to render score and other things possibly
	
	/* This method checks if the player has died, by going through handler and seeing if players health is zero. If it is, set state to gameOver
	 * pre:
	 * post: game.gameState = STATE.gameOver
	 */
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
	
	/* A method that spawns enemies in horizontal line
	 * pre: int number of enemies, int placement (x position), int health
	 * post: spawns number amount of enemies
	 */
	protected void formationXLine(int number, int placement, int health) {
		for (int i = 0; i < number; i++) {
			handler.addObject(new BasicEnemyShip( (placement + (i*30) - 10), -1 * (Game.HEIGHT/4), ObjectID.BasicEnemy, handler, health));
		}
	}
	
	/* spawns enemies in a vertical line 
	 * pre: int number of enemies, int x position, int health
	 * post: spawns number amount of enemies
	 */
	protected void formationYLine(int number, int placement, int health) {
		for (int i = 0; i < number; i++) {
			handler.addObject(new BasicEnemyShip( placement - 10, -1 * (Game.HEIGHT/4) - (i*40), ObjectID.BasicEnemy, handler, health));
		}
	}
	
	/* a very bad method that spawns enemies in a triangle
	 * pre: int placement (x pos), int health
	 * post: spawns 6 enemies in a triangle 
	 */
	protected void formationTri(int placement, int health) {
		basicEnemy(placement - 10, (-1)* ((Game.HEIGHT/4) + 100), health);
		basicEnemy(placement + 50  - 10, (-1)* ((Game.HEIGHT/4) + 100), health);
		basicEnemy(placement + 100  - 10, (-1)* ((Game.HEIGHT/4) + 100), health);
		basicEnemy(placement + 25  - 10, (-1)* ((Game.HEIGHT/4) + 50), health);
		basicEnemy(placement + 75  - 10, (-1)* ((Game.HEIGHT/4) + 50), health);
		basicEnemy(placement + 50  - 10, (-1)* ((Game.HEIGHT/4)), health);
	}
	
	/* spawns a basic enemy ship quarter way up from off the screen (1*game.HEIGHT/4).
	 * pre: int x pos, int health
	 * post: spawns one new BasicEnemyShip
	 */
	protected void basicEnemy(int x, int health) {
		handler.addObject(new BasicEnemyShip(x,(-1)*(Game.HEIGHT/4), ObjectID.BasicEnemy, handler, health));
	}
	
	/* spawns a shotGunEnemy quarter way up from off the screen (1*game.HEIGHT/4).
	 * pre: int x pos, int health
	 * post: spawns one new shotGunEnemy
	 */
	protected void shotGunEnemy(int x, int health) {
		handler.addObject(new ShotGunEnemyShip(x,(-1)*(Game.HEIGHT/4), ObjectID.BasicEnemy, handler, health));
	}
	
	/* spawns a basic enemy ship by x and y position (1*game.HEIGHT/4).
	 * pre: int x pos, int y pos, int health
	 * post: spawns one new BasicEnemyShip
	 */
	protected void basicEnemy(int x, int y, int health) {
		handler.addObject(new BasicEnemyShip(x, y, ObjectID.BasicEnemy, handler, health));
	}
	
	/* spawns a shotGunEnemy quarter way up from off the screen (1*game.HEIGHT/4).
	 * pre: int x pos, int y pos, int health
	 * post: spawns one new shotGunEnemy
	 */
	protected void shotGunEnemy(int x, int y, int health) {
		handler.addObject(new ShotGunEnemyShip(x, y, ObjectID.BasicEnemy, handler, health));
	}
	
	/* This reset variables used by the level class to spawn enemies
	 * pre:
	 * post: timer = 0, adder = 0, gameWin = false
	 */
	public void reset() {
		timer = 0;
		adder = 0;
		gameWin = false;
	}
	
	/* getter for gameWin.
	 * pre:
	 * post: returns boolean
	 */
	public boolean getGameWin() {
		return gameWin;
	}

	/* setter for gameWin
	 * pre: boolean gameWin
	 * post: sets gameWin to newGameWin
	 */
	public void setGameWin(boolean gameWin) {
		this.gameWin = gameWin;
	}
	
}
