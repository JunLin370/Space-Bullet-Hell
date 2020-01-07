/* This object contains the variables and AI for the basic Enemy ship in this game. All this does is move down the screen as a set pace. And
 * shoot the homingBullet Object every certain amount of ticks
 * 
 */

package enemyShips;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import GameClass.Game;
import GameClass.Handler;
import GameClass.Level;
import GameClass.ObjectID;
import abstrackSuperClasses.GameObject;
import abstrackSuperClasses.Ship;
import playerItems.BigRifleBullet;
import playerItems.RifleBullet;

public class BasicEnemyShip extends Ship{
	
	private int rectangleWidth;		// This two variable will be used whenever the size of the ship is required
	private int rectangleHeight;
	
	/* constructor takes in the pre-requisite x and y starting location of the object, and object id, the
	 * handler, and health of the ship. Constructor also declares the speed the ship is going, and size of ship
	 * pre: float x, float y, ObjectID (Ship1), handler, health of the ship
	 * post: This will set the appropriate values for rectangleWidth and recrtangleHeight. It will set vel. it will also super all pre variables
	 */
	public BasicEnemyShip(float x, float y, ObjectID id, Handler handler, int health) {
		super(x, y, id, handler, health);		
		velY = 2;		//Speed of the ship is declare here
		
		rectangleWidth = 20;		//declaration of the size of the ship is here
		rectangleHeight = 30;
	}

	/* This tick method will run 60 times a second from the handler method. It will do so with every other
	 * game object at the same time before each frame. 
	 * This tick method specifically will first check if the objects health is 0, if yes, remove the object
	 * and add 100 to score (wip).
	 * Then if the player object exists, the ship will move, then if its a fourth the way down the screen,
	 * make a tracking bullet at its current location. Then check if its outside the screen, if yes then remove the object
	 * then run the collisions method (see below) 
	 * pre:
	 * post: moves x and y according to velX and velY. Makes bullet at certain place. removes object at edge of screen. checks for collision
	 */
	public void tick() {
		if (health <= 0) {
			Level.score += 100;
			handler.removeObject(this);
		}
		for (int i = 0; i < handler.object.size(); i++) {	//Goes through every object in game
			GameObject tempObject = handler.object.get(i);	
			if(tempObject.getId() == ObjectID.Player1) {	//If player object exists
				//// ACTION GOES HERE
				x += velX;
				y += velY;

				if (y == Game.HEIGHT / 4) {		//if the ship is a quarter way down the screen then,
					trackBullet((int) x,(int) y,8);	//make a new homing bullet object
				}
				////
			}
		}//end for
		if(Game.inBorder(x, 0, Game.WIDTH)|| Game.inBorder(y,  Game.HEIGHT * (-1), Game.HEIGHT - 100)) {	// if object is outside screen of game
			handler.removeObject(this); //remove object
		}

		collisions();
	}
	
	/* collisions checks if the rectangle of this object is intersecting with either the gun object, or the player object (wip, may add more objects later)
	 * If they are, then remove health if hit by gun1, and remove the health of player and remove this ship if it intersects with player
	 * pre:
	 * post: goes throught all the objects in the game. If it is a object with the id Gun1, remove 5 health and remove bullet. If its the player object, remove 50 health to player and remove this object
	 */
	protected void collisions() {	//collision detection for the object 
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
			
			if(tempObject.getId() == ObjectID.Player1) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					Ship tempShip = (Ship) tempObject;	
					tempShip.setHealth(tempShip.getHealth() - 50);	//remove player health
					handler.removeObject(this);	//remove this object
				}
			}
		}//End for
	}
	
	/* This is the render method, and is ran from the handler class. This will output the image of the ship on screen
	 * (WIP) is going to be sprite model/ animation, not just a gray square but thats what it is for now.
	 * pre: 
	 * post: puts shape on screen
	 */
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int)x, (int)y, rectangleWidth, rectangleHeight);
	}
	
	/* This returns a rectangle that has the same dimensions as the render of this ship. used as the ships hit box
	 * pre: 
	 * post: a new Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,rectangleWidth,rectangleHeight);
	}
	
}
