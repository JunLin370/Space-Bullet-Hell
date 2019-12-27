/* Jun Lin
 * DESC: This class contains the the behaviors for the first level of the game (WIP)
 * For now it just spawns many red blocks as they go downwards and the player avoids it
 * This class adds the Player object to the handler. Every tick, the it will add 1 to timer
 * when timer = respawn, it spawns a new enemy and set timer back to 0
 * Every tick, it also adds 1 to score. This displays both score and timer as text on the window
 * DATE: 2019-12-11 */
package GameClass;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Level1 {
	
	private Handler handler;
	private Random r;
	
	private int timer, adder, stagelength;
	public static int score;
	
	public Level1(Handler handler) {	//Constructor takes handler and adds new player to handler linked list
		this.handler = handler;		//Initializes handler
		r = new Random();	//Initialize Random
		handler.addObject(new Player(Game.HEIGHT/4, Game.WIDTH/2, ObjectID.Player1, handler));
		stagelength = 40000;

		
		// Spawn new ships here for testing
		//
	}

	public void tick() {
		timer ++;                                                                                                                                                
		if (timer % 60 == 1) {
			adder ++;
			if (adder == 5 || adder == 10) {
				FormationTri(Game.WIDTH/8);
				FormationTri(Game.WIDTH/2 + Game.WIDTH/4);
			}
			if (adder == 15) {
				FormationXLine(5, 500);
				FormationXLine(5, 70);
			}
			if (adder == 20) {
				FormationTri(Game.WIDTH/8);
				FormationTri(Game.WIDTH/2 + Game.WIDTH/4);
			}
			if (adder == 25) {
				FormationYLine(5, Game.WIDTH/2);
			}
			if (adder == 32) {
				FormationYLine(13, Game.WIDTH/4 + 70);
			}
			if (adder == 34) {
				FormationYLine(7, Game.WIDTH/4 + 55);
			}
			if (adder == 35 ) {
				FormationYLine(10, Game.WIDTH/4);
				FormationYLine(10, Game.WIDTH/4 + 75);
			}
			if (adder == 36) {
				FormationYLine(10, Game.WIDTH/4 -30);
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(10, 10, 300, 15);
		g.setColor(Color.WHITE);
		g.drawString("timer: " + timer, 15, 40);
		g.drawString("Score: " + score, 15, 60);		//displays score (for bragging rights)
		g.drawString("Adder: " + adder, 15, 80);
	}
	private void FormationXLine(int number, int placement) {
		for (int i = 0; i < number; i++) {
			handler.addObject(new BasicEnemyShip( (placement + (i*30) - 10), -1 * (Game.HEIGHT/4), ObjectID.BasicEnemy, handler));
		}
	}
	
	private void FormationYLine(int number, int placement) {
		for (int i = 0; i < number; i++) {
			handler.addObject(new BasicEnemyShip( placement - 10, -1 * (Game.HEIGHT/4) - (i*40), ObjectID.BasicEnemy, handler));
		}
	}
	
	private void FormationTri(int placement) {
		handler.addObject(new BasicEnemyShip( placement - 10, (-1)* ((Game.HEIGHT/4) + 100), ObjectID.BasicEnemy, handler));
		handler.addObject(new BasicEnemyShip( placement + 50  - 10, (-1)* ((Game.HEIGHT/4) + 100), ObjectID.BasicEnemy, handler));
		handler.addObject(new BasicEnemyShip( placement + 100  - 10, (-1)* ((Game.HEIGHT/4) + 100), ObjectID.BasicEnemy, handler));
		handler.addObject(new BasicEnemyShip( placement + 25  - 10, (-1)* ((Game.HEIGHT/4) + 50), ObjectID.BasicEnemy, handler));
		handler.addObject(new BasicEnemyShip( placement + 75  - 10, (-1)* ((Game.HEIGHT/4) + 50), ObjectID.BasicEnemy, handler));
		handler.addObject(new BasicEnemyShip( placement + 50  - 10, (-1)* ((Game.HEIGHT/4)), ObjectID.BasicEnemy, handler));

	}
	
}
