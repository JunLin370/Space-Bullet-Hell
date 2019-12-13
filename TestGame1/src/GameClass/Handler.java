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
	
	public void tick() {	//This runs tick on every object in the LinkedList
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
	}
	

	public void render(Graphics g) {	//This runs render on every object in the LinkedList
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
}
