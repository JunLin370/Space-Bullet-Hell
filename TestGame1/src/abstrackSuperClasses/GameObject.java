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
	
	protected float x,y; // this is the starting x y location of the object in question
	protected ObjectID id;	// this is the enum of the object in question
	protected float velX, velY;	// this is the speed at which this object will move, if it were to move
	
	/* Des: Constructor class will set starting x and y position. It will also set the id for the Object
	 * pre: float x, float y, ObjectID id
	 * post: set x, y, and object id */
	public GameObject(float x, float y, ObjectID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick();	//Abstract Tick method, used by all extended objects to update its current statues in the program
	public abstract void render(Graphics g);	//abstract render method, used by all extended object to update its current visuals in the program
	public abstract Rectangle getBounds();	//abstract getBounds method, used to return a rectangle of an object, to primarily be used for hit boxing/ collision
	
	/* Desc: This sets the x for this object
	 * Pre: float x
	 * Post: sets the x to the new x
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/* Desc: This sets the y location for the object
	 * Pre: float y
	 * Post: sets the y to the new y
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/* Desc: returns the x position of this object
	 * Pre:
	 * Post: float x
	 */
	public float getXs() {
		return x;
	}
	
	/* Desc: returns the y postion of this object
	 * Pre:
	 * Post: float y
	 */
	public float getYs() {
		return y;
	}
	
	/* Desc: Sets the ObjectID of this object
	 * Pre: ObjectID id
	 * Post: id = new ID
	 */
	public void setID(ObjectID id) {
		this.id =id;
	}
	
	/* Desc: Returns the id of this object
	 * Pre: 
	 * Post: ObjectID id
	 */
	public ObjectID getId() {
		return id;
	}
	
	/* Desc: sets velX to a new velX
	 * Pre: float velX
	 * Post: velX = new velX
	 */
	public void setVelX(float velX) {
		this.velX = velX;
	}
	
	/* Desc: changes velY to a new velY
	 * Pre: float velY
	 * Post: velY = new velY
	 */
	public void setVelY(float velY) {
		this.velY = velY;
	}
	
	/* Desc: returns velX of this object
	 * Pre: 
	 * Post: float velX
	 */
	public float getVelX() {
		return velX;
	}
	
	/* Desc: returns velY of this object
	 * Pre:
	 * Post: float velY
	 */
	public float getVelY() {
		return velY;
	}
	
}
