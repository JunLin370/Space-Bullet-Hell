package GameClass;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy2 extends GameObject{
	
private Handler handler;
private int oval = 7;
	
	public Enemy2(int x, int y, ObjectID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velY = 8;
	}

	public void tick() {
		x += velX;
		y += velY;
	}



	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(x, y, oval, oval);
		
		Graphics2D g2d = (Graphics2D) g;		
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
	}

	public Rectangle getBounds() {
		return new Rectangle (x,y,oval,oval);
	}
	
	
	
}

