package playerItems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import abstrackSuperClasses.GameObject;

public class BlueLaser extends GameObject{
	
	private Handler handler;
	private int laserWidth;
	private int timer, even;
	public static int damage;
	
	/* This Constructor, super(x,y,id) that being the x and y this object will originate from. handler, damage, and laserWidth. even is calculated by laserWidth
	 * and is used to make the laser shrink inwards
	 * pre: float x, float y, ObjectID id, Handler handler, int damage, int laserWidth
	 * post: handler, laserWidth, damage are initialize. even is calculated and initialize
	 */
	public BlueLaser(float x, float y, ObjectID id, Handler handler, int damage, int laserWidth) {
		super(x, y, id);
		this.handler = handler;
		this.laserWidth = laserWidth;
		even = 15 - laserWidth/2;
		this.damage = damage;
	}

	/*This gets the players location and constantly updates the x y to match the players location. the even is added to x, so that when the laser shrinks
	 * left, the laser moves right, this makes it look like its shrinking inwards. Every 5 miliseconds, laserWidth is subtracted by 2 and even is
	 * subtracted by 1. when the laserWidth is less than or = 0, remove the laser
	 * pre: 
	 * post: update x and y to player x,y. LaserWidth -= 2, even -= 1. if laserWidth <= 0, remove this object
	 */
	public void tick() {
		timer++;
		
		for (int i = 0; i < handler.object.size(); i++) {	//Goes through every object in game
			GameObject tempObject = handler.object.get(i);	
			if(tempObject.getId() == ObjectID.Player1) {	//If player object exists
				x = tempObject.getXs() + even;
				y = tempObject.getYs();
			}
		}
		
		if (timer == 5) {
			laserWidth -= 2;
			even += 1;
			timer = 0;
		}
		if (laserWidth <= 0) {
			handler.removeObject(this);
		}
	}

	/* Due to how objects are drawn. the point of origin had to be the x position of the player, and the top of the screen (0). The width would be
	 * laserWidth, and the y would be the length of the laser. This will make it look like its coming out of the player and to the top of the screen
	 * This is also done for the hitbox.
	 * pre: Graphics
	 * post: draws two rectangle
	 */
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;	
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());

		g.setColor(Color.CYAN);
		g.fillRect((int)x, 0, laserWidth, (int)y);
	}

	/* Same method as render above to return a rectangle that is where it suppose to be
	 * repersenting the hit box
	 * pre: 
	 * post: Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle ((int)x, 0, laserWidth, (int)y);
	}
}

