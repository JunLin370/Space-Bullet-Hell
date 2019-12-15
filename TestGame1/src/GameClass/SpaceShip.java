package GameClass;

import java.awt.Graphics;
import java.awt.Rectangle;

public class SpaceShip extends GameObject{
	Handler handler;
	int boundWidth, boundHeight;
	
	public SpaceShip(int x, int y, ObjectID id, int newBoundWidth, int newBoundHeight) {
		super(x, y, id);
		this.handler = handler;
		boundWidth = newBoundWidth;
		boundHeight = newBoundHeight;
	}

	public void tick() {
	}

	public void render(Graphics g) {
	}

	public Rectangle getBounds() {
		return new Rectangle (x,y,boundWidth,boundHeight);
	}
	
}

