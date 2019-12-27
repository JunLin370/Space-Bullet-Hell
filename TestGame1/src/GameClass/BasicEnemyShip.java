/* This object contains the variables and AI for the basic Enemy ship in this game. All this does is move down the screen as a set pace. And
 * shoot the homingBullet Object every certain amount of ticks
 * 
 */

package GameClass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BasicEnemyShip extends Ship{
	
	private int rectangleWidth;
	private int rectangleHeight;
	
	public BasicEnemyShip(float x, float y, ObjectID id, Handler handler) {
		super(x, y, id, handler, 10);
		velY = 1;
		
		rectangleWidth = 20;
		rectangleHeight = 30;
	}

	public void tick() {
		if (health <= 0) {
			handler.removeObject(this);
			Level1.score += 100;
		}
		for (int i = 0; i < handler.object.size(); i++) {	//Gets the player object
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectID.Player1) {	//If player object exists
				//// ACTION GOES HERE
				x += velX;
				y += velY;

				if (y == Game.HEIGHT / 4) {		
					handler.addObject(new HomingBullet(x, y, ObjectID.Bullet3, handler, 10));	//make a new homing bullet object
				}
				////
			}
		}//end for
		if(Game.inBorder(x, 0, Game.WIDTH)|| Game.inBorder(y,  Game.HEIGHT * (-1), Game.HEIGHT - 100)) {	// if object is outside screen of game
			handler.removeObject(this); //remove object
		}

		collisions();
	}
	
	protected void collisions() {	//collision detection for the object 
		for (int i = 0; i < handler.object.size(); i++) {	//check all objects
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectID.Gun1) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					health -= 5;		//if yes then remove a certain amount of health
					handler.removeObject(tempObject);	//and remove the bullet
				}
			}
		}//End for
	}

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int)x, (int)y, rectangleWidth, rectangleHeight);
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,rectangleWidth,rectangleHeight);
	}
	
}
