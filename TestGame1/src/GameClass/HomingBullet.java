package GameClass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class HomingBullet extends GameObject{
	
	
	private Handler handler;
	private int vel;
	private boolean bang;
	private float angle;
	private int timer;
	private GameObject player;
	
	public HomingBullet(float x, float y, ObjectID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		bang = true;
		timer = 0;

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectID.Player1) {
				this.player = tempObject;
			}
		}

		float distance = (float) Math.sqrt((x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));

		velX = (float) ((-1.0/distance)*(x - player.getXs() - 20) * 10);
		velY = (float) ((-1.0/distance)*(y - player.getYs() - 20) * 10);

		//if (y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		//if (x <= 0 || x >= Game.WIDTH - 16) velX *= -1;


	}

	public void tick() {
		x += velX;
		y += velY;
	}


	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval((int)x, (int)y, 10, 10);
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
		return new Rectangle ((int)x,(int)y,10,10);
	}
}
