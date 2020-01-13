# ISU Game, AlphaLite
This game is a space shooter from the bullet hell or light em up genre. In this type of game, you would fight enemies that would constantly barrage the player with different bullets while the player tries to dodge and destroy the enemy ships. In my game, at the end of the level, 1 of 2 current bosses will spawn depending on which level the player is playing. The player will have access to two weapon types, each with 5 different power levels, or tiers. The two weapon types are the machine gun and the laser rifle. The machine gun is a more rapid weapon that shoots less accurately than the laser rifle which is more powerful and more accurate, but slow firing. The game has no difficulty setting so the difficulty will be determined by which power level the player uses. There are 3 levels, level 1 demos the two enemy types and the first boss in the game, level 2 demos the second boss, and level 3 is an endless mode where you fight a wave of enemies with increasing health, and at the end, fight one of the 2 bosses. This would go on until the player dies.

## Prerequisites

** Abstract Package 
* GameObject.java
* Ship.java
###enemyShips Package
* BasicEnemyShip.java
* BombBullet.java
* BombBullet2.java
* Boss1.java
* Boss2.java
* BossGod.java
* Enemy.java
* EnemyLaser.java
* HomingBullet.java
* ShotGunEnemyShip
###GameClass Package
* Game.java
* Handler.java
* KeyInput.java
* Menu.java
* MouseMotionAdapter.java
* ObjectID.java
* Window.java
### Levels Package
* Level.java
* Level1.java
* Level2.java
* Level3.java
### playerItems
* BigRifleBullet.java
* BlueLaser.java
* Player.java
* RifleBullet.java

Name of Bat file would go here

The game uses no images or audio files

## Controls

The Mouse and left click is used in the main menu for button clicking
WASD is used for player movement
The L button is used to fire the weapon

## Know Bugs

In endless mode, the bosses' health will escape the size of the health bar after a few waves.
The ammo meter may creep a few pixels over the white box for the ammo meter
(that's all the bugs I know of)

## Authors

Lin Jun

## Acknowledgments

* Sujoy - small technical assistance, inspiration, playtester
* Yusuf - small technical assistance, inspiration, playtester
* Harry - inspiration, (good) playtester
