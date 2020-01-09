package enemyShips;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import GameClass.Game;
import GameClass.Handler;
import GameClass.ObjectID;
import GameClass.Game.STATE;
import Levels.Level;
import abstrackSuperClasses.GameObject;
import abstrackSuperClasses.Ship;
import playerItems.BigRifleBullet;
import playerItems.BlueLaser;
import playerItems.RifleBullet;

public class BossGod extends Ship{
	
	private final int  RECTANGLEWIDTH = 100, RECTANGLEHEIGHT = 125;		//size of the boss
	private int attack, invincibilityFrames, dyingtimer;	// these 5 types of variables are used for the tracking and selecting of the attacks from the boss
	private boolean on, opener, phase2;	
	private Random r;	
	private Game game;

	/* constructor takes in the pre-requisite x and y starting location of the object, and object id, the
	 * handler, and health of the ship. It also initialize many variables used in the tick method.   
	 * pre: float x, float y, ObjectID (Ship1), handler, health of the ship
	 * post: supers all the pre variables. sets on and buffer to false. Make a new random object. set velY to 1
	 */
	public BossGod(float x, float y, ObjectID id, Handler handler, int newHealth, Game game) {
		super(x, y, id, handler, newHealth);
		this.game = game;
		
		on = false;		//on and buffer are variables used in the AI of this boss
		phase2 = false;
		opener = true;
		
		r = new Random();
		velY = 8;

	}
	
	/* This is the collision detection for this Boss1 class. It checks every object in the game and if its a gun object, remove health from this object. If it is the player
	 * destroy the player object, cause its touching a boss ffs
	 * pre:
	 * post: if touch bullet, remove health from boss. if touch player, kill player
	 */
	protected void collisions() {
		for (int i = 0; i < handler.object.size(); i++) {	//check all objects
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectID.Gun1) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					health -= RifleBullet.damage;		//if yes then remove a certain amount of health
					handler.removeObject(tempObject);	//and remove the bullet
				}
			}
			
			if(tempObject.getId() == ObjectID.Gun2) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					health -= BigRifleBullet.damage;		//if yes then remove a certain amount of health
					handler.removeObject(tempObject);	//and remove the bullet
				}
			}
			
			if(tempObject.getId() == ObjectID.Gun3) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					if (invincibilityFrames == 0) {
						health -= BlueLaser.damage;		//if yes then remove a certain amount of health
					}
					invincibilityFrames++;
					if (invincibilityFrames == 2) {
						invincibilityFrames = 0;
					}
				}
			}

			if(tempObject.getId() == ObjectID.Player1) {	//if object's id is Gun1
				if(getBounds().intersects(tempObject.getBounds())){		//check if their bounds touch
					Ship tempShip = (Ship) tempObject;
					tempShip.setHealth(0);
				}
			}
		}//End for
	}
	

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int)x, (int)y, RECTANGLEWIDTH, RECTANGLEHEIGHT);
		g.setColor(Color.WHITE);
		g.drawRect(89, Game.HEIGHT - 80, 500+1, 35);
		g.setColor(Color.WHITE);

		g.setColor(Color.RED);
		g.fillRect(90, Game.HEIGHT - 79, health/5, 34);

		Graphics2D g2d = (Graphics2D) g;		
		g.setColor(Color.WHITE);
		g2d.draw(getBounds());
	}


	/*
	 * 
	 */
	public void tick() {
		if (health <= 0) {
			if (phase2 == false) {
				phase2 = true;
				health = 500;
				attack = r.nextInt(2) + 1;
				Level.score += 500;
			} else if (on == true) {
				Level.score += 1000;
				on = false;
			} else if (dyingtimer == 60 * 6){
				game.gameState = STATE.gameWin;
				handler.removeObject(this);
			} else {
				dyingtimer++;
			}
		}
		for (int i = 0; i < handler.object.size(); i++) {	//Goes through every object in game
			GameObject tempObject = handler.object.get(i);	
			if(tempObject.getId() == ObjectID.Player1) {	//If player object exists
				////ACTION GOES HERE
				x+= velX;
				y += velY;
				timer ++;
				//Opening attack
				if (opener == true) {
					if (timer == 20) {
						attack = r.nextInt(3) + 1;
						switch (attack) {
						case 1:
							newBombPlus((int)x, (int)y, 90, 3, 2);
							break;
						case 2:
							newBombPlus((int)x, (int)y, 100, 3, 2);
							break;
						case 3:
							newBombPlus((int)x, (int)y, 80, 3, 2);
							break;
						}
						timer = 0;
					}
					if (y >= game.HEIGHT) {
						//Opening end
						x = r.nextInt(Game.WIDTH - 100);
						y = -1*Game.HEIGHT;
					}
				}
				////Action end
			}

			collisions();
		}
	}

		public Rectangle getBounds() {
			return new Rectangle ((int)x,(int)y,RECTANGLEWIDTH,RECTANGLEHEIGHT);
		}
}