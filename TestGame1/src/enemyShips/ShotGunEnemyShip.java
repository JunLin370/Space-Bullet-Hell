package enemyShips;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import Levels.Level;
import abstrackSuperClasses.GameObject;
import abstrackSuperClasses.Ship;
import playerItems.BigRifleBullet;
import playerItems.BlueLaser;
import playerItems.RifleBullet;

public class ShotGunEnemyShip extends Ship{
	
	private int rectangleWidth;
	private int rectangleHeight;
	private int invincibilityFrames;

	public ShotGunEnemyShip(float x, float y, ObjectID id, Handler handler, int newHealth) {
		super(x, y, id, handler, 100);
		velY = 1;
		rectangleWidth = 40;
		rectangleHeight = 40;
	}

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
					tempShip.setHealth(tempShip.getHealth() - 50);
					handler.removeObject(this);
				}
			}
		}//End for
	}

	public void tick() {
		if (health <= 0) {
			handler.removeObject(this);
			Level.score += 200;
		}
		for (int i = 0; i < handler.object.size(); i++) {	//Goes through every object in game
			GameObject tempObject = handler.object.get(i);	
			if(tempObject.getId() == ObjectID.Player1) {	//If player object exists
				////ACTION GOES HERE
				x += velX;
				y += velY;
				if (y == Game.HEIGHT/4 && timer != 5*60) {
					timer++;
					if (velY != 0)
						velY = 0;
					if (timer == 60) {
						basicBullet((int)x,(int)y,90,5);
						basicBullet((int)x,(int)y,80,5);
						basicBullet((int)x,(int)y,100,5);
						basicBullet((int)x,(int)y,70,5);
						basicBullet((int)x,(int)y,110,5);
					}
				} 
				else
					velY = 1;
				////
			}
		}
		collisions();
	}

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int)x, (int)y, rectangleWidth, rectangleHeight);
		g.setColor(Color.RED);
		g.fillRect((int)x + rectangleWidth/4, (int)y + rectangleHeight/4, rectangleWidth/2, rectangleHeight/2);
		
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,rectangleWidth,rectangleHeight);
	}

}
