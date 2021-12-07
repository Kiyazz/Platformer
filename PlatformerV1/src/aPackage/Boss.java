package aPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;





/**
 * Class representing a boss enemy. 
 * Bosses usually represent tougher enemies meant to serve as a challenge to the player.
 * <p>
 * 
 * @author Kiya.Zadeh22
 *
 */
public class Boss extends Enemy {
	
	public int health;
	public int maxHealth;
	public double healthPercent = 100;
	
	public boolean enabled = false;
	
	private long time = System.currentTimeMillis();
	
	private int speed;
	
	public final int startX, startY;
	/**
	 * Creates a new Boss object
	 * @param hitbox The boss's hitbox where it interacts with the player
	 * @param sprite An Image for the boss's sprite
	 * @param health How many jumps from the player it takes to kill the boss
	 * @param speed How fast the boss moves
	 * @param l The level the boss is in
	 */
	public Boss(Rectangle hitbox, Image sprite, int health, int speed, Level l) {
		super(hitbox, sprite, l);
		this.health = health;
		this.maxHealth = health;
		this.speed = speed;
		this.startX = hitbox.x;
		this.startY = hitbox.y;
	}
	
	@Override
	public void move() {
		
		if (!enabled) enabled = enableCheck();
		if (enabled && !Driver.isCutsceneActive() && Driver.isGrounded) {
			if (this.hitbox.x > Driver.xpos) {
				this.hitbox.translate(-speed, 0);
			}
			else this.hitbox.translate(speed, 0);
		}
		else if (enabled && !Driver.isCutsceneActive()) {
			if (this.hitbox.x < Driver.xpos) {
				this.hitbox.translate(speed, 0);
			}
			else {
				this.hitbox.translate(-speed, 0);
			}
		}
		
	}

	@Override
	public void kill() {
		if (this.hitbox.intersects(Driver.getPlayerhitBox()) && 
				Driver.getPlayerhitBox().x+Constants.PLAYER_WIDTH > this.hitbox.x && 
				Driver.getPlayerhitBox().x < this.hitbox.x + this.hitbox.width &&
				GravityThread.playerDownSpeed > 0 && 
				Math.abs(time - System.currentTimeMillis()) > 3000) {
			health--;
			GravityThread.jump();
			time = System.currentTimeMillis();
		}
		if (health == 0) {
			this.killed = true;
			this.cleanup();
		}
	}
	/**
	 * Disables the boss after it is killed
	 */
	@Override
	public void cleanup() {
		enabled = false;
	}

	@Override
	public void killCondition() {
		if (this.hitbox.intersects(Driver.getPlayerhitBox()) && 
				Driver.getPlayerhitBox().x+Constants.PLAYER_WIDTH > this.hitbox.x && 
				Driver.getPlayerhitBox().x < this.hitbox.x + this.hitbox.width &&
				GravityThread.playerDownSpeed > 0 && 
				Math.abs(time - System.currentTimeMillis()) > 3000) {
			
		}
		else if (this.hitbox.intersects(Driver.getPlayerhitBox())) {
			Driver.killPlayer();
			this.hitbox.setLocation(startX, startY);
		}

	}
	@Override
	public void show(Graphics g) {
		if (!killed && !(Math.abs(time - System.currentTimeMillis()) < 3000)) {
			g.setColor(Color.BLACK);
			g.drawImage(sprite, getHitBox().x-ScrollThread.scrollFactor, 
				getHitBox().y-ScrollThread.scrollFactorVertical, getHitBox().width, 
					getHitBox().height, null);
		}
		else if (Math.abs(time - System.currentTimeMillis()) < 3000 && !killed) {
			
			if ((System.currentTimeMillis() - time) % 400 < 200) {
				g.setColor(Color.BLACK);
				g.drawImage(sprite, getHitBox().x-ScrollThread.scrollFactor, 
					getHitBox().y-ScrollThread.scrollFactorVertical, getHitBox().width, 
						getHitBox().height, null);
			}
			
		}
		if (enabled) {
			// Draw boss health bar
			g.setColor(Color.BLACK);
			g.drawRect(300, 50, Constants.MAX_BOSS_HEALTH_BAR_WIDTH, 50);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			g.drawString("BOSS HEALTH", 500, 20);
			g.setColor(Color.RED);
			healthPercent = (double) (health) / (double) (maxHealth);
			g.fillRect(299, 51, (int) (Constants.MAX_BOSS_HEALTH_BAR_WIDTH*healthPercent), 48);
			
		}
		
	}
	/**
	 * Gets is this boss should be enabled. Returns true if the boss would appear on screen.
	 * @return
	 */
	public boolean enableCheck() {
		if (Math.abs(hitbox.x-ScrollThread.scrollFactor) < Constants.FRAME_WIDTH) {
			return true;
		}
		else return false;
	}

}
