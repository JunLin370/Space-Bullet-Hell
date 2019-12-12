/* Jun Lin
 * DESC: This is the superclass which holds many properties that an object may have such as x y cords, 
 * x velocity and y velocity, and also the ID of the Object.
 * DATE: 2019-12-11 */ 

package GameClass;

import java.awt.Graphics;

import javax.swing.JFrame;

public abstract class GameObject extends JFrame {
	
	protected int x,y; // protected means this can only be used by this class and the class that inherit this class
	protected ObjectID id;
	protected int velX, velY;
	
	public GameObject(int x, int y, ObjectID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public void setX(int x) {
		this.x = y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setID(ObjectID id) {
		this.id =id;
	}
	
	public ObjectID getId() {
		return id;
	}
	
	public void setVelX(int velX) {
		this.velX = velX;
	}
	
	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public int getVelX() {
		return velX;
	}
	
	public int getVelY() {
		return velY;
	}
	
}
