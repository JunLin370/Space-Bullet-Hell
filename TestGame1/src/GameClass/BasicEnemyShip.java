package GameClass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BasicEnemyShip extends GameObject{

	private Handler handler;
	private int rectangleWidth = 20;
	private int rectangleHeight = 60;
	private int timer;
	
	public BasicEnemyShip(float x, float y, ObjectID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velY = 1;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		timer ++;
		if (timer == 200) {
			handler.addObject(new HomingBullet(x, y, ObjectID.Bullet3, handler));
			timer = 0;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int)x, (int)y, rectangleWidth, rectangleHeight);
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,rectangleWidth,rectangleHeight);
	}
	
}
