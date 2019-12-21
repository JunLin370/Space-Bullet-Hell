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
	
	private int timer, adder;
	public static int score;
	
	public Level1(Handler handler) {	//Constructor takes handler and adds new player to handler linked list
		this.handler = handler;		//Initializes handler
		r = new Random();	//Initialize Random
		handler.addObject(new Player(Game.HEIGHT/4, Game.WIDTH/2, ObjectID.Player1, handler));
	}

	public void tick() {

		if(timer )

		timer ++;	//add one to timer
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(10, 10, 300, 15);
		g.setColor(Color.WHITE);
		g.drawString("Score: " + score, 15, 60);		//displays score (for bragging rights)
	}
	
}
