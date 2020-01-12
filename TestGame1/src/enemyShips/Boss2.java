/*This is the second more mobile boss in my game. this contains the ai for the second boss as well displays a health bar at the bottom of the screen
 * This boss has an opener which also happens to be its main attack for phase 2
 */
package enemyShips;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import GameClass.Game.STATE;
import Levels.Level;
import abstrackSuperClasses.GameObject;
import abstrackSuperClasses.Ship;
import playerItems.BigRifleBullet;
import playerItems.BlueLaser;
import playerItems.RifleBullet;

public class Boss2 extends Ship{
	
	private final int  RECTANGLEWIDTH = 100, RECTANGLEHEIGHT = 125;		//size of the boss
	private int attack, invincibilityFrames, machineGunTimer, timer2, oHealth;	// these 5 types of variables are used for the tracking and selecting of the attacks from the boss
	private boolean on, opener, engines, movepattern, phase2, buffer, moving;	
	private Random r;	
	private Level level;

	/* constructor takes in the pre-requisite x and y starting location of the object, and object id, the
	 * handler, and health of the ship. It also initialize many variables used in the tick method.   
	 * pre: float x, float y, ObjectID (Ship1), handler, health of the ship
	 * post: supers all the pre variables. sets on and buffer to false. Make a new random object. set velY to 1
	 */
	public Boss2(float x, float y, ObjectID id, Handler handler, int newHealth, Level level) {
		super(x, y, id, handler, newHealth);
		this.level = level;
		
		oHealth = health;
		on = false;		//on and buffer are variables used in the AI of this boss
		phase2 = false;
		opener = true;
		engines = true;
		invincibilityFrames = 0;
		
		r = new Random();
		velY = 8;

	}
	
	/* This is the collision detection for this Boss1 class. It checks every object in the game and if its a gun object, remove health from this object. If it is the player
	 * destroy the player object, cause its touching a boss ffs
	 * pre:
	 * post: if touch bullet, remove health from boss. if touch player, kill player
	 */
	protected void collisions() {
		for (int i = 0; i < handler.object.size(); i++) {	//check all objects
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectID.Gun1) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					health -= RifleBullet.damage;		//if yes then remove a certain amount of health
					handler.removeObject(tempObject);	//and remove the bullet
				}
			}
			
			if(tempObject.getId() == ObjectID.Gun2) {	//if object's id is Gun2
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					health -= BigRifleBullet.damage;		//if yes then remove a certain amount of health
					handler.removeObject(tempObject);	//and remove the bullet
				}
			}
			
			if(tempObject.getId() == ObjectID.Gun3) {	//if object's id is Gun3
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					if (invincibilityFrames == 0) {		//if the invincibilityFrames is 0,
						health -= BlueLaser.damage;		//if yes then remove a certain amount of health
					}
					invincibilityFrames++;	//added one to invincibilityFrames
					if (invincibilityFrames == 2) {		//if invincibilityFrames is two,
						invincibilityFrames = 0;	// set invincibilityFrames to 0 so that i can take damage again
					}
				}
			}

			if(tempObject.getId() == ObjectID.Player1) {	//if object's id is Player1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					Ship tempShip = (Ship) tempObject;
					tempShip.setHealth(0);	//set player health to 0
				}	
			}
		}//End for
	}

	/* this render method, draws alot of gray rectangles, including one main grey rectangle to make up a boss which looks sort of a like a space ship
	 * If the engine boolean is true, also draw some rectangle that looks like an engine
	 * also draw a health bar at the bottom of the screen
	 * pre: Graphics
	 * post: draws space ship on screen, if engine is true, draw the engine, draw health bar on screen
	 */
	public void render(Graphics g) {
	
		g.setColor(Color.GRAY);//body of boss
		g.fillRect((int)x, (int)y, RECTANGLEWIDTH, RECTANGLEHEIGHT);
		
		g.setColor(Color.DARK_GRAY); //fins and tip of boss, also deck of boss
		g.fillRect((int)x + 25, (int)y+ 12, 50, 30);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int)x + RECTANGLEWIDTH, (int)y + RECTANGLEHEIGHT/4, 20, 30);

		g.setColor(Color.DARK_GRAY);
		g.fillRect((int)x-20, (int)y + RECTANGLEHEIGHT/4, 20, 30);

		g.setColor(Color.DARK_GRAY);
		g.fillRect((int)x+35, (int)y + RECTANGLEHEIGHT, 30, 20);

		if (engines == true) {		//engine of boss
			g.setColor(Color.ORANGE);
			g.fillRect((int)x+25, (int)y - 30, 50, 30);

			g.setColor(Color.ORANGE);
			g.fillRect((int)x+40, (int)y - 60, 20, 40);

			g.setColor(Color.YELLOW);
			g.fillRect((int)x+40, (int)y - 20, 20, 20);
		}
		
		g.setColor(Color.RED); //eyes of boss?
		g.fillRect((int)x + 40, (int)y+ 30, 20, 5);

		g.setColor(Color.RED);
		g.fillRect((int)x + 58, (int)y+ 27, 12, 5);
		
		g.setColor(Color.RED);
		g.fillRect((int)x + 30, (int)y+ 27, 12, 5);
		
		g.setColor(Color.WHITE);	//health bar of boss
		g.drawRect(89, Game.HEIGHT - 80, 500+1, 35);

		g.setColor(Color.RED);
		g.fillRect(90, Game.HEIGHT - 79, health/3, 34);

		Graphics2D g2d = (Graphics2D) g;		//hit box of boss	
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
	}

	/* This method updates the bosses state every tick and contains the AI for the boss. It will first check the health of the boss. Same as in boss1,
	 * if the health is less than 0, and its not in phase 2, then go to phase 2, and set velX to 0 and velY to 8, and set on to false
	 * If its health is set to 0 again, then die. if it is on in phase 2, check if the player is behide the boss, if yes, call the backTurrentMethod()
	 * Else if opener is true, (starts true). then do the opening attack which is the boss flying down the screen dropping bombPlus along the way
	 * When the boss leaves the screen, reset its y position to the top of the screen (outside it) and have it fly down slowly till its y is about at 140
	 * then set moving to true, on to true, and opener to false. if Moving is true, the boss ship will bounces around in the top section of the screen.
	 * If on is true, it will cycle through 3 attacks, similarly to how its done in boss1. The difference is that there is a higher chance that the 
	 * boss will do attack 1 and 2 over attack 3. This is done by having the random int generator go from 1 to 7. and only having 7 do the 3rd attack.
	 * 1 to 4 does the first attack, and 5 to 6 does the 2nd attack. the 3rd attack simply sets on and movement to false. set velX to 0 and sets opener to
	 * true. This will make it so that the boss does the opening attack again. in the second phase, the boss will do the opening attack none stop. at
	 * the end of tick, it will run collisions to check for projectiles
	 * 
	 */
	public void tick() {
		if (health <= 0) {	//if health is 0
			if (phase2 == false) {	//and its not in phase 2
				phase2 = true;	//set phase2 to true
				if (on == true) {	//and if it is on, set the variables below
					velX = 0;
					velY = 8;	
					on = false;
				}
				health = oHealth /4 - 100; //and increase the health
				Level.score += 500;	//and add some score
			} else {	//if the boss dies again
				level.setGameWin(true);		//set a gameWin in Level to true
				handler.removeObject(this);		//remove boss
			}
		}
		for (int i = 0; i < handler.object.size(); i++) {	//Goes through every object in game
			GameObject tempObject = handler.object.get(i);	
			if(tempObject.getId() == ObjectID.Player1) {	//If player object exists
				////ACTION GOES HERE
				x+= velX;
				y += velY;
				timer ++;

				if (phase2 == false) {// if it is not in phase 2
					if (y > tempObject.getYs()) {	//check if player is behide boss, if yes,
						machineGunTimer++;
						backTurretAttack();//fire back turrent at player
					}

					//Opening attack
					if (y >= 140 && opener == false) {	//if the opener is false, and y is greater than 140,
						engines = false;	//turn engine off :3
						on = true;	//set on to true (guns)
						movepattern =true;	//set movepattern to true
					}
					if (opener == true) {	//if opener is true, 
						strafeAttackOpen();	//run strafeAttack
					}
					if (movepattern == true) {	//if movepattern is true
						// Movement of boss ship
						if (moving == false) {	//and if moving is false
							velX = 7;	//make it move and set moving to true
							moving = true;
							velY = 2;
						}
						if (Game.inBorder(x, 10, Game.WIDTH - RECTANGLEWIDTH - 10)){ //if the bosses x is outside the parameters,
							velX = velX *-1;	//switch velX around	
						}
						if (Game.inBorder(y, 10, 200)){		//if the bosses y is outside the parameters.
							velY = velY *-1;	//switch velY around 
						}
					}
					//Attack of boss ship
					if (on == true) {	//if bosses weapons is on 
						if (buffer == true) {	//and buffer is true
							if (attack != 0)	//sets attack to 0 
								attack = 0;
							timer2 ++;
							if(timer2 >= 60 * 1) {	//if buffer is on and it's been 1 seconds, 
								attack = r.nextInt(7) + 1; //set attack to 1 to 7
								buffer = false;	//make buffer false
								timer2 = 0;//reset timers
								timer = 0;
							}
						}
						if (attack >= 1 && attack <= 4)		//if it is 1 to 4, shotGunAttack
							ShotGunAttack();
						if (attack >= 5 && attack <= 6)		//if it is 5 to 6. laser attack
							laserAttack();
						if (attack == 7)	//if it is 7
							strafeAttack();		//strafeAttack
					}
					//PHASE TWO of Boss
				} else {
					if (timer >= 20) { //every 20 mili seconds
						attack = r.nextInt(3) + 1;		//select 1 of 3 direction to drop a bomb
						switch (attack) {
						case 1:
							newBombPlus((int)x, (int)y, 90, 3, 2);
							break;
						case 2:
							newBombPlus((int)x, (int)y, 100, 3, 2);
							break;
						case 3:
							newBombPlus((int)x, (int)y, 80, 3, 2);
							break;
						}
						timer = 0;	//reset timer
					}
					if (y >= Game.HEIGHT) {		//if the boss is below the bottom of the screen,
						//Opening end
						x = r.nextInt(Game.WIDTH - 100);	//set x to a random value on screen
						y = -1*Game.HEIGHT;		// and set y to top of screen
					}
				}

			}
			////Action end
		}

		collisions();	//check for projectiles
	}

	/*This shoots 5 basic bullets in a simple even spread using recursion. if the angle is 120, return. else make a new bullet and call method again with
	 * angle + 10.
	 * pre: int angle = 70
	 * post: makes 5 new Enemy Objects
	 */
	private int shotGunRecursive(int angle) {
		if (angle == 120)
			return 0;
		else {
			basicBullet((int)x +RECTANGLEWIDTH/2 - 6, (int)y +RECTANGLEHEIGHT + 20, angle, 4);
			return shotGunRecursive(angle + 10);
		}
	}

	/* Every 15 miliseconds, call shotGunRecursive. and reset timer. do this for 1 second and then set buffer to true
	 * pre:
	 * post: buffer = true, calls shotGunRecursive 4 times
	 */
	private void ShotGunAttack() {
		//attack 1
		timer2 ++;
		if(timer == 15) {
			shotGunRecursive(70);
			timer = 0;
		}
		if (timer2 >= 60 * 1 + 1) {
			buffer = true;
			timer2 = 0;
		}
	}

	/* in one second, make a 2 new EnemyLasers. then change buffer to true and reset time
	 * pre:
	 * post: buffer = true, 2 new EnemyLaser
	 */
	private void laserAttack() {
		timer2 ++;
		if (timer == 60 * 1) {
			handler.addObject(new EnemyLaser(x, y, ObjectID.Bullet5, handler, 5, 11, -26, 61));
			handler.addObject(new EnemyLaser(x, y, ObjectID.Bullet5, handler, 5, 11, 95, 61));
			timer = 0;
		}
		if (timer2 >= 61) {
			buffer = true;
			timer2 = 0;
		}
	}

	/* This returns a rectangle that has the same dimensions as the render of this ship. used as the ships hit box
	 * pre: 
	 * post: a new Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,RECTANGLEWIDTH,RECTANGLEHEIGHT);
	}

	/* Resets the variables so that the boss redos the opening attack in the tick method
	 * pre:
	 * post: velX = 0, engines = true, on = false, movepattern = false, opener = true, velY =8,moving = false, timer = 19;
	 */
	private void strafeAttack() {
		velX = 0;
		engines = true;
		on = false;
		movepattern =false;
		opener = true;
		velY = 8;
		moving = false;
		timer = 19;
	}

	/* This spawns a bomb every 20 miliseconds and sets velY to 8. When the boss reaches the bottom of the screen. set opener to false, put the boss
	 * back to the top of the screen, and set buffer to true, and set velY to 4
	 * pre: 
	 * post: every 20 miliseconds, + 1 newBombPlus. when y >= Game.HEIGHT, buffer = true
	 */
	private void strafeAttackOpen() {
		velY = 8;
		if (timer >= 20) {
			attack = r.nextInt(3) + 1;
			switch (attack) {
			case 1:
				newBombPlus((int)x + 30, (int)y, 90, 3, 2);
				break;
			case 2:
				newBombPlus((int)x + 30, (int)y, 100, 3, 2);
				break;
			case 3:
				newBombPlus((int)x + 30, (int)y, 80, 3, 2);
				break;
			}
			timer = 0;
		}
		if (y >= Game.HEIGHT) {
			//Opening end
			opener = false;
			y = -1*Game.HEIGHT;
			velY = 4;
			buffer = true;
		}
	}

	/* every 20 miliseconds, shoot two track bullets at the player
	 * pre:
	 * post: 2 track bullets every when machineGunTimer >= 20
	 */
	private void backTurretAttack() {
		if (machineGunTimer >= 20) {
			trackBullet((int)x + 15, (int)y, 15);
			trackBullet((int)x + RECTANGLEWIDTH - 15, (int)y, 15);
			machineGunTimer = 0;
		}
	}
}