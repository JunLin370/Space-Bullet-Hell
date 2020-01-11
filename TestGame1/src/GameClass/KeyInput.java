package GameClass;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Object;

import abstrackSuperClasses.GameObject;
import playerItems.Player;

public class KeyInput extends Object implements KeyListener {
	
	private int vel = 8;
	private Handler handler;
	private boolean[] keys;
	private int fastSpeed;

	
	public KeyInput(Handler handler) {
		
		fastSpeed = 8;
		
		this.handler = handler;
		keys = new boolean[4];
		keys[0] = false;
		keys[1] = false;
		keys[2] = false;
		keys[3] = false;
	}
	
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

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
