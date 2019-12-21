/* Jun Lin
 * DESC: (WIP) This Class is the first object i am building. It is the labeled Player and does not work :(
 * DATE: 2019-12-12 */
package GameClass;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Player extends Ship  {

	private static final int OVALWIDTH = 20;
	private boolean firing;

	public Player(float x, float y, ObjectID id, Handler handler) {
		super(x, y, id,handler, 100);
		shooting = false;
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,OVALWIDTH,OVALWIDTH);
	}
	
	public void tick() {	//This updates the x and y coords of the object
		timer ++;
		if (health <= 0)
			handler.object.remove(this);
		
		if (timer >= 20) {
			handler.addObject(new Rifle(x,y,ObjectID.Gun1, handler));
			timer = 0;
		}
		
		x += velX;
		y += velY;
		
		x = Game.fborder((int)x, 0, Game.WIDTH - 27);
		y = Game.fborder((int)y, 0, Game.HEIGHT - 55);
		
		collisions();
	}

	public void render(Graphics g) {	//Makes the player Green and fills oval
		g.setColor(Color.GREEN);
		g.fillOval((int)x, (int)y, OVALWIDTH, OVALWIDTH);
		
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
		
		g.setColor(Color.RED);
		g.fillRect(10, 10, health*3, 15);
	}
	
	protected void collisions() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectID.Bullet1) {
				if(getBounds().intersects(tempObject.getBounds())){
					health -= 5;
					handler.removeObject(tempObject);
				}
			}
			
			if(tempObject.getId() == ObjectID.Bullet2) {
				if(getBounds().intersects(tempObject.getBounds())){
					health -= 10;
					handler.removeObject(tempObject);
				}
			}
			
			if(tempObject.getId() == ObjectID.BasicEnemy) {
				if(getBounds().intersects(tempObject.getBounds())){
					health -= 50;
					handler.removeObject(tempObject);
				}
			}
			
			if(tempObject.getId() == ObjectID.Bullet3) {
				if(getBounds().intersects(tempObject.getBounds())){
					health -= 20;
					handler.removeObject(tempObject);
				}
			}
		}//end for
	}
	
	public void getFire(boolean newFire) {
		this.firing = newFire;
	}
	
}