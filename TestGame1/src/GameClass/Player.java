package GameClass;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Player extends GameObject implements KeyListener, ActionListener {

	private int OVALWIDTH = 20;

	public Player(int x, int y, ObjectID id) {
		super(x, y, id);
	}

	public void tick() {
		if(x < 0) {
			x = (Game.WIDTH-OVALWIDTH);
		}
		else if(x > Game.WIDTH-OVALWIDTH) {
			x = 1;
		}
		else {
			x += velX;
		}
		y += velY;
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, OVALWIDTH, OVALWIDTH);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == 'd' || e.getKeyChar() == 'a') {
			velX = 0;
		}
		if(e.getKeyChar() == 'w' || e.getKeyChar() == 's') {
			velY = 0;
		}
	}

	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == 'd') {
			velX = 1;
		}
		else if(e.getKeyChar() == 'a') {
			velX = -1;
		}
		else if(e.getKeyChar() == 'w') {
			velY = -1;
		}
		else if(e.getKeyChar() == 's') {
			velY = 1;
		}
		
	}
}
