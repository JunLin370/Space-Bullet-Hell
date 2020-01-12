/* Jun Lin
 * DESC: gives a enumeration for these variables, so you can id specific objects
 * DATE: 2019-12-11 */

package GameClass;

//these are enum names for possible Objects in this game
public enum ObjectID {

	//Players
	Player1(),
	Player2(),
	//Player Weapons
	Gun1(),
	Gun2(),
	Gun3(),
	//Bullet Types
	Bullet1(),
	Bullet2(),
	Bullet3(),
	Bullet4(),
	Bullet5(),
	//Enemy Ship Types
	BasicEnemy(),
	ShotEnemy(),
	// Enemy Ship Types (BOSS)
	Boss1(),
	Boss2();
}
