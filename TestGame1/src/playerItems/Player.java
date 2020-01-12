/* Jun Lin
 * DESC: (WIP) This Class is the first object i am building. It is the labeled Player and does not work :(
 * DATE: 2019-12-12 */
package playerItems;

import javax.swing.*;
import javax.swing.Timer;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import GameClass.Game.STATE;
import abstrackSuperClasses.GameObject;
import abstrackSuperClasses.Ship;
import enemyShips.BombBullet;
import enemyShips.Enemy;
import enemyShips.EnemyLaser;
import enemyShips.HomingBullet;

import java.awt.*;
import java.awt.event.*;

public class Player extends Ship  {

	private static final int OVALWIDTH = 30;
	private boolean firing;
	private int weaponLevel, weaponType, energyLevel, invincibilityFrames;

	/* Constructor, gets origin x and y location, objectID (Player1), handler, the weaponLevel and weaponType. the energy Level is set to 200 and
	 * the weaponLevel and weaponType are initialize. Depending on the weapon type, the timer will start at different times
	 * 
	 */
	public Player(float x, float y, ObjectID id, Handler handler, int weaponLevel, int weaponType) {
		super(x, y, id,handler, 100);
		this.weaponLevel = weaponLevel;
		this.weaponType = weaponType;
		energyLevel = 200;
		switch (weaponType) {
		case 1:
			timer = 6;
			break;
		case 2:
			timer = 49;
			break;
		}
	}

	/* This returns a rectangle that will be used for the players hitbox by other classes
	 * pre:
	 * post: Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,OVALWIDTH,OVALWIDTH);
	}

	/* Remove this object if the health is 0
	 * A switch case determines which weapon is being used. If the machineGun is being used, when the machine is firing, and the math related to the weapon being used
	 * will reduce the ammo bar and increase it when not in use. the laser is similar but works differently, depending on power level, recharging slower.
	 * x and y location is updated with velX and velY, and checks if the object is within the screen and puts keeps the player in with fborder method
	 * pre:
	 * post: may call machineGun method or laserGun method depending on weaponType. updates x y with velX and velY, prevents player for leaving screen
	 * removes object if health = 0
	 */
	public void tick() {	//This updates the x and y coords of the object
		if (health == 0) {
			handler.removeObject(this);
		}
		switch (weaponType) {	//switch case for weaponType
		case 1:	//if weaponType is 1
			if (energyLevel > 0 && firing == true) {	//and if you have energy and is shooting
				timer ++;	//add to timer
				machineGun();	//and shoot
				energyLevel --;	//and minus 1 to energy
			}else if (energyLevel <= 0 && firing == true){	//if you have no energy but is shooting
				energyLevel ++;	//energy plus 1
				timer = 0;	//but timer is set to 0 (preventing from shooting)
			}else if (energyLevel <= 200) {	//if energy is less than max
				energyLevel ++;	//add to energy
				timer = 6;	//and set time to 6 (ready to shoot)
			}
			break;
		case 2:	//if weaponType is 2
			if (energyLevel >= 190 && firing == true) {	//and if energy is almost full and the player is shooting
				laserGun();	//shoot laser
				energyLevel -= energyLevel;	//and deplete energy/ammo
			}else if (energyLevel <= 200) {
				switch (weaponLevel) {	//depending on weaponLevel, weapon charges slower
				case 1:
					energyLevel += 3;
					break;
				case 2:
					energyLevel += 3;
					break;
				case 3:
					energyLevel += 2;
					break;
				case 4:
					energyLevel += 2;
					break;
				case 5:
					energyLevel += 1;
					break;
				}
			}
		}

		x += velX;		
		y += velY;
		
		x = Game.fborder((int)x, 0, Game.WIDTH - 27);	//prevents player from leaving screen
		y = Game.fborder((int)y, 0, Game.HEIGHT - 55);
		
		collisions();	//check for collision
	}

	/* Renders a green circle as player with white box as hitbox. Also renders the health bar at the top left of the screen
	 * will render a ammo bar underneath it. The visuals will change depending on weaponType.
	 * pre: Graphics
	 * post: draws player on x y pos, draws health bar and ammo bar
	 */
	public void render(Graphics g) {	//Makes the player Green and fills oval
		g.setColor(Color.GREEN);	//renders green circle
		g.fillOval((int)x, (int)y, OVALWIDTH, OVALWIDTH);
		
		Graphics2D g2d = (Graphics2D) g;	//renders hit box
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
		
		g.setColor(Color.WHITE);	//health bar frame
		g.drawRect(10, 10, 300, 15);
		g.setColor(Color.WHITE);
		
		g.setColor(Color.RED);		//health bar
		g.fillRect(11, 11, health*3 -1, 14);
		
		g.setColor(Color.WHITE);		//ammo bar frame
		g.drawRect(10, 30, 300, 15);
		
		if (weaponType == 1) {	//if weapon type 1
			g.setColor(Color.YELLOW);	//ammo bar
			g.fillRect(11, 31, energyLevel*3/2 -2, 14);
			
			for (int i = 11; i < 310; i+= 10) {	//Separator for ammo (just for looks)
				g.setColor(Color.BLACK);
				g.fillRect(i, 31, 2, 14);
			}
		}

		if (weaponType == 2) {	//if weapon type 2
			g.setColor(Color.CYAN);	 //render blue energy bar
			g.fillRect(10, 31, energyLevel*3/2, 14);
		}
	}
	
	/*collision detection for player class. See if player is in contact with any of the bullet objects. If yes, remove the bullet object and remove
	 * the amount of health retrieved from the bullet class. if its bullet5 (the laser), dont remove the object but instead take damage and have an 
	 * invincibility frame count so the player doesn't die instantly 
	 * pre: 
	 * post: subtracts health from player if player is hit
	 */
	protected void collisions() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectID.Bullet1) {
				if(getBounds().intersects(tempObject.getBounds())){
					health -= Enemy.damage;
					handler.removeObject(tempObject);
				}
			}
			
			if(tempObject.getId() == ObjectID.Bullet2) {
				if(getBounds().intersects(tempObject.getBounds())){
					health -= HomingBullet.damage;
					handler.removeObject(tempObject);
				}
			}
			
			if(tempObject.getId() == ObjectID.Bullet3) {
				if(getBounds().intersects(tempObject.getBounds())){
					health -= BombBullet.damage;
					handler.removeObject(tempObject);
				}
			}
			
			if(tempObject.getId() == ObjectID.Bullet4) {
				if(getBounds().intersects(tempObject.getBounds())){
					health -= BombBullet.damage;
					handler.removeObject(tempObject);
				}
			}
			
			if(tempObject.getId() == ObjectID.Bullet5) {
				if(getBounds().intersects(tempObject.getBounds())){
					if (invincibilityFrames == 0) {
						health -= EnemyLaser.damage;		//if yes then remove a certain amount of health
					}
					invincibilityFrames++;
					if (invincibilityFrames == 10) {
						invincibilityFrames = 0;
					}
				}
			}
			
		}//end for
	}
	
	/*This is the laserGun method that shoots a laser at the players location when call. Depending on weaponLevel, laser will be more powerful and bigger
	 *pre:
	 *post: makes new LaserObject 
	 */
	private void laserGun() {
		switch (weaponLevel) {
		case 1:
			handler.addObject(new BlueLaser(x + 10, y, ObjectID.Gun3, handler, 3, 5));
			break;
		case 2:
			handler.addObject(new BlueLaser(x + 10, y, ObjectID.Gun3, handler, 4, 10));
			break;
		case 3:
			handler.addObject(new BlueLaser(x + 10, y, ObjectID.Gun3, handler, 5, 15));

			break;
		case 4:
			handler.addObject(new BlueLaser(x + 10, y, ObjectID.Gun3, handler, 7, 20));

			break;
		case 5:
			handler.addObject(new BlueLaser(x + 10, y, ObjectID.Gun3, handler, 15, 50));
			break;
		}
	}
	
	/* This method creates 1 or more rifleBullets /BigRifleBullets, depending on weaponLevel. 
	 * pre:
	 * post: 1 or more new rifleBullets /BigRifleBullets, depending on weaponLevel. 
	 */
	private void machineGun() {
		switch (weaponLevel) {
			case 1:
				if (timer == 7) {
					handler.addObject(new RifleBullet(x + OVALWIDTH/2 - 2, y, ObjectID.Gun1, handler, 270, 15));
					timer = 0;
				}
				break;
			case 2:
				if (timer == 7) {
					handler.addObject(new RifleBullet(x + 5, y, ObjectID.Gun1, handler, 270, 15));
					handler.addObject(new RifleBullet(x + OVALWIDTH - 10, y, ObjectID.Gun1, handler, 270, 15));
					timer = 0;
				}
				break;
			case 3:
				if (timer == 7) {
					handler.addObject(new BigRifleBullet(x + OVALWIDTH/2 - 5,y, ObjectID.Gun2, handler, 270, 15 ));
					handler.addObject(new RifleBullet(x - 10, y, ObjectID.Gun1, handler, 250, 15));
					handler.addObject(new RifleBullet(x + OVALWIDTH + 5, y, ObjectID.Gun1, handler, 290, 15));
					timer = 0;
				}
				break;
			case 4:
				if (timer == 6) {
					handler.addObject(new BigRifleBullet(x + OVALWIDTH/2 - 5 ,y, ObjectID.Gun2, handler, 270, 15 ));
					handler.addObject(new RifleBullet(x + OVALWIDTH + 5, y, ObjectID.Gun1, handler, 285, 15));
					handler.addObject(new RifleBullet(x - 10, y, ObjectID.Gun1, handler, 255, 15));
					handler.addObject(new RifleBullet(x + OVALWIDTH + 5, y, ObjectID.Gun1, handler, 295, 15));
					handler.addObject(new RifleBullet(x - 10, y, ObjectID.Gun1, handler, 245, 15));
				}
				if (timer == 7) {
					handler.addObject(new RifleBullet(x, y, ObjectID.Gun1, handler, 270, 15));
					handler.addObject(new RifleBullet(x + OVALWIDTH - 5, y, ObjectID.Gun1, handler, 270, 15));
					timer = 0;
				}
				break;
			case 5:
				if (timer == 6) {
					handler.addObject(new BigRifleBullet(x + OVALWIDTH/2 + 7 ,y, ObjectID.Gun2, handler, 270, 15 ));
					handler.addObject(new BigRifleBullet(x + OVALWIDTH/4 - 8 ,y, ObjectID.Gun2, handler, 270, 15 ));
					handler.addObject(new RifleBullet(x + OVALWIDTH + 5, y, ObjectID.Gun1, handler, 285, 15));
					handler.addObject(new RifleBullet(x - 10, y, ObjectID.Gun1, handler, 255, 15));
					handler.addObject(new RifleBullet(x + OVALWIDTH + 5, y, ObjectID.Gun1, handler, 295, 15));
					handler.addObject(new RifleBullet(x - 10, y, ObjectID.Gun1, handler, 245, 15));
				}
				if (timer == 7) {
					handler.addObject(new BigRifleBullet(x - 10, y, ObjectID.Gun1, handler, 250, 15));
					handler.addObject(new RifleBullet(x + OVALWIDTH/2 -3, y, ObjectID.Gun1, handler, 270, 15));
					handler.addObject(new BigRifleBullet(x + OVALWIDTH + 5, y, ObjectID.Gun1, handler, 290, 15));
					timer = 0;
				}
		}
	}
	
	/* setter for the firing boolean
	 * pre: boolean firing
	 * post: changes firing to new firing
	 */
	public void setFire(boolean firing) {
		this.firing = firing;
	}

	/* gets firing boolean
	 * pre: 
	 * post: returns firing
	 */
	public boolean getFire() {
		return firing;
	}
}