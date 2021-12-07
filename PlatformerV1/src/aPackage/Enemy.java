package aPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
/**
 * 
 * @author Kiya.Zadeh22
 *
 */
public abstract class Enemy extends Hazard implements Killable, Show {

	public static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	protected Rectangle hitbox;
	protected Image sprite;
	public boolean killed;
	
	public Enemy(Rectangle hitbox, Image sprite, Level l) {
		// TODO Auto-generated constructor stub
		this.hitbox = hitbox;
		this.sprite = sprite;
		l.showableList.add(this);
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
	/**
	 * This method should contain the condition for this enemy to be killed.
	 */
	@Override
	public abstract void kill();
	/**
	 * This method will be called upon the Enemy dying. 
	 * It should contain any cleanup to be done
	 */
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
	
	@Override
	public void show(Graphics g) {
		
		if (!killed) {
			g.setColor(Color.BLACK);
			g.drawImage(sprite, getHitBox().x-ScrollThread.scrollFactor, 
				getHitBox().y-ScrollThread.scrollFactorVertical, getHitBox().width, 
					getHitBox().height, null);
		}
	}

}
