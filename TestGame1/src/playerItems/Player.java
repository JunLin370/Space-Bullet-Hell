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
import enemyShips.HomingBullet;

import java.awt.*;
import java.awt.event.*;

public class Player extends Ship  {

	private static final int OVALWIDTH = 30;
	private boolean firing;
	private int weaponLevel, weaponType;

	public Player(float x, float y, ObjectID id, Handler handler, int weaponLevel, int weaponType) {
		super(x, y, id,handler, 100);
		this.weaponLevel = weaponLevel;
		this.weaponType = weaponType;
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,OVALWIDTH,OVALWIDTH);
	}
	
	public void tick() {	//This updates the x and y coords of the object
		if (health == 0) {
			handler.removeObject(this);
		}
		
		timer ++;
		
		switch (weaponType) {
		case 1:
			machineGun();
			break;
		case 2:
			laserGun();
			break;
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
		
		g.setColor(Color.RED);
		g.fillRect(10, 10, health*3, 15);
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
			
		}//end for
	}
	
	private void laserGun() {
		switch (weaponLevel) {
		case 1:
			if (timer == 50) {
				handler.addObject(new BlueLaser(x + 10, y, ObjectID.Gun3, handler, 3, 5));
				timer = 0;
			}
			break;
		case 2:
			if (timer == 50) {
				handler.addObject(new BlueLaser(x + 10, y, ObjectID.Gun3, handler, 4, 10));
				timer = 0;
			}
			break;
		case 3:
			if (timer == 50) {
				handler.addObject(new BlueLaser(x + 10, y, ObjectID.Gun3, handler, 5, 15));
				timer = 0;
			}
			break;
		case 4:
			if (timer == 50) {
				handler.addObject(new BlueLaser(x + 10, y, ObjectID.Gun3, handler, 7, 20));
				timer = 0;
			}
			break;
		case 5:
			if (timer == 50) {
				handler.addObject(new BlueLaser(x + 10, y, ObjectID.Gun3, handler, 15, 50));
				timer = 0;
			}
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
	
}