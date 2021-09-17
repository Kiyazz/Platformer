package aPackage;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
/**
 * 
 * @author Kiya.Zadeh22
 *
 */
public abstract class Enemy extends Hazard implements Killable {

	public static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	protected Rectangle hitbox;
	protected Image sprite;
	public boolean killed;
	
	public Enemy(Rectangle hitbox, Image sprite, Level l) {
		// TODO Auto-generated constructor stub
		this.hitbox = hitbox;
		this.sprite = sprite;
		l.enemyList.add(this);
		
	}
	/**
	 * Gets the hitbox of this enemy
	 * @return A Rectangle object representing the hitbox
	 */
	public Rectangle getHitBox () {
		return hitbox;
	}
	/**
	 * This method is used to set how this enemy moves
	 */
	public abstract void move();
	
	@Override
	public abstract void kill();

	@Override
	public abstract void cleanup();

	@Override
	public abstract void killCondition();
	
	public static void updateAllEnemies (Level l) {
		for (int i = 0; i < l.enemyList.size(); i++) {
			if (!l.enemyList.get(i).killed) {
				l.enemyList.get(i).move();
				l.enemyList.get(i).killCondition();
				l.enemyList.get(i).kill();
				
			}
		}
	}

}
