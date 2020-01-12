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
	
	//this class makes the window for the game
	//pre : int width, int height, String title, Game game
	//post: will make window to width and height size, and put title as the title. It will then put the game on the window
	public Window (int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));	//set preferred Size, Max and Min size to width and Height
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//exit on close
		frame.setResizable(false);		//stop user from resizing program
		frame.setLocationRelativeTo(null);	//puts window in the center of the screen	
		frame.add(game);	//adds the game to the window
		frame.setVisible(true);		
		game.start();
		
	}
}
