package GameClass;

public abstract class Ship extends GameObject{

	protected Handler handler;
	protected int timer;
	protected int health;
	
	public Ship(float x, float y, ObjectID id, Handler handler, int newHealth) {
		super(x, y, id);
		this.handler = handler;
		health = newHealth;
	}
	
	protected abstract void collisions();

}
