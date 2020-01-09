/* Jun Lin
 * DESC: This class contains the the behaviors for the first level of the game (WIP)
 * For now it just spawns many red blocks as they go downwards and the player avoids it
 * This class adds the Player object to the handler. Every tick, the it will add 1 to timer
 * when timer = respawn, it spawns a new enemy and set timer back to 0
 * Every tick, it also adds 1 to score. This displays both score and timer as text on the window
 * DATE: 2019-12-11 */
package Levels;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import enemyShips.Boss1;
import playerItems.Player;

public class Level1 extends Level {
	
	public Level1(Handler handler, Game game) {	//Constructor takes handler and adds new player to handler linked list
		super(handler, game);
	}

	public void tick() {
		timer ++;                                                                                                                                                
		if (timer % 60 == 0) {
			adder ++;
			if (adder == 1) {
				score = 0;
				handler.addObject(new Player(Game.WIDTH/2, Game.HEIGHT/2 + Game.HEIGHT/4, ObjectID.Player1, handler, game.getWeaponLevel(), game.getWeaponType()));
			}
			if (adder == 5 || adder == 10) {
				formationTri(Game.WIDTH/8, 10);
				formationTri(Game.WIDTH/2 + Game.WIDTH/4, 10);
			}
			if (adder == 15) {
				shotGunEnemy(Game.WIDTH/2, 30);
				formationXLine(5, 500);
				formationXLine(5, 70);
			}
			if (adder == 20) {
				formationTri(Game.WIDTH/8, 10);
				formationTri(Game.WIDTH/2 + Game.WIDTH/4, 10);
			}
			if (adder == 30) {
				handler.addObject(new Boss1(Game.WIDTH/2 - 150 , -1*Game.HEIGHT/2, ObjectID.Boss1, handler, 2500, game));
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
