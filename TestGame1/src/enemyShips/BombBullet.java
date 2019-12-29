package enemyShips;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import GameClass.Game;
import GameClass.GameObject;
import GameClass.Handler;
import GameClass.ObjectID;
import GameClass.Ship;

public class BombBullet extends GameObject{
	
	private final int bombWidth = 30;
	private Handler handler;
	private float toX, toY;
	private int timer, ticker;

	public BombBullet(float x, float y, ObjectID id, Handler handler, int angle, int vel, int timer) {
		super(x, y, id);
		this.handler = handler;
		velX = (float) (vel * Math.cos(Math.toRadians( angle )));
		velY = (float) (vel * Math.sin(Math.toRadians( angle )));
		this.timer = timer;
	}
	
	public void tick() {
		// Bomb AI
		x += velX;
		y += velY;
		ticker ++;
		if (ticker == timer* 60) {
			explode();
		}

		// Bomb Ai end
		collisions();
		if (Game.inBorder(x, 0, Game.WIDTH)|| Game.inBorder(y,  Game.HEIGHT * (-1), Game.HEIGHT - 100)) {	//and if the object is out of the screen, 
			handler.removeObject(this);	//remove it from the list
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval((int)x, (int)y, 10, 10);
		
		g.setColor(Color.YELLOW);
		g.drawOval((int)x, (int) y, bombWidth+5, bombWidth+5);
		
		Graphics2D g2d = (Graphics2D) g;		
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
		
	}
	
	private void explode() {
		int angle = 0;
		for (int i = 0; i <= 6; i++) {
			angle += 45;
			handler.addObject(new Enemy(x, y, ObjectID.Bullet1, handler, angle, 5));
		}
		handler.removeObject(this);
	}
	
	protected void collisions() {
		for (int i = 0; i < handler.object.size(); i++) {	//check all objects
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectID.Player1) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					Ship tempShip = (Ship) tempObject;
					explode();
					tempShip.setHealth(tempShip.getHealth() - 50);
					
					
				}
			}
		}//End for
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,bombWidth,bombWidth);
	}

}
