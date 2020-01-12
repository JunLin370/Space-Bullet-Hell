package GameClass;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import GameClass.Game.STATE;
import Levels.Level;
import Levels.Level1;

public class Menu extends MouseAdapter {

	private Handler handler;
	private Game game;

	public Menu(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
	}

	/* This class listens for mousePressed. if mouse is pressed and the mouse x y is over a certain point, make something happen
	 * pre: MouseEvent
	 * post: gameState may change, weaponLevel and weaponType may change, may exit system
	 */
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (game.gameState == STATE.Menu) {		//if in menu
			if(mouseOver(mx, my, 145, 250, 400, 64)) {	//button for LevelSelect
				game.gameState = STATE.levelSelect;
			}

			if(mouseOver(mx, my, 145, 400, 400, 64)) {	//button for shop
				game.gameState = STATE.shop;
			}

			if(mouseOver(mx, my, 145, 550, 400, 64)) {	//button for help
				game.gameState = STATE.help;
			}

			if (mouseOver(mx, my,145, 700, 400, 64)) {	//button to close program
				System.exit(1);
			}
		}
		if (game.gameState == STATE.gameOver) {	//if in game over
			if (mouseOver(mx, my,145, 550, 400, 64)) {	//back button
				game.gameState = STATE.Menu;
			}
		}
		if (game.gameState == STATE.levelSelect) {	//if in level select
			if (mouseOver(mx, my,145, 400, 400, 64)) {	//button for level 1
				game.gameState = STATE.Level1;
			}
			if (mouseOver(mx, my,145, 525, 400, 64)) {	//button for level 2
				game.gameState = STATE.Level2;
			}
			if (mouseOver(mx, my,145, 650, 400, 64)) {	//button for endless mode
				game.gameState = STATE.Level3;
			}
			if (mouseOver(mx, my,145, 775, 400, 64)) {	//back button
				game.gameState = STATE.Menu;
			}
		}
		if (game.gameState == STATE.shop) {	//if in shop
			if (mouseOver(mx, my,145, 550, 400, 64)) {	//back button
				game.gameState = STATE.Menu;
			}
			if (mouseOver(mx, my,30, 200, 200, 64)) {	//MachineGun Button
				game.gameState = STATE.powerSelect;	//goes to powerselect menu
				game.setWeaponType(1);	//set weaponType to machine gun
			}
			if (mouseOver(mx, my,30, 300, 200, 64)) {	//laser Rifle button
				game.gameState = STATE.powerSelect;	//goes to powerselect menu
				game.setWeaponType(2);	//set weaponType to laser Rifle
			}
		}
		if (game.gameState == STATE.help) {	// if in help menu
			if (mouseOver(mx, my,145, 700, 400, 64)) {	//back button
				game.gameState = STATE.Menu;
			}
		}
		if (game.gameState == STATE.powerSelect) {	//if in powerSelect
			if (mouseOver(mx, my,10, 350, 100, 100)) {	//power 1 button
				game.setWeaponLevel(1);
			}
			if (mouseOver(mx, my,150, 350, 100, 100)) {	//power 2 button
				game.setWeaponLevel(2);
			}
			if (mouseOver(mx, my,290, 350, 100, 100)) {	//power 3 button
				game.setWeaponLevel(3);
			}
			if (mouseOver(mx, my,430, 350, 100, 100)) {	//power 4 button
				game.setWeaponLevel(4);
			}
			if (mouseOver(mx, my,570, 350, 100, 100)) {	//power 5 button
				game.setWeaponLevel(5);
			}
			if (mouseOver(mx, my,145, 550, 400, 64)) {	//back button
				game.gameState = STATE.shop;
			}
		}
		if (game.gameState == STATE.gameWin) {	// if in gameWin
			if (mouseOver(mx, my,145, 550, 400, 64)) {	//back button
				game.gameState = STATE.Menu;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {}

	/* This method returns a boolean if the mx and my is within the given parameter
	 * pre: int mx, int my, int y, int width, int height
	 * post: boolean
	 */
	public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			}else return false;
		}else return false;
	}

	public void tick() {}

	/*This renders everything in the menu depending on what state the game is currently in
	 * pre: Graphics
	 * post: output buttons and texts depending on state of game
	 */
	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 50);	//font type 1
		Font fnt2 = new Font("arial", 1, 30);	//font type 2
		g.setFont(fnt);	//default set font to 1
		
		if (game.gameState == STATE.Menu) {	//if in menu
			g.setColor(Color.WHITE);	//is title
			g.drawString("AlphLite", 250, 150);	

			g.setColor(Color.WHITE);	//is start button
			g.drawString("Start", 290, 300);
			g.setColor(Color.WHITE);
			g.drawRect(145, 250, 400, 64);

			g.setColor(Color.WHITE);	//is Armory button
			g.drawString("Armory", 260, 450);
			g.setColor(Color.WHITE);
			g.drawRect(145, 400, 400, 64);

			g.setColor(Color.WHITE);	//is help button
			g.drawString("Help", 290, 600);
			g.setColor(Color.WHITE);
			g.drawRect(145, 550, 400, 64);

			g.setColor(Color.WHITE);	//is quit button
			g.drawString("Quit", 290, 750);
			g.setColor(Color.WHITE);
			g.drawRect(145, 700, 400, 64);
		} 
		if (game.gameState == STATE.gameWin) {	//if in win screen
			g.setColor(Color.WHITE);	//is back button
			g.drawString("Back To Menu", 180, 600);
			g.setColor(Color.WHITE);
			g.drawRect(145, 550, 400, 64);

			g.setColor(Color.WHITE);	//is "You Win"
			g.drawString("You Win", 200, 150);
			
			g.setFont(fnt2);	//set font to smaller font
			
			g.drawString("Your Final Score is " + Level1.score, 195, 300);	//is you final score
			
			g.setFont(fnt);	//reset font
		}
		if (game.gameState == STATE.gameOver) {	// if in gameOver screen
			g.setColor(Color.WHITE);	//back button
			g.drawString("Back To Menu", 180, 600);
			g.setColor(Color.WHITE);
			g.drawRect(145, 550, 400, 64);

			g.setColor(Color.WHITE);	//is "Game Over"
			g.drawString("Game Over", 200, 150);
			
			g.setFont(fnt2);	//set small font
			
			g.drawString("Your Final Score is " + Level.score, 195, 300);	//is you final score
			
			g.setFont(fnt);
		} 
		if (game.gameState == STATE.levelSelect) {	//if you are in level select
			g.setColor(Color.WHITE);	//is title
			g.drawString("Level Select", 210, 300);

			g.setColor(Color.WHITE);	//is level 1 button
			g.drawString("Level 1", 270, 450);
			g.setColor(Color.WHITE);
			g.drawRect(145, 400, 400, 64);
			
			g.setColor(Color.WHITE);	//is level 2 button
			g.drawString("Level 2", 270, 575);
			g.setColor(Color.WHITE);
			g.drawRect(145, 525, 400, 64);
			
			g.setColor(Color.WHITE);	//is level 3 button
			g.drawString("Endless Mode", 183, 700);
			g.setColor(Color.WHITE);
			g.drawRect(145, 650, 400, 64);
			
			g.setColor(Color.WHITE);	//is back button
			g.drawString("Back", 290, 825);
			g.setColor(Color.WHITE);
			g.drawRect(145, 775, 400, 64);
		}
		if (game.gameState == STATE.shop) {	//if in shop
			g.setColor(Color.WHITE);	//is back button
			g.drawString("Back", 290, 600);
			g.setColor(Color.WHITE);
			g.drawRect(145, 550, 400, 64);

			g.setColor(Color.WHITE);	//is title
			g.drawString("Armory", 260, 150);	

			g.setFont(fnt2);	//set font to small

			if(game.getWeaponType() == 1) {	//if weapon one selected
				g.setColor(Color.GRAY);	//draw gray box behind machinegun button
				g.fillRect(30, 200, 200, 64);
			}
			g.setColor(Color.WHITE);	//is machine gun button
			g.drawString("Machine Gun ", 37, 240);
			g.setColor(Color.WHITE);
			g.drawRect(30, 200, 200, 64);
			
			if(game.getWeaponType() == 2) {	//if weapon two selected
				g.setColor(Color.GRAY);	//draw gray box behind lasergun
				g.fillRect(30, 300, 200, 64);
			}
			g.setColor(Color.WHITE);	//is laser gun button
			g.drawString("Laser Rifle", 37, 340);
			g.setColor(Color.WHITE);
			g.drawRect(30, 300, 200, 64);
			g.setFont(fnt);		//reset font
		}
		if (game.gameState == STATE.help) {	//if in help menu
			g.setColor(Color.WHITE);	//is title
			g.drawString("Help", 300, 150);	
			
			g.setColor(Color.WHITE);	//is back button
			g.drawString("Back", 290, 750);
			g.setColor(Color.WHITE);
			g.drawRect(145, 700, 400, 64);
			
			g.setFont(fnt2);	//set smaller font
			g.setColor(Color.WHITE);	//is instructions
			g.drawString("USE WASD KEY TO MOVE USE", 115, 350);
			g.setColor(Color.WHITE);	
			g.drawString("USE L KEY TO FIRE YOUR WEAPON ", 80, 450);
			g.setColor(Color.WHITE);
			g.drawString("DODGE THE ENEMY AND THEIR BULLETS", 40, 550);
			g.setFont(fnt);	//reset font
			
		}
		if (game.gameState == STATE.powerSelect) {	//if in powerSelect
			g.setFont(fnt);

			g.setColor(Color.WHITE);	//is title
			g.drawString("Armory", 260, 150);	

			g.setColor(Color.WHITE);	//is sub title
			g.drawString("Power Level", 200, 250);	

			if(game.getWeaponLevel() == 1) {	//if weaponLevel is 1
				
				g.setFont(fnt2);	//out put text in small font and then reset font
				g.setColor(Color.WHITE);
				g.drawString("Extra hard", 270, 300);	
				g.setFont(fnt);
				
				g.setColor(Color.GRAY);	//and but background on button
				g.fillRect(10, 350, 100, 100);
			}
			g.setColor(Color.WHITE);
			g.drawString(" 1 ", 33 , 420);
			g.setColor(Color.WHITE);
			g.drawRect(10, 350, 100, 100);

			if(game.getWeaponLevel() == 2) {	//repeat for all the powerlevels but with different text and color backgrounds
				
				g.setFont(fnt2);
				g.setColor(Color.WHITE);
				g.drawString("Hard", 305, 300);	
				g.setFont(fnt);
				
				g.setColor(Color.CYAN);
				g.fillRect(150, 350, 100, 100);
			}
			g.setColor(Color.WHITE);
			g.drawString(" 2 ", 173, 420);
			g.setColor(Color.WHITE);
			g.drawRect(150, 350, 100, 100);

			if(game.getWeaponLevel() == 3) {
				
				g.setFont(fnt2);
				g.setColor(Color.WHITE);
				g.drawString("Normal", 290, 300);	
				g.setFont(fnt);
				
				g.setColor(Color.MAGENTA);
				g.fillRect(290, 350, 100, 100);
			}
			g.setColor(Color.WHITE);
			g.drawString(" 3 ", 313, 420);
			g.setColor(Color.WHITE);
			g.drawRect(290, 350, 100, 100);
			
			if(game.getWeaponLevel() == 4) {
				
				g.setFont(fnt2);
				g.setColor(Color.WHITE);
				g.drawString("Easy", 305, 300);	
				g.setFont(fnt);
				
				g.setColor(Color.ORANGE);
				g.fillRect(430, 350, 100, 100);
			}
			g.setColor(Color.WHITE);
			g.drawString(" 4 ", 453, 420);
			g.setColor(Color.WHITE);
			g.drawRect(430, 350, 100, 100);
			
			if(game.getWeaponLevel() == 5) {
				
				g.setFont(fnt2);
				g.setColor(Color.WHITE);
				g.drawString("Baby Mode", 265, 300);	
				g.setFont(fnt);
				
				g.setColor(Color.RED);
				g.fillRect(570, 350, 100, 100);
			}
			g.setColor(Color.WHITE);
			g.drawString(" 5 ", 593, 420);
			g.setColor(Color.WHITE);
			g.drawRect(570, 350, 100, 100);
			
			g.setColor(Color.WHITE);
			g.drawString("Back", 290, 600);
			g.setColor(Color.WHITE);
			g.drawRect(145, 550, 400, 64);
		}
	}
}
