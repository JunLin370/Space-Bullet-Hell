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
	
	public HomingBullet(float x, float y, ObjectID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		bang = true;
		timer = 0;

		//float distance = (float) Math.sqrt((x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));

		//velX =  ((-1/distance)*(x - player.getXs() - 20) * 10);
		//velY =  ((-1/distance)*(y - player.getYs() - 20) * 10);

		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectID.Player1) {
				float deltaX = tempObject.getXs() - x;
				float deltaY = tempObject.getYs() - y;
				float angle = (float) Math.atan2( deltaY, deltaX );

				float vel = 5;

				velX = (float) (vel * Math.cos( angle ));
				velY = (float) (vel * Math.sin( angle ));
			}
		}
	}

	public void tick() {
		x += velX;
		y += velY;
	}


	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
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
