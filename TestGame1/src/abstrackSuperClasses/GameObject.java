/* Jun Lin
 * DESC: This is the superclass which holds many properties that an object may have such as x y cords, 
 * x velocity and y velocity, and also the ID of the Object.
 * DATE: 2019-12-11 */ 

package abstrackSuperClasses;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JFrame;

import GameClass.ObjectID;

public abstract class GameObject extends JFrame {
	
	protected float x,y; // protected means this can only be used by this class and the class that inherit this class
	protected ObjectID id;	
	protected float velX, velY;
	
	/* Des: Constructor class will set starting x and y position. It will also set the id for the Object
	 * pre:
	 * post: set x, y, and object id */
	public GameObject(float x, float y, ObjectID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	//these are the accessor methods for this class to change the X,Y,and ObjectID, velX, velX
	//as well get X,Y, and ObjectID, velX, velX
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getXs() {
		return x;
	}
	
	public float getYs() {
		return y;
	}
	
	public void setID(ObjectID id) {
		this.id =id;
	}
	
	public ObjectID getId() {
		return id;
	}
	
	public void setVelX(float velX) {
		this.velX = velX;
	}
	
	public void setVelY(float velY) {
		this.velY = velY;
	}
	
	public float getVelX() {
		return velX;
	}
	
	public float getVelY() {
		return velY;
	}
	
}
