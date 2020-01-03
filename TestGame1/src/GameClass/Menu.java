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
		if (game.gameState == STATE.gameOver) {
			g.setColor(Color.WHITE);
			g.drawString("Back To Menu", 180, 600);
			g.setColor(Color.WHITE);
			g.drawRect(145, 550, 400, 64);

			g.setColor(Color.WHITE);
			g.drawString("Game Over", 200, 150);

			Font fnt2 = new Font("arial", 1, 30);
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
		}
	}
}
