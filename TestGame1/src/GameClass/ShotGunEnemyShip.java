package GameClass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ShotGunEnemyShip extends Ship{
	
	private int rectangleWidth;
	private int rectangleHeight;
	private int shots, j;

	public ShotGunEnemyShip(float x, float y, ObjectID id, Handler handler, int newHealth) {
		super(x, y, id, handler, 20);
		velY = 1;
		
		rectangleWidth = 40;
		rectangleHeight = 40;
	}

	protected void collisions() {
		for (int i = 0; i < handler.object.size(); i++) {	//check all objects
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectID.Gun1) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					health -= 5;		//if yes then remove a certain amount of health
					handler.removeObject(tempObject);	//and remove the bullet
				}
			}
		}//End for
	}

	public void tick() {
		if (health <= 0) {
			handler.removeObject(this);
			Level1.score += 200;
		}
		for (int i = 0; i < handler.object.size(); i++) {	//Gets the player object
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectID.Player1) {	//If player object exists
				////ACTION GOES HERE
				x += velX;
				y += velY;
				if (y == Game.HEIGHT/8) {
					timer ++;
					if (velY != 0)
						velY = 0;
					if (timer == 30 && shots < 8) {
						handler.addObject(new Enemy(x, y, ObjectID.Bullet1, handler, j, 1));
						handler.addObject(new Enemy(x, y, ObjectID.Bullet1, handler, -1*j, 1));
						shots ++;
						j++;
						timer = 0;
					}
				}

				////
			}
		}

	}

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int)x, (int)y, rectangleWidth, rectangleHeight);
		g.setColor(Color.RED);
		g.fillRect((int)x + rectangleWidth/4, (int)y + rectangleHeight/4, rectangleWidth/2, rectangleHeight/2);
		
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,rectangleWidth,rectangleHeight);
	}

}
