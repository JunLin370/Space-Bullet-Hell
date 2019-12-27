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

	public void basicBullet(int x, int y, float angle, int vel) {
		handler.addObject(new Enemy(x, y, ObjectID.Bullet1, handler, angle, vel));
	}
}
