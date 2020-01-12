package enemyShips;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import abstrackSuperClasses.GameObject;

public class EnemyLaser extends GameObject {
	private Handler handler;
	private int laserWidth, relX, relY;
	private int timer, even;
	public static int damage;
	private int tempDamage;
	private boolean active;
	
	/* Contructor for the Laser Class used by enemies on the player. It needs the origin x and y. Object id, the handler, amount of damage it will do
	 * , width of the laser, and relative position of laser from point of origin(kinda redundant but its too late to change)
	 * pre: float x, float y, ObjectID id, Handler handler, int damage, laserWidth > 0, int posX, int posY
	 * post: super(x,y,id), laserWidth = newLaserWidth, instantiate new handler, gets even, sets relX and relY
	 */
	public EnemyLaser(float x, float y, ObjectID id, Handler handler, int damage, int laserWidth, int posX, int posY) {
		super(x, y, id);
		this.handler = handler;
		this.laserWidth = laserWidth;
		even = 15 - laserWidth/2;
		tempDamage = damage;
		active = false;
		this.damage = 0;
		
		x = (int) x + posX;
		y = (int) y + posY;
		relX = posX;
		relY = posY;
	}

	/* tick method updates state of this laser once ever milisecond. It will get the bosses location, and then reset the x and y location to the bosses
	 * new location (if its moving). It it will then after one second, change active to true and set time to 0. After active is true, the 
	 * laserWidth -= 2, and even will + 1. This is what makes the laser look like it is shrinking towards the middle as the laserWidth was be reduced 
	 * twice to the left, and the even will push the new x location once to the right. When laserWidth <= 0, the laser will be removed.
	 * pre:
	 * post: gets new x and y of boss, and after one second, change active to true and set damage to tempDamage. subtracts from laserWidth and adds to even until laserWidth = 0
	 */
	public void tick() {
		timer++;
		
		for (int i = 0; i < handler.object.size(); i++) {	//Goes through every object in game
			GameObject tempObject = handler.object.get(i);	
			if(tempObject.getId() == ObjectID.Boss2) {	//If player object exists
				x = tempObject.getXs() + even + relX;
				y = tempObject.getYs() + relY;
			}
		}
		
		if (timer == 60 * 1 && active == false) {
			active = true;
			timer = 0;
			damage = tempDamage;
		}
		
		if (active == true) {
			if (timer == 5) {
				laserWidth -= 2;
				even += 1;
				timer = 0;
			}
			if (laserWidth <= 0) {
				handler.removeObject(this);
			}
		}
	}

	/* While active is false, it will render a red line indicating where the laser will come out. After one second in tick, the damage will be change from
	 * 0 to tempDamage, and the laser will be rendered
	 * pre: Graphics
	 * post: draw red line while active false, draws laser and hitbox while active is true
	 */
	public void render(Graphics g) {
		if (active == true) {
			Graphics2D g2d = (Graphics2D) g;	
			g.setColor(Color.WHITE);
			g2d.draw(getBounds());

			g.setColor(Color.RED);
			g.fillRect((int)x+1, (int)y+1, laserWidth-1, Game.HEIGHT);
		}else {
			g.setColor(Color.RED);
			g.fillRect((int)x + 5, (int)y, 2, Game.HEIGHT);
		}
	}

	/* This returns a rectangle that has the same dimensions as the render of this ship. used as the laser hit box
	 * pre: 
	 * post: a new Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle ((int)x, (int)y, laserWidth, Game.HEIGHT);
	}
}
