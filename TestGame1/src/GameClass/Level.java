package GameClass;

import enemyShips.BasicEnemyShip;
import enemyShips.Boss1;
import enemyShips.ShotGunEnemyShip;

public abstract class Level {
	protected Handler handler;
	
	public Level(Handler hander) {
		this.handler = hander;
	}
	
	protected void formationXLine(int number, int placement) {
		for (int i = 0; i < number; i++) {
			handler.addObject(new BasicEnemyShip( (placement + (i*30) - 10), -1 * (Game.HEIGHT/4), ObjectID.BasicEnemy, handler, 10));
		}
	}
	
	protected void formationYLine(int number, int placement) {
		for (int i = 0; i < number; i++) {
			handler.addObject(new BasicEnemyShip( placement - 10, -1 * (Game.HEIGHT/4) - (i*40), ObjectID.BasicEnemy, handler, 10));
		}
	}
	
	protected void formationTri(int placement, int health) {
		basicEnemy(placement - 10, (-1)* ((Game.HEIGHT/4) + 100), health);
		basicEnemy(placement + 50  - 10, (-1)* ((Game.HEIGHT/4) + 100), health);
		basicEnemy(placement + 100  - 10, (-1)* ((Game.HEIGHT/4) + 100), health);
		basicEnemy(placement + 25  - 10, (-1)* ((Game.HEIGHT/4) + 50), health);
		basicEnemy(placement + 75  - 10, (-1)* ((Game.HEIGHT/4) + 50), health);
		basicEnemy(placement + 50  - 10, (-1)* ((Game.HEIGHT/4)), health);
	}
	
	protected void basicEnemy(int x, int health) {
		handler.addObject(new BasicEnemyShip(x,(-1)*(Game.HEIGHT/4), ObjectID.BasicEnemy, handler, health));
	}
	
	protected void shotGunEnemy(int x, int health) {
		handler.addObject(new ShotGunEnemyShip(x,(-1)*(Game.HEIGHT/4), ObjectID.BasicEnemy, handler, health));
	}
	
	protected void basicEnemy(int x, int y, int health) {
		handler.addObject(new BasicEnemyShip(x, y, ObjectID.BasicEnemy, handler, health));
	}
	
	protected void shotGunEnemy(int x, int y, int health) {
		handler.addObject(new ShotGunEnemyShip(x, y, ObjectID.BasicEnemy, handler, health));
	}
	
	protected void newBoss(int x, int y, int health) {
		handler.addObject(new Boss1(x, y, ObjectID.Boss1, handler, health));
	}
	
}
