/* Jun Lin
 * DESC: (WIP) This Class is the first object i am building. It is the labeled Player and does not work :(
 * DATE: 2019-12-12 */
package GameClass;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Player extends GameObject  {

	private static int OVALWIDTH = 20;

	public Player(int x, int y, ObjectID id) {
		super(x, y, id);
	}

	public void tick() {
		x += velX;
		y += velY;
	}

	public void render(Graphics g) {
		if (g.getColor() != Color.WHITE)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.BLUE);
		g.fillOval(x, y, OVALWIDTH, OVALWIDTH);
	}
}