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
	private boolean on, buffer, phase2, dying;	
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
		on = false;		//on and buffer are variables used in the AI of this boss
		buffer = false;
		phase2 = false;
		dying = false;
		
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
			
			if(tempObject.getId() == ObjectID.Gun2) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					health -= BigRifleBullet.damage;		//if yes then remove a certain amount of health
					handler.removeObject(tempObject);	//and remove the bullet
				}
			}
			
			if(tempObject.getId() == ObjectID.Gun3) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					if (invincibilityFrames == 0) {
						health -= BlueLaser.damage;		//if yes then remove a certain amount of health
					}
					invincibilityFrames++;
					if (invincibilityFrames == 2) {
						invincibilityFrames = 0;
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

			g.drawString("BOSS HEALTH: " + health, 15, 80);
	}


	/*
	 * 
	 */
	public void tick() {
		if (health <= 0) {
			if (phase2 == false) {
				phase2 = true;
				health = oHealth/4;
				attack = r.nextInt(2) + 1;
				Level.score += 500;
			} else if (on == true) {
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
				if (y == 110) {
					if (velY != 0) {
						velY = 0;
						timer = 0;}
					if (timer == 60 && on != true && health != 0) {
						on = true;
						timer = 0;
						attack = r.nextInt(3) + 1;
					}
				}
				if (on == true && phase2 != true) {	///attack part of the boss ai
					if (buffer == true) {
						if (attack != 0)	//sets attack to 0 
							attack = 0;
						timer2 ++;
						if(timer2 >= 60 * 2) {	//if buffer is on and it's been 2 seconds, set 
							attack = r.nextInt(3) + 1;
							buffer = false;
							timer2 = 0;
							timer = 0;
						}
					}
					switch (attack) {
					case 1:
						spinAttack();
						break;
					case 2:
						miniGunAttack();
						break;
					case 3: 
						tripleBombAttack();
						break;
					default:
						break;

					}
				}else if (on == true && phase2 == true){ //-----------PHASE 2 AI of Boss------------
					if (buffer == true) {
						if (attack != 0)	//sets attack to 0 
							attack = 0;
						timer2 ++;
						if(timer2 >= 60 * 2) {	//if buffer is on and it's been 2 seconds, set 
							attack = r.nextInt(2) + 1;
							buffer = false;
							timer2 = 0;
							timer = 0;
						}
					}
					spinAttackMod();
					switch (attack) {
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

		collisions();
	}

	private void spinAttackMod() {
		if(timer% 15 == 0) {
			if (angle >= 90)
				angle = 0;
			angle += 15;
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle, 2);
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+90, 2);
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+180, 2);
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+270, 2);
		}
	}

	private void tripleBombAttack() {
		if (timer == 60) {
			if (bombTimer == 4)
				newBomb((int)x + RECTANGLEHEIGHT, (int)y + RECTANGLEWIDTH/2, 90, 2, bombTimer - 1);
			if (bombTimer == 3)
				newBomb((int)x + RECTANGLEHEIGHT, (int)y + RECTANGLEWIDTH/2, 120, 2, bombTimer - 1);
			if (bombTimer == 2)
				newBomb((int)x + RECTANGLEHEIGHT, (int)y + RECTANGLEWIDTH/2, 60, 2, bombTimer - 1);
			bombTimer --;
			timer = 0;
		}
		if (bombTimer <= 1) {
			buffer = true;
			bombTimer = 4;
		}
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,RECTANGLEWIDTH,RECTANGLEHEIGHT);
	}

	public void spinAttack() {
		//attack 1
		timer2 ++;
		if(timer == 15) {
			if (angle >= 90)
				angle = 0;
			angle += 15;
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle, 2);
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+90, 2);
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+180, 2);
			basicBullet((int)x +RECTANGLEWIDTH/2, (int)y +RECTANGLEHEIGHT, angle+270, 2);
			timer = 0;
		}
		if (timer2 % 60 * 3 == 0) {
			trackBullet((int)x + RECTANGLEWIDTH/4 + 10, (int)y + RECTANGLEHEIGHT, 6);
			trackBullet((int)x + RECTANGLEWIDTH/4 - 10, (int)y + RECTANGLEHEIGHT, 6);
			trackBullet((int)x + RECTANGLEWIDTH/2 + RECTANGLEWIDTH/4 + 10, (int)y + RECTANGLEHEIGHT, 6);
			trackBullet((int)x + RECTANGLEWIDTH/2 + RECTANGLEWIDTH/4 - 10, (int)y + RECTANGLEHEIGHT, 6);
		}
		if (timer2 >= 60 * 7) {
			buffer = true;
			timer2 = 0;
		}
	}
	
	public void miniGunAttack() {
		timer2 ++;
		if (timer == 15) {
			trackBullet((int)x + RECTANGLEWIDTH/4 + 10, (int)y + RECTANGLEHEIGHT, 6);
			trackBullet((int)x + RECTANGLEWIDTH/4 - 10, (int)y + RECTANGLEHEIGHT, 6);
			trackBullet((int)x + RECTANGLEWIDTH/2 + RECTANGLEWIDTH/4 + 10, (int)y + RECTANGLEHEIGHT, 6);
			trackBullet((int)x + RECTANGLEWIDTH/2 + RECTANGLEWIDTH/4 - 10, (int)y + RECTANGLEHEIGHT, 6);
			timer = 0;
		}
		if (timer2 >= 60 * 5) {
			buffer = true;
			timer2 = 0;
		}
	}
}
