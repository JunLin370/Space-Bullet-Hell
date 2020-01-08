package playerItems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import abstrackSuperClasses.GameObject;

public class BigRifleBullet extends GameObject {
	
	private Handler handler;
	private int vel;
	private boolean bang;
	private int timer;
	public static final int damage = 15;
	
	public BigRifleBullet(float x, float y, ObjectID id, Handler handler, float angle, float vel) {
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
		g.fillOval((int)x, (int)y, 10, 15);
		if (timer <= 60) {
			g.drawString("Bang", (int)x, (int)y);
			bang = false;
			timer++;
		}

		Graphics2D g2d = (Graphics2D) g;		
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,10,15);
	}
}
