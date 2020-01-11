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
	
	public Enemy(float x, float y, ObjectID id, Handler handler, float angle, int vel) {
		super(x, y, id);
		this.handler = handler;
		velX = (float) (vel * Math.cos(Math.toRadians( angle )));
		velY = (float) (vel * Math.sin(Math.toRadians( angle )));
		damage = 10;
	}

	public void tick() {
		x += velX;
		y += velY;

		if (Game.inBorder(x, 0, Game.WIDTH)|| Game.inBorder(y,  Game.HEIGHT * (-1), Game.HEIGHT)) {	//and if the object is out of the screen, 
			handler.removeObject(this);	//remove it from the list
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval((int)x, (int)y, 10, 10);
		
		Graphics2D g2d = (Graphics2D) g;		
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,10,10);
	}
	
	
	
}
