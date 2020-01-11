package Levels;

import java.awt.Color;
import java.awt.Graphics;

import GameClass.Game;
import GameClass.Game.STATE;
import GameClass.Handler;
import GameClass.ObjectID;
import enemyShips.Boss2;
import playerItems.Player;

public class Level2 extends Level{

	public Level2(Handler hander, Game game) {
		super(hander, game);
	}
	
	public void tick() {
		timer ++;                                                                                                                                                
		if (timer % 60 == 0) {
			adder ++;
			if (adder == 1) {
				score = 0;
				handler.addObject(new Player(Game.WIDTH/2, Game.HEIGHT/2 + Game.HEIGHT/4, ObjectID.Player1, handler, game.getWeaponLevel(), game.getWeaponType()));
			}
			if (adder == 5) {
				handler.addObject(new Boss2(Game.WIDTH/2 - 50 , -1*Game.HEIGHT/2, ObjectID.Boss2, handler, 1500, this));
			}
			if (gameWin == true) {
				game.gameState = STATE.gameWin;
			}
		}
	}

	public void render(Graphics g) {
		if (adder <= 5) {
			g.setColor(Color.RED);
			g.drawString( String.valueOf(6 - adder), Game.WIDTH/2, Game.HEIGHT/2);
		}
		g.setColor(Color.WHITE);
		g.drawString("Score: " + score, 15, 60);		//displays score (for bragging rights)
	}

}
