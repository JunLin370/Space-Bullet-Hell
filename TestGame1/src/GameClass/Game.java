 /* Jun Lin
 * DESC: This is the main class that contains the Main
 * This class is also responsible for starting and stopping necessary components of the program such as:
 * the thread, running(the main loop of the game), and handler, This place will also contain the main menu.*/

package GameClass;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import Levels.Level;
import Levels.Level1;
import Levels.Level2;
import Levels.Level3;
import abstrackSuperClasses.GameObject;
import abstrackSuperClasses.Ship;
import playerItems.Player;

public class Game extends Canvas implements Runnable{	
	
	public static final int  HEIGHT = 920, WIDTH = HEIGHT/12*9;	//WIDTH AND HEIGHT of window here 
	private Thread thread;		
	private boolean running = false; 	
	private Handler handler; 	//initialize handler class that will control all objects in game
	private Level level1, level2, level3;	//initialize level classes in the game
	private Menu menu;	//initialize menu, which will render all screens outside of the levels
	
	private int weaponLevel; //This is the variable that remembers how powerful the weapon will be when the player enters the game
	private int weaponType;	//this is the variable that remebers which weapon the player will use when he enters a game
	
	/* this is the different States a menu can be in called STATE
	 * it contains States for the menu, the levels, the shop, the powerSelect screen, the help Screen, gameover screen and win screen, and level select screen
	 */
	public enum STATE {
		Menu,
		Level1,
		Level2,
		Level3,
		shop,
		powerSelect,
		help,
		gameOver,
		gameWin,
		levelSelect,
	};
	
	//instantiate a new instance of STATE to be used in the menu and in game
	public STATE gameState = STATE.Menu;
	
	
	/* Des: Constructor method. It initializes the handler class, the keyListener, the window, and all the
	 * objects in the game. This is also where the levels are initialize. the weapon level and type are set to 1 by default. 
	 * menu is initialize, adding mouseListener to the menu, and keyListener to handler 
	 * pre:
	 * post: Runs the game itself */
	public Game() {		// This starts the windows which is called from main

		handler = new Handler();	//This starts the handler class
		level1 = new Level1(handler, this);	
		level2 = new Level2(handler, this);
		level3 = new Level3(handler, this);
		weaponLevel = 1;
		weaponType = 1;
		menu = new Menu(this, handler);
		
		
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);

		new Window(WIDTH, HEIGHT, "AlphaLite", this);

	}

	/* Des: This is synchronized meaning only one thread can be inside at a time. It makes a new thread and
	 * starts it. It runs the "run" behavior. It also sets running to true
	 * pre: 
	 * post: This runs the "run" behavior and sets running to true */
	public synchronized void start() {	// To start the game and tell program game is running
		if (running)	//if the game is already running, just return out of method
			return;
		
		thread = new Thread(this);
		thread.start();		//This calls the behavior run and starts the game loop
		running = true;
	}
	
	/* Des: This is to stop the thread when you want to close the game.
	 * pre:
	 * post: joins thread and then set running to false */
	public synchronized void stop() {	// To stop the game and tell program game is not running
		if (!running)	//if the game is not running, leave method cause there is nothing to stop
			return;
		
		try {
			thread.join();
			running = false;
		}catch(Exception e ) {
			e.printStackTrace();
		}
	}
	
	/* Des: This behaviour is holds the loop which the game will be placed in. It limits the amount of frames using
	 * math. It will run forever until running is set to false. It will repeatedly run tick and render(see below)
	 * pre:
	 * post: while loops the tick and render. run tick 60 times a second */
	public void run() { // Popular game loop system, to stop flicking in Java and maintain tick rate
		this.requestFocus();	//Auto Focus the game when launched
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {			// THIS IS THE MAIN LOOP OF THE GAME
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();		//Runs tick below
				delta--;
			}
			if (running)
				render();	//Runs render below
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	/* Des: This behavior will run tick from the handler class. Depending on the state, it will run different tick methods
	 * pre:	
	 * post: It runs tick from the handler class*/
	private void tick() {
		handler.tick();	//this goes to the Handler Class
		//if the games state is any of the states below, run the menu
		if (gameState == STATE.Menu || gameState == STATE.gameOver || gameState == STATE.help|| gameState == STATE.levelSelect || gameState == STATE.shop || gameState == STATE.powerSelect || gameState == STATE.gameWin) {	//if the game is in theses states,
			menu.tick();	//run menu	
			clearEnemies();
			this.level1.reset();	//these will reset the timer and adder in the levels so that the player can play them again
			this.level2.reset();
			this.level3.reset();
		}
		//if the game state is one of the levels, run that respective level
		if (gameState == STATE.Level1 || gameState == STATE.Level2 || gameState == STATE.Level3) {
			if (gameState == STATE.Level1)
				level1.tick(); // run game
			if (gameState == STATE.Level2)
				level2.tick();
			if (gameState == STATE.Level3)
				level3.tick();
			//resets level if player losses
			for (int i = 0; i < handler.object.size(); i++) {	//check all objects
				GameObject tempObject = handler.object.get(i);
				if(tempObject.getId() == ObjectID.Player1) {	//if object's id is Player1
					Ship tempShip = (Ship) tempObject;
					if (tempShip.getHealth() <= 0) {	//if player health = 0
						gameState = STATE.gameOver;		//set gamestate to gameOver
					}
				}
			}//End for
		}
	}

	/* Des: This will uses bufferstrategy to help fix the flickering problom at the cost of some fps, though that doesn't really matter.
	 * It does this by creating another frame after the first one has been rendered, and then displays it when the first one has been show. It then disposes
	 * the frame when its done
	 * pre:
	 * post: disposes frames with bufferStrategy and g.dispose. Will show frames with bs.show */
	private void render() {		//Creates buffers in game to limit frames the game will run
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);		// This goes to the HandlerClass

		//if state is one of the levels, output run the respective levels render
		if (gameState == STATE.Level1 || gameState == STATE.Level2 || gameState == STATE.Level3) {
			if (gameState == STATE.Level1)
				level1.render(g); // run game
			if (gameState == STATE.Level2)
				level2.render(g);
			if (gameState == STATE.Level3)
				level3.render(g);
		}
		//else render menu if one of states below
		else if (gameState == STATE.Menu || gameState == STATE.gameOver || gameState == STATE.help|| gameState == STATE.levelSelect || gameState == STATE.shop || gameState == STATE.powerSelect || gameState == STATE.gameWin) {
			menu.render(g);
		}
		g.dispose();
		bs.show();
	}
	
	/* Des: This checks if a value is between two values. If it is, return True, if its not, return false
	 * pre:	float pos, float min, float max
	 * post: returns the value of min or max depending on which the pos is equal or greater then*/
	public static float fborder(float pos, float min, float max) {
		if (pos >= max)	//if the position is greater than or equal maximum
			return pos = max;	//returns the max number
		else if (pos <= min)	//if the position is less than or equal minimum
			return pos = min;	//return minimum
		else
			return pos;		//else return the position
	}
	
	/* Des: This checks if a value is between two values. If it is, return True, if its not, return false
	 * pre:	float pos, float min, float max
	 * post: boolean determining whether its in whiten parameters or not*/
	public static boolean inBorder(float pos, float min, float max) {
		if (pos >= max)			//if the position is greater than or equal maximum
			return true;	//return true
		else if (pos <= min)	//if the position is less than or equal minimum
			return true;	//return true
		else
			return false;	//else return false 
	}
	
	/* This method clears every object in the handler class
	 * pre:
	 * post: removes every object from the handler linkedlist
	 */
	public void clearEnemies() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			handler.object.clear();
		}
	}
	
	//------------------------ Getter Setters ------------------------//
	/* returns weapon type
	 * pre:
	 * post:int
	 */
	public int getWeaponType() {
		return weaponType;
	}
	
	/* sets the weaponType variable, have to be valid weapon type int
	 * pre:	int
	 * post: weaponType = int
	 */
	public void setWeaponType(int newWeaponType) {
		weaponType = newWeaponType;
	}
	
	/* returns the weapon level
	 * pre:
	 * post: int
	 */
	public int getWeaponLevel() {
		return weaponLevel;
	}
	
	/* sets the weaponLevel, has the be a valid weapon level int
	 * pre: int
	 * post: weaponLevel = int
	 */
	public void setWeaponLevel(int newWeaponLevel) {
		weaponLevel = newWeaponLevel;
	}
	
	//------------------------ MAIN ------------------------//
	//This is main
	public static void main (String [] args) {
		new Game();	//Runs game :)
	}

}
