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

import abstrackSuperClasses.GameObject;
import abstrackSuperClasses.Ship;


public class Game extends Canvas implements Runnable{	
	
	public static final int  HEIGHT = 920, WIDTH = HEIGHT/12*9;	//WIDTH AND HEIGHT of window here 
	private Thread thread;		
	private boolean running = false; 
	
	private Handler handler;
	private Level1 level1;
	private Menu menu;
	
	public enum STATE {
		Menu,
		Game,
		thing,
		help,
		gameOver,
	};
	
	public STATE gameState = STATE.Menu;
	
	
	/* Des: Constructor method. It initializes the handler class, the keyListener, the window, and all the
	 * objects in the game. This is also where most of the game will be put (WIP) 
	 * pre:
	 * post: Runs the game itself */
	public Game() {		// This starts the windows which is called from main

		handler = new Handler();	//This starts the handler class
		level1 = new Level1(handler, this);	
		menu = new Menu(this, handler);

		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);

		new Window(WIDTH, HEIGHT, "my Game", this);



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
	
	/* Des: (WIP) cause i dont really know what it does besides stop the run method by turing running to false
	 * pre:
	 * post: */
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
	 * math. It will run forever untill running is set to false. It will repeatedly run tick and render(see below)
	 * pre:
	 * post: */
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
	
	/* Des: This behavior will run tick from the handler class
	 * pre:	
	 * post: It runs tick from the handler class*/
	private void tick() {
		handler.tick();	//this goes to the Handler Class

		if (gameState == STATE.Game) { //if gameState is game, then
			level1.tick(); // run game

			for (int i = 0; i < handler.object.size(); i++) {	//check all objects
				GameObject tempObject = handler.object.get(i);
				if(tempObject.getId() == ObjectID.Player1) {	//if object's id is Gun1
					Ship tempShip = (Ship) tempObject;
					if (tempShip.getHealth() <= 0) {
						gameState = STATE.gameOver;
						clearEnemies();
						this.level1.reset();
					}
				}

			}//End for

		}
		else if (gameState == STATE.Menu || gameState == STATE.gameOver) {
			menu.tick();
		}
	}
	
	/* Des: This will uses bufferstrategy to limit the amount of frames the game will output. It will 
	 * then output the background for the window. It will then run render inside the handler class.
	 * (WIP)
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

		if (gameState == STATE.Game) { // If the state of the game is in Game State, render level1
			level1.render(g);
		}
		else if (gameState == STATE.Menu || gameState == STATE.gameOver) {
			menu.render(g);
		}
		g.dispose();
		bs.show();
	}
	
	/* Des: This checks if a value is between two values. If it is, return True, if its not, return false
	 * pre:	float pos, float min, float max
	 * post: returns the value of min or max depending on which the pos is equal or greater then*/
	public static float fborder(float pos, float min, float max) {
		if (pos >= max)
			return pos = max;
		else if (pos <= min)
			return pos = min;
		else
			return pos;
	}
	
	/* Des: This checks if a value is between two values. If it is, return True, if its not, return false
	 * pre:	float pos, float min, float max
	 * post: boolean determining whether its in whiten parameters or not*/
	public static boolean inBorder(float pos, float min, float max) {
		if (pos >= max)
			return true;
		else if (pos <= min)
			return true;
		else
			return false;
	}
	
	public static void main (String [] args) {
		new Game();	//Runs game :)
	}
	
	public void clearEnemies() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			handler.object.clear();
		}
	}

}
