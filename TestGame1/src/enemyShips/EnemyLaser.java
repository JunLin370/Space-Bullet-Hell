package enemyShips;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import abstrackSuperClasses.GameObject;

public class EnemyLaser extends GameObject {
	private Handler handler;
	private int laserWidth, relX, relY;
	private int timer, even;
	public static int damage;
	private boolean active;
	
	public EnemyLaser(float x, float y, ObjectID id, Handler handler, int damage, int laserWidth, int posX, int posY) {
		super(x, y, id);
		this.handler = handler;
		this.laserWidth = laserWidth;
		even = 15 - laserWidth/2;
		this.damage = damage;
		active = false;
		
		x = (int) x + posX;
		y = (int) y + posY;
		relX = posX;
		relY = posY;
	}

	public void tick() {
		timer++;
		
		for (int i = 0; i < handler.object.size(); i++) {	//Goes through every object in game
			GameObject tempObject = handler.object.get(i);	
			if(tempObject.getId() == ObjectID.Boss2) {	//If player object exists
				x = tempObject.getXs() + even + relX;
				y = tempObject.getYs() + relY;
			}
		}
		
		if (timer == 60 * 1 && active == false) {
			active = true;
			timer = 0;
		}
		
		if (active == true) {
			if (timer == 5) {
				laserWidth -= 2;
				even += 1;
				timer = 0;
			}
			if (laserWidth <= 0) {
				handler.removeObject(this);
			}
		}
	}


	public void render(Graphics g) {
		if (active == true) {
			Graphics2D g2d = (Graphics2D) g;	
			g.setColor(Color.WHITE);
			g2d.draw(getBounds());

			g.setColor(Color.RED);
			g.fillRect((int)x+1, (int)y+1, laserWidth-1, Game.HEIGHT);
		}else {
			g.setColor(Color.RED);
			g.fillRect((int)x + 5, (int)y, 2, Game.HEIGHT);
		}
	}

	public Rectangle getBounds() {
		
		return new Rectangle ((int)x, (int)y, laserWidth, Game.HEIGHT);

	}
}
