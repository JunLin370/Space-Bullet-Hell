/* Jun Lin
 * DESC: This is the main class that contains the Main
 * This class is also responsible for starting and stopping necessary components of the program such as:
 * the thread, running, and handler.
 * DATE: 2019-12-11 */

package GameClass;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable{	
	
	public static final int  HEIGHT = 640, WIDTH = HEIGHT/12*9;	//WIDTH AND HEIGHT of window here
	private Thread thread;		
	private boolean running = false; 
	
	private Handler handler;
	
	public Game() {		// This starts the windows which is called from main

		handler = new Handler();
		
		this.addKeyListener(new KeyInput(handler));
		
		new Window(WIDTH, HEIGHT, "my Game", this);

		handler.addObject(new Player(HEIGHT/4, WIDTH/2, ObjectID.Player1));
		handler.addObject(new Player(HEIGHT/2, WIDTH/2, ObjectID.Player2));
		

	}
	
	public synchronized void start() {	// To start the game and tell program game is running
		thread = new Thread(this);
		thread.start();		//This calls the behavior run and starts the game loop
		running = true;
	}
	
	public synchronized void stop() {	// To stop the game and tell program game is not running
		try {
			thread.join();
			running = false;
		}catch(Exception e ) {
			e.printStackTrace();
		}
	}
	
	public void run() { // Popular game loop system, to stop flicking in Java and maintain tick rate, notch used it?
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
	
	private void tick() {
		handler.tick();	//this goes to the Handler Class
	}
	
	private void render() {		//Creates buffers in game to limit frames
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.RED);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);		// This goes to the HandlerClass
		
		g.dispose();
		bs.show();
	}
	
	public static void main (String [] args) {
		new Game();
	}

}
