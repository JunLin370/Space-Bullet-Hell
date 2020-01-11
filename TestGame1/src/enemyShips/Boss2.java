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
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int)x + 25, (int)y+ 12, 50, 30);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int)x + RECTANGLEWIDTH, (int)y + RECTANGLEHEIGHT/4, 20, 30);

		g.setColor(Color.DARK_GRAY);
		g.fillRect((int)x-20, (int)y + RECTANGLEHEIGHT/4, 20, 30);

		g.setColor(Color.DARK_GRAY);
		g.fillRect((int)x+35, (int)y + RECTANGLEHEIGHT, 30, 20);

		if (engines == true) {
			g.setColor(Color.ORANGE);
			g.fillRect((int)x+25, (int)y - 30, 50, 30);

			g.setColor(Color.ORANGE);
			g.fillRect((int)x+40, (int)y - 60, 20, 40);

			g.setColor(Color.YELLOW);
			g.fillRect((int)x+40, (int)y - 20, 20, 20);
		}
		
		g.setColor(Color.RED);
		g.fillRect((int)x + 40, (int)y+ 30, 20, 5);

		g.setColor(Color.RED);
		g.fillRect((int)x + 58, (int)y+ 27, 12, 5);
		
		g.setColor(Color.RED);
		g.fillRect((int)x + 30, (int)y+ 27, 12, 5);
		
		g.setColor(Color.WHITE);
		g.drawRect(89, Game.HEIGHT - 80, 500+1, 35);
		g.setColor(Color.WHITE);

		g.setColor(Color.RED);
		g.fillRect(90, Game.HEIGHT - 79, health/3, 34);

		Graphics2D g2d = (Graphics2D) g;		
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
	}


	/*
	 * 
	 */
	public void tick() {
		if (health <= 0) {
			if (phase2 == false) {
				phase2 = true;
				if (on == true) {
					velX = 0;
					velY = 8;
					on = false;
				}
				health = oHealth /4;
				attack = r.nextInt(2) + 1;
				Level.score += 500;
			} else {
				level.setGameWin(true);
				handler.removeObject(this);
			}
		}
		for (int i = 0; i < handler.object.size(); i++) {	//Goes through every object in game
			GameObject tempObject = handler.object.get(i);	
			if(tempObject.getId() == ObjectID.Player1) {	//If player object exists
				////ACTION GOES HERE
				x+= velX;
				y += velY;
				timer ++;

				if (phase2 == false) {
					if (y > tempObject.getYs()) {
						machineGunTimer++;
						backTurrentAttack();
					}

					//Opening attack
					if (y >= 140 && opener == false) {
						engines = false;
						on = true;
						movepattern =true;
					}
					if (opener == true) {
						strafeAttackOpen();
					}
					if (movepattern == true) {
						// Movement of boss ship
						if (moving == false) {
							velX = 9;
							moving = true;
							velY = 2;
						}
						if (Game.inBorder(x, 10, Game.WIDTH - RECTANGLEWIDTH - 10)){
							velX = velX *-1;
						}
						if (Game.inBorder(y, 10, 200)){
							velY = velY *-1;
						}
					}
					//Attack of boss ship
					if (on == true) {
						if (buffer == true) {
							if (attack != 0)	//sets attack to 0 
								attack = 0;
							timer2 ++;
							if(timer2 >= 60 * 1) {	//if buffer is on and it's been 2 seconds, set 
								attack = r.nextInt(7) + 1;
								buffer = false;
								timer2 = 0;
								timer = 0;
							}
						}
						if (attack >= 1 && attack <= 4)
							ShotGunAttack();
						if (attack >= 5 && attack <= 6)
							laserAttack();
						if (attack == 7)
							strafeAttack();
					}
				} else {//Phase two Boss Ai
					if (timer >= 20) {
						attack = r.nextInt(3) + 1;
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
						timer = 0;
					}
					if (y >= Game.HEIGHT) {
						//Opening end
						x = r.nextInt(Game.WIDTH - 100);
						y = -1*Game.HEIGHT;
					}
				}

			}
			////Action end
		}

		collisions();
	}

	public int shotGunRecursive(int angle) {
		if (angle == 120)
			return 0;
		else {
			basicBullet((int)x +RECTANGLEWIDTH/2 - 6, (int)y +RECTANGLEHEIGHT + 20, angle, 4);
			return shotGunRecursive(angle + 10);
		}
	}

	public void ShotGunAttack() {
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

	public void laserAttack() {
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

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,RECTANGLEWIDTH,RECTANGLEHEIGHT);
	}

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

	private void backTurrentAttack() {
		if (machineGunTimer >= 20) {
			trackBullet((int)x + 15, (int)y, 15);
			trackBullet((int)x + RECTANGLEWIDTH - 15, (int)y, 15);
			machineGunTimer = 0;
		}
	}
}