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
	
	public BlueLaser(float x, float y, ObjectID id, Handler handler, int damage, int laserWidth) {
		super(x, y, id);
		this.handler = handler;
		this.laserWidth = laserWidth;
		even = 15 - laserWidth/2;
		this.damage = damage;
	}

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

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;	
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());

		g.setColor(Color.CYAN);
		g.fillRect((int)x, 0, laserWidth, (int)y);
	}

	public Rectangle getBounds() {
		
		return new Rectangle ((int)x, 0, laserWidth, (int)y);

	}
}

