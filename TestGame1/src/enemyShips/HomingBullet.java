/* Jun Lin	
 * DESC: This class is the code responsible for creating a bullet which moves in the direction of the player object. This class will detect
 * and set the appropriated velocities
 */

package enemyShips;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import abstrackSuperClasses.GameObject;
import abstrackSuperClasses.Ship;

public class HomingBullet extends GameObject{
	
	private Handler handler;	
	private int timer;
	public static int damage;
	
	/*Handler is responsible for setting velX and velY. Thus the constructor also contains the code that gets the players location, and then
	 * using it to to set the velX and velY appropriately
	 * pre: float x, float y (Location of origin), ObjectID, Handler, float (speed of bullet)
	 * post: this sets the velX and velY to make the object move towards the player at the desired speed (dictated by vel)
	 */
	public HomingBullet(float x, float y, ObjectID id, Handler handler, float vel) {
		super(x, y, id);
		this.handler = handler;
		timer = 0;
		
		damage = 5;

		for (int i = 0; i < handler.object.size(); i++) {	//Goes through every object in game
			GameObject tempObject = handler.object.get(i);	
			if(tempObject.getId() == ObjectID.Player1) {	//If player object exists
				float deltaX = tempObject.getXs() - x;
				float deltaY = tempObject.getYs() - y;
				float angle = (float) Math.atan2( deltaY, deltaX );

				velX = (float) (vel * Math.cos( angle ));
				velY = (float) (vel * Math.sin( angle ));
			}
		}
	}

	/* tick is moves the object by the velocity. It also checks if the object is withen the screen. If yes remove the object
	 * pre:
	 * post: add velX to x and velY to y. Removes object if it reaches bounds of window.
	 */
	public void tick() {
		x += velX;
		y += velY;
		
		if(Game.inBorder(x, 0, Game.WIDTH)|| Game.inBorder(y,  Game.HEIGHT * (-1), Game.HEIGHT))  {
			handler.removeObject(this);
		}
	}

	/* This draws the object. It is an orange oval which is 10 by 10.
	 * pre:
	 * post: Draws object on screen
	 */
	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval((int)x, (int)y, 10, 10);
		if (timer <= 200) {
			g.drawString("Bang", (int)x, (int)y);
			timer++;
		}

	}

	/* This returns a rectangle that has the same dimensions as the render of this ship. used as the bullet hit box
	 * pre: 
	 * post: a new Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,10,10);
	}
}
