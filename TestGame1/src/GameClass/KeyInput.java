package GameClass;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Object;

import abstrackSuperClasses.GameObject;
import playerItems.Player;

public class KeyInput extends Object implements KeyListener {
	
	private Handler handler;
	private boolean[] keys;
	private int fastSpeed;

	/* Constructor, this initialize a boolean array used to keep track of player controls and make the controls mores smooth, also sets the speed the
	 * player will move at
	 * pre: Handler handler
	 * post: initialize boolean array and sets all of it to false. sets fastSpeed to 8
	 */
	public KeyInput(Handler handler) {
		
		fastSpeed = 8;
		
		this.handler = handler;
		keys = new boolean[4];
		keys[0] = false;
		keys[1] = false;
		keys[2] = false;
		keys[3] = false;
	}
	
	/* This listens for if a key is Pressed. If WASD is pressed, set the speed appropriately, and change that keys boolean slot to true
	 * Also listen for L, if L is pressed, setFire to true. Do this only if the player object exists
	 * pre: KeyEvent
	 * post: changes velY and velX for the player depending on which WASD keys pressed. also sets fire to true if L key is pressed. Sets keys[] to true depending on which key is pressed
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ObjectID.Player1) {	//if the temp object is equal to the Player1 object, then run below
				Player player = (Player) tempObject;
				
				
				if (key == KeyEvent.VK_W) {tempObject.setVelY(-fastSpeed); keys[0] = true;}
				if (key == KeyEvent.VK_S) {tempObject.setVelY(fastSpeed); keys[1] = true;}
				if (key == KeyEvent.VK_D) {tempObject.setVelX(fastSpeed); keys[2] = true;}
				if (key == KeyEvent.VK_A) {tempObject.setVelX(-fastSpeed); keys[3] = true;}
				if (key == KeyEvent.VK_L) player.setFire(true);
			}

		}
	}
	
	/*When a key is released, set the respective keys[] to false. If WS is false. velY = 0, if AD is false, velX = 0
	 * also if L is released, setFire(false)
	 * pre: KeyEvent
	 * post: sets keys[] to false depending on WASD keys released. setsFire(false) if L key is released. if If WS is false, velY = 0. if AD is false, velX = 0
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ObjectID.Player1) {
				Player player = (Player) tempObject;
				if (key == KeyEvent.VK_W) keys[0] = false;
				if (key == KeyEvent.VK_S) keys[1] = false;
				if (key == KeyEvent.VK_D) keys[2] = false;
				if (key == KeyEvent.VK_A) keys[3] = false;
				if (key == KeyEvent.VK_L) player.setFire(false);
				
				if (keys[0] == false && keys[1] == false) tempObject.setVelY(0);
				if (keys[2] == false && keys[3] == false) tempObject.setVelX(0);
			}
		}
	}

	public void keyTyped(KeyEvent arg0) {}

}
