/* Jun Lin
 * DESC: This class is responsible for setting the size of the window, as well other necessary implementation
 * required for the window such as setting resize to false and visible to true. It also adds the game itself
 * to the window
 * DATE: 2019-12-11 */

package GameClass;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{
	
	public Window (int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);		//stop user from resizing program
		frame.setLocationRelativeTo(null);	//puts window in the center of the screen
		frame.add(game);	//adds the game to the window
		frame.setVisible(true);		
		game.start();
		
	}
}
