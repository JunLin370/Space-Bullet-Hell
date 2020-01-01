package GameClass;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Object;

import abstrackSuperClasses.GameObject;

public class KeyInput extends Object implements KeyListener {
	
	private int vel = 5;
	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ObjectID.Player1) {	//if the temp object is equal to the Player1 object, then run below
				if (key == KeyEvent.VK_W) { tempObject.setVelY(-1 * vel);}
				if (key == KeyEvent.VK_A) tempObject.setVelX(-1 * vel);
				if (key == KeyEvent.VK_S) tempObject.setVelY(1 * vel);
				if (key == KeyEvent.VK_D) tempObject.setVelX(1 * vel);
			}
			if (tempObject.getId() == ObjectID.Player2) {	//if the temp object is equal to Pgvlayer2 object, then run below
				if (key == KeyEvent.VK_UP) tempObject.setVelY(-1 * vel);
				if (key == KeyEvent.VK_LEFT) tempObject.setVelX(-1 * vel);
				if (key == KeyEvent.VK_DOWN) tempObject.setVelY(1 * vel);
				if (key == KeyEvent.VK_RIGHT) tempObject.setVelX(1 * vel);
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ObjectID.Player1) {
				if (key == KeyEvent.VK_W) {tempObject.setVelY(0);}
				if (key == KeyEvent.VK_A) tempObject.setVelX(0);
				if (key == KeyEvent.VK_S) tempObject.setVelY(0);
				if (key == KeyEvent.VK_D) tempObject.setVelX(0);
			}
			if (tempObject.getId() == ObjectID.Player2) {
				if (key == KeyEvent.VK_UP) tempObject.setVelY(0);
				if (key == KeyEvent.VK_LEFT) tempObject.setVelX(0);
				if (key == KeyEvent.VK_DOWN) tempObject.setVelY(0);
				if (key == KeyEvent.VK_RIGHT) tempObject.setVelX(0);
			}
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
