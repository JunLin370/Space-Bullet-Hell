/* This is the class for bomb bullets. It is a object on screen that at a certain time, or if it touches the player, explodes and creates 6 normal bullets
 * the timer and speed and angle are variables.
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

public class BombBullet extends GameObject{
	
	private final int bombWidth = 30;	//size of the bomb (oval)
	private Handler handler;	//new handler instance
	private int timer, ticker;	//variables used in the timing of the bomb
	public static int damage;
	
	/* This will get the starting location of the bomb bullet, the id, an instance of handler, the angle it will travel, velocity it will travel at, and a timer
	 * in seconds of when the bomb will explode
	 * pre: the starting x, y location. ObjectID (bullet4), handler, angle its going to move, velocity of bullet, timer for explosion
	 */
	public BombBullet(float x, float y, ObjectID id, Handler handler, float angle, float vel, int timer) {
		super(x, y, id);	
		this.handler = handler;
		velX = (float) (vel * Math.cos(Math.toRadians( angle )));	//these two will set the velocity of the bullet according the angle its wanter to move and speed
		velY = (float) (vel * Math.sin(Math.toRadians( angle )));
		this.timer = timer;		
		damage = 50;
	}
	
	/* This is the tick method that runs 60 times a second from the handler class for the bomb bullet class. 
	 * This will first move the bomb bullet according to the x and y velocity. It will then add one to ticker. If ticker equals to the timer times 60, run explode
	 * method. Then run collision detection method(see collisions()). It will then check if the game object is outside of the screen. if yes, remove the object
	 * pre:
	 * post: move object according to velX and velY. Run explode method if ticker = timer*60. run collisions(). check border and remove if outside
	 */
	public void tick() {
		// Bomb AI
		x += velX;
		y += velY;
		ticker ++;
		if (ticker == timer* 60) {
			explode();
		}

		// Bomb Ai end
		collisions();
		if (Game.inBorder(x, 0, Game.WIDTH)|| Game.inBorder(y,  Game.HEIGHT * (-1), Game.HEIGHT - 100)) {	//and if the object is out of the screen, 
			handler.removeObject(this);	//remove it from the list
		}
	}

	/* This method is ran from the handler class. It will put the shape of the bomb bullet on screen (wip). going to be sprite in future.
	 * pre:
	 * post: puts shapes on screen
	 */
	public void render(Graphics g) {
		g.setColor(Color.RED);		//inside of bomb
		g.fillOval((int)x, (int)y, bombWidth, bombWidth);
		
		g.setColor(Color.YELLOW);	//out line of bomb
		g.drawOval((int)x-2, (int) y-2, bombWidth+4, bombWidth+4);
		
		Graphics2D g2d = (Graphics2D) g;	//this is hitbox of bomb
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
		
	}
	
	private int explodeRecursive(int angle) {
		if (angle >= 360) {
			return angle;
		}else {
			handler.addObject(new Enemy(x, y, ObjectID.Bullet1, handler, angle, 5));
			return explodeRecursive(angle + 45);
		}
	}
	
	/* This method is ran when the object wants to explode. This will make 6 new Enemy objects and send then in 6 different directions by using a recursive method. (not random). It will then remove this object
	 * pre: 
	 * post: removes this object. Makes 6 new enemy objects
	 */
	private void explode() {
		explodeRecursive(0);
		handler.removeObject(this);
	}
	
	/* This is the collision dectection method for this object. It will check if it is in contact with the player object. If yes then explode and remove 50 health
	 * from the player object.
	 * pre: 
	 * post: checks if this object came in contact with the player. If yes remove health and run explode method
	 */
	protected void collisions() {
		for (int i = 0; i < handler.object.size(); i++) {	//check all objects
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectID.Player1) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					Ship tempShip = (Ship) tempObject;
					explode();	// run explode method
					tempShip.setHealth(tempShip.getHealth() - 30);	//remove 50 health from player object
					
					
				}
			}
		}//End for
	}
	
	/* This returns a rectangle that has the same dimensions as the render of this ship. used as the ships hit box
	 * pre: 
	 * post: a new Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,bombWidth,bombWidth);
	}

}
