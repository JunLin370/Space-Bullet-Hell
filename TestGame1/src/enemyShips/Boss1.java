/* Jun Lin
 * This is the first boss in the game. This will have the code that controls the AI for movement, shooting
 * and also display a new healthbar on the screen
 * Currently is just a test model
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

public class Boss1 extends Ship{
	
	private final int  RECTANGLEWIDTH = 300, RECTANGLEHEIGHT = 150;		//size of the boss
	private int attack, angle, timer2, bombTimer, invincibilityFrames, dyingtimer, oHealth;	// these 5 types of variables are used for the tracking and selecting of the attacks from the boss
	private boolean on, buffer, phase2;	 //on, buffer, and phase2 are variables used in the AI of this boss
	private Random r;	
	private Level level;

	/* constructor takes in the pre-requisite x and y starting location of the object, and object id, the
	 * handler, and health of the ship. It also initialize many variables used in the tick method.   
	 * pre: float x, float y, ObjectID (Ship1), handler, health of the ship
	 * post: supers all the pre variables. sets on and buffer to false. Make a new random object. set velY to 1
	 */
	public Boss1(float x, float y, ObjectID id, Handler handler, int newHealth, Level level) {
		super(x, y, id, handler, newHealth);
		this.level = level;		
		
		oHealth = health;
		on = false;		
		buffer = false;
		phase2 = false;
		
		r = new Random();
		velY = 1;
		bombTimer = 4;

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

			if(tempObject.getId() == ObjectID.Player1) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					Ship tempShip = (Ship) tempObject;
					tempShip.setHealth(0);
				}
			}
		}//End for
	}

	/* Desc: This render method, renders a big gray rectangle at the objects x and y position, and to the variables RECTANGLEWIDTH and RECTANGLEHEIGHT's
	 * specifications. It will also render a health bar and health bar frame at the bottom of the screen. when the boss is dying, this will render addition
	 * texts reading Boom around the boss, in places of an animation
	 * pre: Graphics
	 * post: draws boss on screen
	 */
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int)x, (int)y, RECTANGLEWIDTH, RECTANGLEHEIGHT);
		g.setColor(Color.WHITE);
		g.drawRect(89, Game.HEIGHT - 80, 500+1, 35);
		g.setColor(Color.WHITE);

		g.setColor(Color.RED);
		g.fillRect(90, Game.HEIGHT - 79, health/5, 34);

		Graphics2D g2d = (Graphics2D) g;		
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());

		if(dyingtimer > 60*4 && dyingtimer < 60*6) {
			g.drawString("Boom", RECTANGLEWIDTH, RECTANGLEHEIGHT);
			g.drawString("Boom", RECTANGLEWIDTH +20, RECTANGLEHEIGHT+100);
			g.drawString("Boom", RECTANGLEWIDTH +140, RECTANGLEHEIGHT+80);
		}
		if(dyingtimer > 60*2 && dyingtimer < 60*6) {
			g.drawString("Boom", RECTANGLEWIDTH, RECTANGLEHEIGHT);
			g.drawString("Boom", RECTANGLEWIDTH +120, RECTANGLEHEIGHT+10);
			g.drawString("Boom", RECTANGLEWIDTH +10, RECTANGLEHEIGHT+70);
		}
		if(dyingtimer > 60 && dyingtimer < 60*6) {
			g.drawString("Boom", RECTANGLEWIDTH, RECTANGLEHEIGHT);
			g.drawString("Boom", RECTANGLEWIDTH +50, RECTANGLEHEIGHT);
			g.drawString("Boom", RECTANGLEWIDTH +75, RECTANGLEHEIGHT+20);
		}
	}

	/* Desc: This tick method contians the bosses AI, and will run every milisecond with the rest of the program and update the bosses state.
	 * It will first check its health. If it is below zero, phase2 will be set to true the health increased. If health is 0 again, set on to false and
	 * start a 6 second countdown to the object being removed and a variable called gameWin in Level to be set to true.
	 * For the actual AI part, first update x and y position with the velX and velY. if the boss is a certain way down the screen, set velY to 0.
	 * After 60 seconds, set on to true and select an attack. It will use a random number generator to get a number from 1 to 3, representing the 3 attacks types
	 * using a switch case, it will run one of the attacks by running a method. it will keep running that method until buffer is set to true by the attack
	 * after a certain period of time. When that happens, the attack type will be set to 0 and a second timer will start. When that timer reaches two seconds
	 * a buffer will be set to false and attack will be set to a new number from 1 to 3. If phase2 is true, the same thing will happen, except the random number
	 * generator will be between 1 and 2, and the spin attack will happen continuously. the attack will only happen if on is set to true. at the end of tick
	 * it will run the collision method 
	 * pre:
	 * post: updates this object based on the AI in this method
	 */
	public void tick() {
		if (health <= 0) {	//if the boss dies
			if (phase2 == false) {	//and its not in phase 2
				phase2 = true;		//going into phase two
				health = oHealth/4;		// increase the health by a quarter
				attack = r.nextInt(2) + 1;		//and select a random attack between 1 and 2
				Level.score += 500;		//also increase the score by 500
			} else if (on == true) {	//else if phase 2 is true, and on is true, set score to 1000, and 
				Level.score += 1000;
				on = false;
			} else if (dyingtimer == 60 * 6){
				level.setGameWin(true);
				handler.removeObject(this);
			} else {
				dyingtimer++;
			}
		}
		for (int i = 0; i < handler.object.size(); i++) {	//Goes through every object in game
			GameObject tempObject = handler.object.get(i);	
			if(tempObject.getId() == ObjectID.Player1) {	//If player object exists
				////ACTION GOES HERE
				x += velX;
				y += velY;
				timer ++;
				if (y == 110) {		//if the boss's y is at 110,
					if (velY != 0) {	//and the velY is not 0
						velY = 0;	//set velY to 0
						timer = 0;
					}
					if (timer == 60 && on != true && health > 0) {	//if the boss is alive, and is not on and it has been 1 second
						on = true;	//on to true
						timer = 0;	//reset timer
						attack = r.nextInt(3) + 1; //attack is set to a number from 1 to 3
					}
				}
				//AI for boss Attacking
				if (on == true && phase2 != true) {		//if on is true, and its not phase 2
					if (buffer == true) {		//if buffer is true
						if (attack != 0)	//sets attack to 0 
							attack = 0;
						timer2 ++; //and start a second timer
						if(timer2 >= 60 * 2) {	//if buffer is on and it's been 2 seconds, set 
							attack = r.nextInt(3) + 1;		//attack to a number from 1 to 3
							buffer = false;		//set buffer to false
							timer2 = 0;		//reset timer2
							timer = 0;	//reset timer
						}
					}
					switch (attack) {		//switch case for three attack types
					case 1:		//if attack is 1, spin attack (see methods for attack)
						spinAttack();
						break;
					case 2:		//if attack is 2, miniGunAttack
						miniGunAttack();
						break;
					case 3: 	//if attack is 3, tripleBombAttack
						tripleBombAttack();
						break;
					default:	//if attack is none of above, just exit switch case
						break;

					}
					//PHASE TWO ai of the boss
				}else if (on == true && phase2 == true){	//else if phase 2 is true, and boss is still on,
					if (buffer == true) {		//do the same buffer stragedy for the attack as above but	
						if (attack != 0)	
							attack = 0;
						timer2 ++;
						if(timer2 >= 60 * 2) {	
							attack = r.nextInt(2) + 1;	//attack is set between 1 and 2
							buffer = false;
							timer2 = 0;
							timer = 0;
						}
					}
					spinAttackMod();	//constantly do a modified version of the spin attack (see method for more detail)
					switch (attack) {	//and use switch case, same as in phase 1 to determine attack type
					case 1:
						miniGunAttack();
						break;
					case 2:
						tripleBombAttack();
						break;
					default:
						break;
					}
				}
			}
			////
		}

		collisions();	//run collisions to see if boss is in contact with any projectiles or the player
	}

	/* Desc: This is a modified verson of the spinAttack, every 15 miliseconds, spawn 4 basic bullets in all 4 directions with the angle increasing
	 * by 15 every time. If the angle is 90 or greater, reset the angle.
	 * Pre: 
	 * Post: Creates 4 Enemy objects from the boss in a spinning motion every 15 mili seconds
	 */
	private void spinAttackMod() {
		if(timer% 15 == 0) {	//if timer% 15 is 0, every 15 miliseconds
			if (angle >= 90)	//if angle is greater or equal to 90
				angle = 0;	//set angle to 0
			angle += 15;
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle, 2);	//spawn 4 bullets, each with coming out a different direction 
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+90, 2);
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+180, 2);
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+270, 2);
		}
	}

	/* Desc: This method spawns 3 bombs with a second between each bomb spawning. Each bomb will come out in a different direction in a set pattern. (to
	 * lazy to randomize :|) They will then explode at the same time. When bombTimer is equal to one, set buffer to true and reset bombTimer
	 * pre:
	 * post: spawn 3 bombs within the span of 3 seconds and then on the fourth, set buffer to true
	 */
	private void tripleBombAttack() {
		if (timer == 60) {	//if timer is 60,
			if (bombTimer == 4)		//and if the bombtimer is 4 
				newBomb((int)x + RECTANGLEHEIGHT, (int)y + RECTANGLEWIDTH/2, 90, 2, bombTimer - 1);	//spawn a bomb
			if (bombTimer == 3)		//else if bomb timer is 3
				newBomb((int)x + RECTANGLEHEIGHT, (int)y + RECTANGLEWIDTH/2, 120, 2, bombTimer - 1);	//spawn a bomb moving in a different direction
			if (bombTimer == 2)		//else if the bomb timer is 2
				newBomb((int)x + RECTANGLEHEIGHT, (int)y + RECTANGLEWIDTH/2, 60, 2, bombTimer - 1);		//spawn a another bomb
			bombTimer --;	//subtract 1 from bombTimer every 60 seconds
			timer = 0;		//and reset timer
		}
		if (bombTimer <= 1) {	//once bombTimer has gone down to 1
			buffer = true;	//set buffer to true
			bombTimer = 4;	//and reset bombTimer
		}
	}

	/* This returns a rectangle that has the same dimensions as the render of this ship. used as the ships hit box
	 * pre: 
	 * post: a new Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,RECTANGLEWIDTH,RECTANGLEHEIGHT);
	}

	/* This is the spin attack used phase one of the boss. Every 15 seconds, 4 new basic bullets will be created with a 90 degree offset from each other
	 * , and will each timer, the bullets will spawn 15 degrees to the left. the 4 bullets spawning will make it seem like its spawning in a spiral.
	 * at the same time, another timer will be counting down. Every seconds, it will spawn 4 tracking bullets that shoot directly at the player.
	 * in 7 seconds, buffer will be set to true, and timer2 reset.
	 * pre:
	 * post: spawns 4 Enemy objects every 15 miliseconds, and 4 tracking bullets every second for 7 seconds, then set buffer to true
	 */
	private void spinAttack() {
		//attack 1
		timer2 ++;	//timer2 counting up every tick
		if(timer == 15) {		//if timer = 15,
			if (angle >= 90)	//if angle is greater than 90,
				angle = 0;	//reset angle to 0
			angle += 15;	//then add 15 to angle
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle, 2);		//and spawn 4 bullets with the angle of "angle" +0, 90, 180, and 270
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+90, 2);
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+180, 2);
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+270, 2);
			timer = 0;		//and reset timer to 0
		}
		if (timer2 % 60 == 0) {	//if timer2%60 is equal to 0,
			trackBullet((int)x + RECTANGLEWIDTH/4 + 10, (int)y + RECTANGLEHEIGHT, 6);	//shoot 4 tracking bullets 
			trackBullet((int)x + RECTANGLEWIDTH/4 - 10, (int)y + RECTANGLEHEIGHT, 6);
			trackBullet((int)x + RECTANGLEWIDTH/2 + RECTANGLEWIDTH/4 + 10, (int)y + RECTANGLEHEIGHT, 6);
			trackBullet((int)x + RECTANGLEWIDTH/2 + RECTANGLEWIDTH/4 - 10, (int)y + RECTANGLEHEIGHT, 6);
		}
		if (timer2 >= 60 * 7) {		//once timer2 has reached 7 seconds
			buffer = true;		//set buffer to true
			timer2 = 0;	// and reset timer2
		}
	}
	
	/* The miniGunAttack just shoots 4 tracking bullets every 15 miliseconds for 5 seconds. At the end of which, buffer will be set to true
	 * pre:
	 * post: 4 tracking bullets every 15 mili seconds for 5 seconds. then buffer is set to true
	 */
	private void miniGunAttack() {
		timer2 ++;	//counting up timer2
		if (timer == 15) {		//every 15 miliseconds
			trackBullet((int)x + RECTANGLEWIDTH/4 + 10, (int)y + RECTANGLEHEIGHT, 6);	//spawn 4 tracking bullets
			trackBullet((int)x + RECTANGLEWIDTH/4 - 10, (int)y + RECTANGLEHEIGHT, 6);
			trackBullet((int)x + RECTANGLEWIDTH/2 + RECTANGLEWIDTH/4 + 10, (int)y + RECTANGLEHEIGHT, 6);
			trackBullet((int)x + RECTANGLEWIDTH/2 + RECTANGLEWIDTH/4 - 10, (int)y + RECTANGLEHEIGHT, 6);
			timer = 0; 	//and reset timer
		}
		if (timer2 >= 60 * 5) { 	//when timer2 has reached 5 seconds
			buffer = true;		//buffer is set to true
			timer2 = 0;
		}
	}
}
