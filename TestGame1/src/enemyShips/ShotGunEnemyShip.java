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

	/* Constructor, takes origin x and y location, object id, handler, and the health of ship
	 * pre: float x, float y, ObjectID id(Enemy2), Handler handler, int newHealth >0
	 * post: sets velY to 1, supers(x,y,id,handler,newHealth), sets size of ship
	 */
	public ShotGunEnemyShip(float x, float y, ObjectID id, Handler handler, int newHealth) {
		super(x, y, id, handler, newHealth);
		velY = 1;
		rectangleWidth = 40;
		rectangleHeight = 40;
	}
	
	/* collisions checks if the rectangle of this object is intersecting with either the gun object, or the player object (wip, may add more objects later)
	 * If they are, then remove health if hit by gun1, and remove the health of player and remove this ship if it intersects with player
	 * pre:
	 * post: goes throught all the objects in the game. If it is a object with the id Gun1, remove 5 health and remove bullet. If its the player object, remove 50 health to player and remove this object
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
					tempShip.setHealth(tempShip.getHealth() - 50);
					handler.removeObject(this);
				}
			}
		}//End for
	}

	/* This object is removed when health is 0 and 200 points is added. if player is alive, update the x and y with velX and velY. if the y is a certain
	 * way down the screen. Stop and after a second shoot a shotgun blast of basicBullets. After 5 more seconds, change velY to 1. Check for collision
	 * and if the enemy is outside the screen, remove object
	 * pre:
	 * post: moves down screen, stop at a certain point, makes new basicBullets, moves down screen after 5 seconds, remove object when it leaves screen
	 */
	public void tick() {
		if (health <= 0) {
			Level.score += 200;
			handler.removeObject(this);
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
		
		if(Game.inBorder(x, 0, Game.WIDTH)|| Game.inBorder(y,  Game.HEIGHT * (-1), Game.HEIGHT)) {	// if object is outside screen of game
			handler.removeObject(this); //remove object
		}

		collisions();
		
	}

	/* This renders a gray rectangle to rectangleWidth and rectangleHeights specifications. It also puts a smaller red rectangle in the middle
	 * to give it some distinction
	 * pre: Graphics
	 * post: draws two rectangles on the screen
	 */
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int)x, (int)y, rectangleWidth, rectangleHeight);
		g.setColor(Color.RED);
		g.fillRect((int)x + rectangleWidth/4, (int)y + rectangleHeight/4, rectangleWidth/2, rectangleHeight/2);
		
	}
	
	/* This returns a rectangle that has the same dimensions as the render of this ship. used as the ships hit box
	 * pre: 
	 * post: a new Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,rectangleWidth,rectangleHeight);
	}

}
