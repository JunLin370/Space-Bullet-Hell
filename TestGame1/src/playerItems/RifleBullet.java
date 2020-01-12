/* This class is the Weapon Class used by the player to destroy Enemies, (place holder)
 * 
 */

package playerItems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import abstrackSuperClasses.GameObject;

public class RifleBullet extends GameObject{
	
	
	private Handler handler;
	private int timer;
	public static final int damage = 5;
	
	/* Constructor. Gets origin x and y position. Object id, the handler. Angle its will move in, and speed. It then uses angle and speed to determine
	 * the velX and velY
	 * pre: float x, float y. ObjectID id, Handler handler, float 0 < angle < 360, float vel > 0
	 */
	public RifleBullet(float x, float y, ObjectID id, Handler handler, float angle, float vel) {
		super(x, y, id);
		this.handler = handler;
		velX = (float) (vel * Math.cos(Math.toRadians( angle )));
		velY = (float) (vel * Math.sin(Math.toRadians( angle )));
	}

	/* Ai for the Big Rifle Bullet, really simple. Move bullet with velX and velY. if the object leaves screen, remove this object
	 * pre:
	 * post: x and y is updated by velX and velY
	 */
	public void tick() {
		x += velX;
		y += velY;
		
		if(Game.inBorder(x, 0, Game.WIDTH)|| Game.inBorder(y,  0, Game.HEIGHT ))  {
			handler.removeObject(this);
		}
	}

	/* This draws a bang on the bullet for one second. It also draws the bullet as a yellow oval.
	 * pre: Graphics
	 * post: draws oval on screen
	 */
	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval((int)x, (int)y, 5, 15);
		
		if (timer <= 40) {
			g.drawString("Bang", (int)x, (int)y);
			timer++;
		}
	}

	/* This returns a rectangle that has the same dimensions as the render of this ship. used as the bullet hit box
	 * pre: 
	 * post: a new Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,5,15);
	}
}
