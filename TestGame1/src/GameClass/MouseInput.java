package GameClass;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Object;

public class MouseInput extends Object implements KeyListener {
	
	private int vel = 5;
	private Handler handler;
	
	public MouseInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ObjectID.Player1) {
				if (key == KeyEvent.VK_W) tempObject.setVelY(-1 * vel);
				if (key == KeyEvent.VK_A) tempObject.setVelX(-1 * vel);
				if (key == KeyEvent.VK_S) tempObject.setVelY(1 * vel);
				if (key == KeyEvent.VK_D) tempObject.setVelX(1 * vel);
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for (int i = 0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if (tempObject.getId() == ObjectID.Player1) {
				if (key == KeyEvent.VK_W) tempObject.setVelY(0);
				if (key == KeyEvent.VK_A) tempObject.setVelX(0);
				if (key == KeyEvent.VK_S) tempObject.setVelY(0);
				if (key == KeyEvent.VK_D) tempObject.setVelX(0);
			}
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
