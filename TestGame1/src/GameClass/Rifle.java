/* This class is the Weapon Class used by the player to destroy Enemies, (place holder)
 * 
 */

package GameClass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Rifle extends GameObject{
	
	
	private Handler handler;
	private int vel;
	private boolean bang;
	private float angle;
	private int timer;
	
	public Rifle(float x, float y, ObjectID id) {
		super(x, y, id);
		this.handler = handler;
		
		velY = -20;
	}

	public void tick() {
		x += velX;
		y += velY;
	}


	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval((int)x, (int)y, 30, 10);
		if (timer <= 200) {
			g.drawString("Bang", (int)x, (int)y);
			bang = false;
			timer++;
		}

		Graphics2D g2d = (Graphics2D) g;		
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,30,10);
	}
}
