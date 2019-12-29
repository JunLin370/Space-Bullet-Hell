/* This class is the Weapon Class used by the player to destroy Enemies, (place holder)
 * 
 */

package playerItems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import GameClass.Game;
import GameClass.GameObject;
import GameClass.Handler;
import GameClass.ObjectID;

public class Rifle extends GameObject{
	
	
	private Handler handler;
	private int vel;
	private boolean bang;
	private int timer;
	
	public Rifle(float x, float y, ObjectID id, Handler handler, float angle, float vel) {
		super(x, y, id);
		this.handler = handler;
		
		velX = (float) (vel * Math.cos(Math.toRadians( angle )));
		velY = (float) (vel * Math.sin(Math.toRadians( angle )));
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(Game.inBorder(x, 0, Game.WIDTH)|| Game.inBorder(y,  0, Game.HEIGHT ))  {
			handler.removeObject(this);
		}
	}


	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval((int)x, (int)y, 10, 30);
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
		return new Rectangle ((int)x,(int)y,10,30);
	}
}
