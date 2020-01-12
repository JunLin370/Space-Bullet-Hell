package Levels;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import GameClass.Game.STATE;
import abstrackSuperClasses.GameObject;
import abstrackSuperClasses.Ship;
import enemyShips.Boss1;
import enemyShips.Boss2;
import playerItems.Player;

public class Level3 extends Level{
	
	private int basicEnemyHealth, shotGunEnemyHealth, heavyBossHealth, lightBossHealth, spawnTimer, wave;
	private boolean start;
	private Random r;
	
	/* This Constructor first supers handler and game. It then initialize the starting health for the enemies in the endless mode 
	 * pre: Handler handler, Game game
	 * post: supers(handler, game). initialize enemies health and sets start to true, and wave to 1. initialize Random
	 */
	public Level3(Handler hander, Game game) {
		super(hander, game);
		
		start = true;
		basicEnemyHealth = 5;
		shotGunEnemyHealth = 50;
		heavyBossHealth = 1250;
		lightBossHealth = 500;
		wave = 1;
		
		r = new Random();
	}

	/* tick method, similar to level1, spawns enemies on a second base timer. In this level, the player and score are spawned and reset once, and can not be
	 * spawn and reset until the reset method is run. It then spawns a whole mess of enemies (rushed) for an 25 seconds. It then spawns either boss 1 or
	 * boss 2. While they are alive, the basicEnemy and shotGunEnemy are gradually being spawn to support the boss. When the boss is defeated, gameWin
	 * is set to true, and every enemies health is increased. Added is set back to 0 and the player is healed. The wave counter also counts up.
	 * This provides a technically endless gamemode. The enemies health are capped at a certain point (but tbh i don't think anyone is going to reach there)
	 * pre:
	 * post: Spawns many enemies for 25 seconds. Then a boss, and random enemies every so often. After boss is defeated, repeat.
	 */
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
				for (int i = 0; i < handler.object.size(); i++) {	//check all objects
					GameObject tempObject = handler.object.get(i);
					if(tempObject.getId() == ObjectID.Player1) {	//if object's id is Gun1
						Ship tempShip = (Ship) tempObject;
						tempShip.setHealth(100);
					}
				}
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

	/* Renders a count down, the score, and the wave number
	 * 
	 */
	public void render(Graphics g) {
		if (adder <= 5 && start == true) {
			g.setColor(Color.RED);
			g.drawString( String.valueOf(6 - adder), Game.WIDTH/2, Game.HEIGHT/2);
		}
		g.setColor(Color.WHITE);
		g.drawString("Score: " + score, 15, 60);		//displays score (for bragging rights)
		g.drawString("Wave: " + wave, 15, 80);
	}

	/* Override the reset method in the Level class, resets timer, adder, gameWin, Start, and Wave. (see i used everything we learned ',:)
	 * pre:
	 * post: timer = 0, adder = 0, gameWin = false, start = true, wave = 1
	 */
	public void reset() {
		timer = 0;
		adder = 0;
		gameWin = false;
		start = true;
		wave = 1;
	}
}
