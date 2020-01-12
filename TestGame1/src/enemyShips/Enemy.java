/* Jun Lin
 * DESC: This is a class used for the enemy for the game (WIP)
 * This does nothing but go downwards at velocity of 4. If it reaches the bottom of the window, remove
 * this object from handler
 * DATE: 2019-12-11 */

package enemyShips;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import abstrackSuperClasses.GameObject;

public class Enemy extends GameObject{

	private Handler handler;
	public static int damage;
	
	/* Constructor, this takes the prerequisite origin x and y location. The object id, an instance of handler, the angle of which the bullet will
	 * travel, and the speed. It will calculate the velX and velY using the angle and vel. It will also instantiate this classes version of handler
	 * it also sets the damage 
	 * pre: float x, float y, ObjectID id (gun1), Handler handler, float 0 < angle < 360, vel > 0
	 * post: sets damage, sets velX and velY, supers (x,y,id), instantiate new instance of handler
	 */
	public Enemy(float x, float y, ObjectID id, Handler handler, float angle, int vel) {
		super(x, y, id);
		this.handler = handler;
		velX = (float) (vel * Math.cos(Math.toRadians( angle )));
		velY = (float) (vel * Math.sin(Math.toRadians( angle )));
		damage = 10;
	}

	/*This will update the x and y location according the velX and velY. it will also remove object if it leaves the screen
	 * 
	 */
	public void tick() {
		x += velX;
		y += velY;

		if (Game.inBorder(x, 0, Game.WIDTH)|| Game.inBorder(y,  Game.HEIGHT * (-1), Game.HEIGHT)) {	//and if the object is out of the screen, 
			handler.removeObject(this);	//remove it from the list
		}
	}

	/* This will render a red oval 
	 * pre: Graphics
	 * post: draws oval
	 */
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval((int)x, (int)y, 10, 10);
	}

	/* This returns a rectangle that has the same dimensions as the render of this ship. used as the bullet hit box
	 * pre: 
	 * post: a new Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,10,10);
	}
	
	
	
}
