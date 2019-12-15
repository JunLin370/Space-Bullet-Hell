/* Jun Lin
 * DESC: This is a class used for the enemy for the game (WIP)
 * This does nothing but go downwards at velocity of 4. If it reaches the bottom of the window, remove
 * this object from handler
 * DATE: 2019-12-11 */

package GameClass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy extends GameObject{

	private Handler handler;
	
	public Enemy(int x, int y, ObjectID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velY = 4;
	}

	public void tick() {
		x += velX;
		y += velY;
		if (y > Game.HEIGHT + 1000) 	//(WIP) !!! :( ( DOESNT WORK AND I DONT KNOW WHY :(
			for (int i = 0; i < handler.object.size(); i++) {		//for number of objects in game
				GameObject tempObject = handler.object.get(i);

				if(tempObject.getId() == ObjectID.Enemy1) {		//if it is enemy1 which is the current object	
					//handler.removeObject(tempObject);
				}

			}
	}//end for


	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, 10, 10);
		
		Graphics2D g2d = (Graphics2D) g;		
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
	}

	public Rectangle getBounds() {
		return new Rectangle (x,y,10,10);
	}
	
	
	
}
