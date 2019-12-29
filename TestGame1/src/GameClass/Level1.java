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

import playerItems.Player;

public class Level1 extends Level {

	private int timer, adder;
	public static int score;
	
	public Level1(Handler handler) {	//Constructor takes handler and adds new player to handler linked list
		super(handler);

		
		//Add test objects here to spawn them in main menu
		//
	}

	public void tick() {
		timer ++;                                                                                                                                                
		if (timer % 60 == 0) {
			adder ++;
			if (adder == 5 ) {
				handler.addObject(new Player(Game.WIDTH/2, Game.HEIGHT/2 + Game.HEIGHT/4, ObjectID.Player1, handler));
				newBoss(Game.WIDTH/2 - 150 , -1*Game.HEIGHT/2, 2500);
			}
			if (adder == 10 || adder == 15) {
			//	formationTri(Game.WIDTH/8, 10);
			//	formationTri(Game.WIDTH/2 + Game.WIDTH/4, 10);
			}
			if (adder == 20) {
			//	shotGunEnemy(Game.WIDTH/2, 30);
			//	formationXLine(5, 500);
			//	formationXLine(5, 70);
			}
			if (adder == 25) {
			//	formationTri(Game.WIDTH/8, 10);
			//	formationTri(Game.WIDTH/2 + Game.WIDTH/4, 10);
			}
		}
	}

	public void render(Graphics g) {
		if (adder <= 5) {
			g.setColor(Color.RED);
			g.drawString( String.valueOf(adder), Game.WIDTH/2, Game.HEIGHT/2);
		}else {
			g.setColor(Color.WHITE);
			g.drawRect(10, 10, 300, 15);
			g.setColor(Color.WHITE);
		}
		g.drawString("timer: " + timer, 15, 40);
		g.drawString("Score: " + score, 15, 60);		//displays score (for bragging rights)
		g.drawString("Adder: " + adder, 15, 80);
	}
}
