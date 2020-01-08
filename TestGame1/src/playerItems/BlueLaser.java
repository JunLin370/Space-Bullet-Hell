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
	private int timer;
	private float angle;
	public static final int damage = 5;
	
	public BlueLaser(float x, float y, ObjectID id, Handler handler, float angle) {
		super(x, y, id);
		this.handler = handler;
		laserWidth = 10;
		this.angle = angle;
	}

	public void tick() {
		timer++;
		if (timer == 7) {
			laserWidth -= 2;
			x --;
			timer = 0;
		}
		if (laserWidth <= 5) {
			handler.removeObject(this);
		}
	}


	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;	
		g2d.rotate(Math.toRadians( angle ), x, y);
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());

		g.setColor(Color.CYAN);
		g.fillRect((int)x, (int)y, laserWidth, Game.HEIGHT);

		g2d.rotate(-(Math.toRadians( angle )),x,y);
	}

	public Rectangle getBounds() {
		
		return new Rectangle ((int)x,(int)y,laserWidth,Game.HEIGHT);

	}
}

