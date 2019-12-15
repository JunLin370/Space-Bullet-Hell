/* Jun Lin
 * DESC: This class is responsible for calling the tick class in every object (updating its information)
 * It then renders every single object and puts then on the screen.
 * This is also where the LinkedList is and that Linkedlist contains every single object in the game
 * DATE: 2019-12-11 */
package GameClass;


import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	/* Des: This runs the tick method inside every object in the linkedlist. It does this by going thought the
	 * linkedlist with a for loop. It then puts the linked list into a temp object and then runs the tick
	 * behavior inside it.
	 * pre:
	 * post: Runs tick inside every object in linkedlist (object) */
	public void tick() {	//This runs tick on every object in the LinkedList
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
	}
	
	/* Des: This behavior runs the render behavior inside every object in the linked list using a for loop
	 * It does this by putting the object inside a temp object variable. It then runs the render behavior
	 * inside that
	 * pre: Graphics g
	 * post: runs render inside every object in linkedlist (object) */
	public void render(Graphics g) {	//This runs render on every object in the LinkedList
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	/* Des: This adds an object to the linkedlist 
	 * pre: GameObject object
	 * post: object.add(object) */
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	/* Des: This removes an object from the linkedlist
	 * pre: GameObject object
	 * post: object.remove (object)*/
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
}
