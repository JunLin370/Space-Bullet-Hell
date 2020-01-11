package Levels;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import GameClass.Game.STATE;
import enemyShips.Boss1;
import enemyShips.Boss2;
import playerItems.Player;

public class Level3 extends Level{
	
	private int basicEnemyHealth, shotGunEnemyHealth, heavyBossHealth, lightBossHealth, spawnTimer, wave;
	private boolean start;
	private Random r;
	
	public Level3(Handler hander, Game game) {
		super(hander, game);
		
		start = true;
		basicEnemyHealth = 5;
		shotGunEnemyHealth = 50;
		heavyBossHealth = 2500;
		lightBossHealth = 750;
		wave = 1;
		
		r = new Random();
	}

	public void tick() {
		timer ++;                                                                                                                                                
		if (timer % 60 == 0) {
			adder ++;
			if (adder == 1 && start == true) {
				start = false;
				score = 0;
				handler.addObject(new Player(Game.WIDTH/2, Game.HEIGHT/2 + Game.HEIGHT/4, ObjectID.Player1, handler, game.getWeaponLevel(), game.getWeaponType()));
			}
			if (adder == 6) {
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
			}
			if (adder == 8) {
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				shotGunEnemy(r.nextInt(Game.WIDTH - 1) + 1, shotGunEnemyHealth);
				shotGunEnemy(r.nextInt(Game.WIDTH - 1) + 1, shotGunEnemyHealth);
			}
			if (adder == 10) {
				formationTri(10, basicEnemyHealth);
				formationTri(Game.WIDTH/2, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				shotGunEnemy(r.nextInt(Game.WIDTH - 1) + 1, shotGunEnemyHealth);
			}
			if (adder == 14 || adder == 18) {
				formationTri(Game.WIDTH/8, basicEnemyHealth);
				shotGunEnemy(r.nextInt(Game.WIDTH - 1) + 1, shotGunEnemyHealth);
				formationTri(Game.WIDTH/2 + Game.WIDTH/4, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
			}
			if (adder == 21) {
				shotGunEnemy(r.nextInt(Game.WIDTH - 1) + 1, shotGunEnemyHealth);
				formationXLine(5, 500, basicEnemyHealth);
				formationXLine(5, 70, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
			}
			if (adder == 25) {
				formationTri(Game.WIDTH/8, basicEnemyHealth);
				formationTri(Game.WIDTH/2 + Game.WIDTH/4, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
			}
			if (adder == 30) {
				switch (r.nextInt(2) + 1) {
				case 1:
					handler.addObject(new Boss1(Game.WIDTH/2 - 150 , -1*Game.HEIGHT/2, ObjectID.Boss1, handler, heavyBossHealth, this));
					break;
				case 2:
					handler.addObject(new Boss2(Game.WIDTH/2 - 50 , -1*Game.HEIGHT/2, ObjectID.Boss2, handler, lightBossHealth, this));
					break;
				}
			}
			if (adder >= 40) {
				spawnTimer++;
				if (spawnTimer%2 == 0)
					basicEnemy(r.nextInt(Game.WIDTH - 1) + 1, basicEnemyHealth);
				if (spawnTimer == 10) {
					shotGunEnemy(r.nextInt(Game.WIDTH - 1) + 1, shotGunEnemyHealth);
					spawnTimer = 0;
				}
				
			}
			
			if (gameWin == true) {
				adder = 0;
				wave ++;
				gameWin = false;
				if (basicEnemyHealth <= 100)
					basicEnemyHealth += 5;
				if (shotGunEnemyHealth <= 1000) 
					shotGunEnemyHealth += 50;
				if (heavyBossHealth <= 10000)
					heavyBossHealth += 500;
				if (lightBossHealth <= 5000) {
					lightBossHealth += 250;
				}
			}
		}
		
	}

	public void render(Graphics g) {
		if (adder <= 5 && start == true) {
			g.setColor(Color.RED);
			g.drawString( String.valueOf(6 - adder), Game.WIDTH/2, Game.HEIGHT/2);
		}
		g.setColor(Color.WHITE);
		g.drawString("Score: " + score, 15, 60);		//displays score (for bragging rights)
		g.drawString("Wave: " + wave, 15, 80);
	}

	public void reset() {
		timer = 0;
		adder = 0;
		gameWin = false;
		start = true;
	}
}
