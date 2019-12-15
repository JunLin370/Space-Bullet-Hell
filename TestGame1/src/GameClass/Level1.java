/* Jun Lin
 * DESC: This class contains the the behaviors for the first level of the game (WIP)
 * For now it just spawns many red blocks as they go downwards and the player avoids it
 * This class adds the Player object to the handler. Every tick, the it will add 1 to timer
 * when timer = respawn, it spawns a new enemy and set timer back to 0
 * Every tick, it also adds 1 to score. This displays both score and timer as text on the window
 * DATE: 2019-12-11 */
package GameClass;

import java.awt.Graphics;
import java.util.Random;

public class Level1 {
	
	private Handler handler;
	private Random r;
	
	private static final int RESPAWN = 5;
	
	private int score, timer, adder;
	
	public Level1(Handler handler) {	//Constructor takes handler and adds new player to handler linked list
		this.handler = handler;		//Initializes handler
		r = new Random();	//Initialize Random
		handler.addObject(new Player(Game.HEIGHT/4, Game.WIDTH/2, ObjectID.Player1, handler));
	}
	
	public void tick() {		
		if (timer == RESPAWN) {		
			handler.addObject(new Enemy(r.nextInt(Game.WIDTH), -5, ObjectID.Enemy1, handler));	//make new enemy object and add it to handler linked list
			timer = 0;		//and reset timer
			adder += 1;
			if (adder == 3) {
				handler.addObject(new Enemy2(r.nextInt(Game.WIDTH), -5, ObjectID.Enemy1, handler));
				adder = 0;
			}
		}
			for (int i = 0; i < handler.object.size(); i++) {		//for number of objects in game
				GameObject tempObject = handler.object.get(i);
				if (tempObject.y > Game.HEIGHT - 100 ) { 	//(WIP) !!! :( ( DOESNT WORK AND I DONT KNOW WHY :(
				if(tempObject.getId() == ObjectID.Enemy1) {		//if it is enemy1 which is the current object	
					handler.removeObject(tempObject);
				}

			}
		}
		timer ++;	//add one to timer
		score ++;	//add one to score
	}
	
	public void render(Graphics g) {
		g.drawString("Timer: " + timer, 15, 40);		//display timer (kinda useless tbh)
		g.drawString("Score: " + score, 15, 60);		//displays score (for bragging rights)
	}
	
}
