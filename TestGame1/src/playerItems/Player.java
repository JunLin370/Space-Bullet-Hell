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

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,OVALWIDTH,OVALWIDTH);
	}

	public void tick() {	//This updates the x and y coords of the object
		if (health == 0) {
			handler.removeObject(this);
		}
		switch (weaponType) {
		case 1:
			if (energyLevel > 0 && firing == true) {
				timer ++;
				machineGun();
				energyLevel --;
			}else if (energyLevel <= 0 && firing == true){
				energyLevel ++;
				timer = 0;
			}else if (energyLevel <= 200) {
				energyLevel ++;
				timer = 6;
			}
			break;
		case 2:
			if (energyLevel >= 190 && firing == true) {
				laserGun();
				energyLevel -= energyLevel;
			}else if (energyLevel <= 200) {
				switch (weaponLevel) {
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
				timer = 6;
			}
		}

		x += velX;
		y += velY;
		
		x = Game.fborder((int)x, 0, Game.WIDTH - 27);
		y = Game.fborder((int)y, 0, Game.HEIGHT - 55);
		
		collisions();
	}

	public void render(Graphics g) {	//Makes the player Green and fills oval
		g.setColor(Color.GREEN);
		g.fillOval((int)x, (int)y, OVALWIDTH, OVALWIDTH);
		
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
		
		g.setColor(Color.WHITE);
		g.drawRect(10, 10, 300, 15);
		g.setColor(Color.WHITE);
		
		g.setColor(Color.RED);
		g.fillRect(11, 11, health*3 -1, 14);
		
		g.setColor(Color.WHITE);
		g.drawRect(10, 30, 300, 15);
		
		if (weaponType == 1) {
			g.setColor(Color.YELLOW);
			g.fillRect(11, 31, energyLevel*3/2 -2, 14);
			
			for (int i = 11; i < 310; i+= 10) {
				g.setColor(Color.BLACK);
				g.fillRect(i, 31, 2, 14);
			}
		}

		if (weaponType == 2) {
			g.setColor(Color.CYAN);
			g.fillRect(10, 31, energyLevel*3/2, 14);
		}
	}
	
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
					if (invincibilityFrames == 2) {
						invincibilityFrames = 0;
					}
				}
			}
			
		}//end for
	}
	
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
					timer = 0;
				}
				break;
			case 4:
				if (timer == 6) {
					handler.addObject(new BigRifleBullet(x + OVALWIDTH/2 - 5 ,y, ObjectID.Gun2, handler, 270, 15 ));
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
				}
				if (timer == 7) {
					handler.addObject(new RifleBullet(x - 10, y, ObjectID.Gun1, handler, 250, 15));
					handler.addObject(new RifleBullet(x + OVALWIDTH/2 -3, y, ObjectID.Gun1, handler, 270, 15));
					handler.addObject(new RifleBullet(x + OVALWIDTH + 5, y, ObjectID.Gun1, handler, 290, 15));
					timer = 0;
				}
		}
	}
	
	public void setFire(boolean firing) {
		this.firing = firing;
	}
	public boolean getFire() {
		return firing;
	}
}