package GameClass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy2 extends GameObject{
	
private Handler handler;
private int oval = 7;
	
	public Enemy2(float x, float y, ObjectID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velY = 8;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(Game.inBorder(x, 0, Game.WIDTH)|| Game.inBorder(y,  Game.HEIGHT * (-1), Game.HEIGHT - 100)) {	//and if the object is out of the screen, 
			handler.removeObject(this);	//remove it from the list
		}
	}



	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval((int)x, (int)y, oval, oval);
		
		Graphics2D g2d = (Graphics2D) g;		
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x,(int)y,oval,oval);
	}
	
	
	
}

