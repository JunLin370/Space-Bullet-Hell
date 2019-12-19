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

public class Level1 {
	
	private Handler handler;
	private Random r;
	
	private static final int RESPAWN = 10;
	
	private int score, timer, adder;
	
	public Level1(Handler handler) {	//Constructor takes handler and adds new player to handler linked list
		this.handler = handler;		//Initializes handler
		r = new Random();	//Initialize Random
		handler.addObject(new Player(Game.HEIGHT/4, Game.WIDTH/2, ObjectID.Player1, handler));
	}

	public void tick() {
		
		if (timer == RESPAWN) {	
			//handler.addObject(new Enemy(r.nextInt(Game.WIDTH), -5, ObjectID.Bullet1, handler));	//make new enemy object and add it to handler linked list
			timer = 0;		//and reset timer
			adder += 1;
			if (adder % 3 == 0) {
			//	handler.addObject(new Enemy2(r.nextInt(Game.WIDTH), -5, ObjectID.Bullet2, handler));
			}if (adder == 10) {
				handler.addObject(new BasicEnemyShip(r.nextInt(Game.WIDTH), -5, ObjectID.BasicEnemy, handler));
				adder = 0;
			}

		}

		//This removes enemies from the screen when the exit down the bottom of it
		for (int i = 0; i < handler.object.size(); i++) {		//for number of objects in game
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectID.Bullet1) {		//if the object is id as Bullet1,
				if (tempObject.y > Game.HEIGHT - 100) {	//and if the object is out of the screen, 
					handler.removeObject(tempObject);	//remove it from the list
				}
			}
			if(tempObject.getId() == ObjectID.Bullet2) {		//if the object is id as Bullet2,
				if (tempObject.y > Game.HEIGHT - 100) {	//and if the object is out of the screen, 
					handler.removeObject(tempObject);	//remove it from the list
				}
			}
			
			if(tempObject.getId() == ObjectID.Bullet3) {
				if(Game.inBorder(tempObject.x, 100, Game.WIDTH - 100)|| Game.inBorder(tempObject.y,  -100, Game.HEIGHT - 100)) {
					handler.removeObject(tempObject);
				}
			}
			if(tempObject.getId() == ObjectID.BasicEnemy) {
				// If the object is 
				if(Game.inBorder(tempObject.x, 100, Game.WIDTH - 100)|| Game.inBorder(tempObject.y,  -100, Game.HEIGHT - 100)) {
					handler.removeObject(tempObject);
				}
			}
		}
		timer ++;	//add one to timer
		score ++;	//add one to score
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("Timer: " + timer, 15, 40);		//display timer (kinda useless tbh)
		g.drawString("Score: " + score, 15, 60);		//displays score (for bragging rights)
	}
	
}
