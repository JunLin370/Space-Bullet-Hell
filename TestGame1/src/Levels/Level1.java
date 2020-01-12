/* Jun Lin
 * DESC: This Class spawns enemies every 5 seconds and at 30 seconds, it spawns the boss of the stage. A very simple set up
 * in the first second, it sets score to 0 and spawns player
 * DATE: 2019-12-11 */
package Levels;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import GameClass.Game;
import GameClass.Game.STATE;
import GameClass.Handler;
import GameClass.ObjectID;
import enemyShips.Boss1;
import playerItems.Player;

public class Level1 extends Level {
	
	/* Constructor, supers handler and game
	 * pre: Handler handler, Game game
	 * post: supers handler and game
	 */
	public Level1(Handler handler, Game game) {	//Constructor takes handler and adds new player to handler linked list
		super(handler, game);
	}

	/* Every second, adder ++. use adder to determine when enemies spawn
	 * The level is 30 seconds of the basic enemy and one shotgun enemy in simple formations. At the end, boss1 is spawned
	 * if the boss is defeated. gameWin is set to true. When gameWin is set to true, game.gameState is set to STATE.gameWin
	 * pre:
	 * post: Spawns enemies. at 30 seconds, spawn boss, if gameWin == true, gameState = gameWin
	 */
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
				formationXLine(5, 500, 10);
				formationXLine(5, 70, 10);
			}
			if (adder == 20) {
				formationTri(Game.WIDTH/8, 10);
				formationTri(Game.WIDTH/2 + Game.WIDTH/4, 10);
			}
			if (adder == 30) {
				handler.addObject(new Boss1(Game.WIDTH/2 - 150 , -1*Game.HEIGHT/2, ObjectID.Boss1, handler, 2500, this));
			}
			if (gameWin == true) {
				game.gameState = STATE.gameWin;
			}
		}
	}

	/* Displays a countdown at the begining of level. Then it renders the score under the ammo and health
	 * pre: Graphics
	 * post: renders score and countdown
	 */
	public void render(Graphics g) {
		if (adder <= 5) {
			g.setColor(Color.RED);
			g.drawString( String.valueOf(6 - adder), Game.WIDTH/2, Game.HEIGHT/2);
		}
		g.setColor(Color.WHITE);
		g.drawString("Score: " + score, 15, 60);		//displays score (for bragging rights)
	}
}
