package GameClass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BasicEnemyShip extends GameObject{

	private Handler handler;
	private int rectangleWidth = 20;
	private int rectangleHeight = 60;
	private int timer;
	private int health = 10;
	
	public BasicEnemyShip(float x, float y, ObjectID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velY = 1;
	}

	public void tick() {
		if (health <= 0)
			handler.removeObject(this);
		
		x += velX;
		y += velY;
		
		timer ++;
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectID.Player1) {
				if (timer == 200) {
					handler.addObject(new HomingBullet(x, y, ObjectID.Bullet3, handler));
					timer = 0;
				}
			}
		}//end for
		
		collision();
	}
	
	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectID.Gun1) {
				if(getBounds().intersects(tempObject.getBounds())){
					health -= 5;
					handler.removeObject(tempObject);
				}
			}
		}//End for
	}

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int)x, (int)y, rectangleWidth, rectangleHeight);
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,rectangleWidth,rectangleHeight);
	}
	
}
