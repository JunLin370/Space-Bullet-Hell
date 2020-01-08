package GameClass;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import GameClass.Game.STATE;

public class Menu extends MouseAdapter {

	private Handler handler;
	private Game game;

	public Menu(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (game.gameState == STATE.Menu) {
			if(mouseOver(mx, my, 145, 250, 400, 64)) {
				game.gameState = STATE.levelSelect;
			}

			if(mouseOver(mx, my, 145, 400, 400, 64)) {
				game.gameState = STATE.shop;
			}

			if(mouseOver(mx, my, 145, 550, 400, 64)) {
				game.gameState = STATE.help;
			}

			if (mouseOver(mx, my,145, 700, 400, 64)) {
				System.exit(1);
			}
		}
		if (game.gameState == STATE.gameOver) {
			if (mouseOver(mx, my,145, 550, 400, 64)) {
				game.gameState = STATE.Menu;
			}
		}
		if (game.gameState == STATE.levelSelect) {
			if (mouseOver(mx, my,145, 400, 400, 64)) {
				game.gameState = STATE.Level1;
			}
			if (mouseOver(mx, my,145, 550, 400, 64)) {
				game.gameState = STATE.Menu;
			}
		}
		if (game.gameState == STATE.shop) {
			if (mouseOver(mx, my,145, 550, 400, 64)) {
				game.gameState = STATE.Menu;
			}
			if (mouseOver(mx, my,30, 200, 200, 64)) {
				game.gameState = STATE.powerSelect;
			}
		}
		if (game.gameState == STATE.powerSelect) {
			if (mouseOver(mx, my,10, 350, 100, 100)) {
				game.setWeaponLevel(1);
			}
			if (mouseOver(mx, my,150, 350, 100, 100)) {
				game.setWeaponLevel(2);
			}
			if (mouseOver(mx, my,290, 350, 100, 100)) {
				game.setWeaponLevel(3);
			}
			if (mouseOver(mx, my,430, 350, 100, 100)) {
				game.setWeaponLevel(4);
			}
			if (mouseOver(mx, my,570, 350, 100, 100)) {
				game.setWeaponLevel(5);
			}
			if (mouseOver(mx, my,145, 550, 400, 64)) {
				game.gameState = STATE.shop;
			}
		}
		if (game.gameState == STATE.gameWin) {
			if (mouseOver(mx, my,145, 550, 400, 64)) {
				game.gameState = STATE.Menu;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {}

	public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			}else return false;
		}else return false;
	}

	public void tick() {
	}

	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 50);
		Font fnt2 = new Font("arial", 1, 30);
		g.setFont(fnt);
		
		if (game.gameState == STATE.Menu) {
			g.setColor(Color.WHITE);
			g.drawString("Menu", 290, 150);	

			g.setColor(Color.WHITE);
			g.drawString("Start", 290, 300);
			g.setColor(Color.WHITE);
			g.drawRect(145, 250, 400, 64);

			g.setColor(Color.WHITE);
			g.drawString("Shop", 280, 450);
			g.setColor(Color.WHITE);
			g.drawRect(145, 400, 400, 64);

			g.setColor(Color.WHITE);
			g.drawString("Help", 290, 600);
			g.setColor(Color.WHITE);
			g.drawRect(145, 550, 400, 64);

			g.setColor(Color.WHITE);
			g.drawString("Quit", 290, 750);
			g.setColor(Color.WHITE);
			g.drawRect(145, 700, 400, 64);
		} 
		if (game.gameState == STATE.gameWin) {
			g.setColor(Color.WHITE);
			g.drawString("Back To Menu", 180, 600);
			g.setColor(Color.WHITE);
			g.drawRect(145, 550, 400, 64);

			g.setColor(Color.WHITE);
			g.drawString("You Win", 200, 150);
			
			g.setFont(fnt2);
			
			g.drawString("Your Final Score is " + Level1.score, 195, 300);
		}
		if (game.gameState == STATE.gameOver) {
			g.setColor(Color.WHITE);
			g.drawString("Back To Menu", 180, 600);
			g.setColor(Color.WHITE);
			g.drawRect(145, 550, 400, 64);

			g.setColor(Color.WHITE);
			g.drawString("Game Over", 200, 150);
			
			g.setFont(fnt2);
			
			g.drawString("Your Final Score is " + Level1.score, 195, 300);
		} 
		if (game.gameState == STATE.levelSelect) {
			g.setColor(Color.WHITE);
			g.drawString("Level Select", 210, 300);

			g.setColor(Color.WHITE);
			g.drawString("Level 1", 270, 450);
			g.setColor(Color.WHITE);
			g.drawRect(145, 400, 400, 64);
			
			g.setColor(Color.WHITE);
			g.drawString("Back", 290, 600);
			g.setColor(Color.WHITE);
			g.drawRect(145, 550, 400, 64);
		}
		if (game.gameState == STATE.shop) {
			g.setColor(Color.WHITE);
			g.drawString("Back", 290, 600);
			g.setColor(Color.WHITE);
			g.drawRect(145, 550, 400, 64);
			
			g.setColor(Color.WHITE);
			g.drawString("Armory", 260, 150);	
			
			g.setFont(fnt2);
			
			g.setColor(Color.WHITE);
			g.drawString("Machine Gun ", 37, 240);
			g.setColor(Color.WHITE);
			g.drawRect(30, 200, 200, 64);
		}
		if (game.gameState == STATE.powerSelect) {
			g.setFont(fnt);
			
			g.setColor(Color.WHITE);
			g.drawString("Armory", 260, 150);	
			
			g.setColor(Color.WHITE);
			g.drawString("Power Level", 200, 250);	
			
			if(game.getWeaponLevel() == 1) {
				g.setColor(Color.GREEN);
				g.fillRect(10, 350, 100, 100);
			}
			g.setColor(Color.WHITE);
			g.drawString(" 1 ", 33 , 420);
			g.setColor(Color.WHITE);
			g.drawRect(10, 350, 100, 100);
			
			if(game.getWeaponLevel() == 2) {
				g.setColor(Color.GREEN);
				g.fillRect(150, 350, 100, 100);
			}
			g.setColor(Color.WHITE);
			g.drawString(" 2 ", 173, 420);
			g.setColor(Color.WHITE);
			g.drawRect(150, 350, 100, 100);
			
			if(game.getWeaponLevel() == 3) {
				g.setColor(Color.GREEN);
				g.fillRect(290, 350, 100, 100);
			}
			g.setColor(Color.WHITE);
			g.drawString(" 3 ", 313, 420);
			g.setColor(Color.WHITE);
			g.drawRect(290, 350, 100, 100);
			
			if(game.getWeaponLevel() == 4) {
				g.setColor(Color.GREEN);
				g.fillRect(430, 350, 100, 100);
			}
			g.setColor(Color.WHITE);
			g.drawString(" 4 ", 453, 420);
			g.setColor(Color.WHITE);
			g.drawRect(430, 350, 100, 100);
			
			if(game.getWeaponLevel() == 5) {
				g.setColor(Color.GREEN);
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
