/* This is an abstract class that extends gameObject, and contains many methods and variables that a ship may need (both the player and enemy)
 * 
 */

package abstrackSuperClasses;

import GameClass.Handler;
import GameClass.ObjectID;
import enemyShips.BombBullet;
import enemyShips.BombBullet2;
import enemyShips.Enemy;
import enemyShips.HomingBullet;

public abstract class Ship extends GameObject{

	protected Handler handler;
	protected int timer;
	protected int health;
	
	/* Desc: Constructor, will super the x,y and ID of the object to GameObject. Will instantiate a new instance of handler, and set the health of the ship in question
	 * Pre: float x, float y, ObjectID id, Handler handler, int newHealth > 1
	 * Post: instantiates new verison of handler, sets x, y, and ID. Sets the health of the object 
	 */
	public Ship(float x, float y, ObjectID id, Handler handler, int newHealth) {
		super(x, y, id);
		this.handler = handler;
		health = newHealth;
	}
	
	protected abstract void collisions(); // abstract method for collision dectection. used for ship objects to detect whether they have been hit or not

	/* Desc: This creates a new Enemy object with the appropriate/necessary variables such as ID, xy origin, trajectory, and speed  
	 * Pre: int x, int y, float 0 < angle < 360, int vel > 0
	 * Post: new enemyObject added to handler linked list
	 */
	protected void basicBullet(int x, int y, float angle, int vel) {
		handler.addObject(new Enemy(x, y, ObjectID.Bullet1, handler, angle, vel));
	}
	
	/* Desc: This creates a new HomingBullet object with the appropriate/necessary variables such as ID, xy origin, and speed
	 * Pre: int x, int y, int vel > 0
	 * Post: new HomingBullet added to handler linked list
	 */
	protected void trackBullet(int x, int y, int vel) {
		handler.addObject(new HomingBullet(x, y, ObjectID.Bullet2, handler, vel));
	}
	
	/* Desc: This creates a new BombBullet object with the appropriate/necessary variables such as ID, xy origin,trajectory, speed, and time till bomb explodes
	 * Pre: int x, int y, int vel > 0, int timer > 0
	 * Post: new BombBullet added to handler linked list
	 */
	protected void newBomb(int x, int y, int angle, int vel, int time) {
		handler.addObject(new BombBullet(x, y, ObjectID.Bullet3, handler, angle, vel, time));
	}
	
	/* Desc: This creates a new BombBullet2 object with the appropriate/necessary variables such as ID, xy origin, trajectory, speed, and time till bomb explodes
	 * Pre: int x, int y, int vel > 0
	 * Post: new HomingBullet added to handler linked list
	 */
	protected void newBombPlus(int x, int y, int angle, int vel, int time) {
		handler.addObject(new BombBullet2(x, y, ObjectID.Bullet4, handler, angle, vel, time));
	}
	
	/* Desc: sets the health of the ship, used to remove or added health to the ship
	 * Pre: int newHealth
	 * Post: health = newHealth
	 */
	public void setHealth(int newHealth) {
		health = newHealth;
	}
	
	/* Desc: sets the health of the ship, used to help with removing a certain amount of damage from ship and to check the statues of a ship
	 * Pre:
	 * Post: int health
	 */
	public int getHealth() {
		return health;
	}
}
