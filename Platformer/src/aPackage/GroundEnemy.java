package aPackage;

import java.awt.Image;
import java.awt.Rectangle;

public class GroundEnemy extends Enemy {
	
	private int leftBound, rightBound, speed;
	private boolean left;
	
	public GroundEnemy(Rectangle hitbox, Image sprite, int leftBound, int rightBound, int speed, Level l) {
		super(hitbox, sprite, l);
		this.leftBound = leftBound;
		this.rightBound = rightBound;
		this.speed = speed;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		if (this.hitbox.intersects(Driver.getPlayerhitBox()) && 
				Driver.getPlayerhitBox().x+Constants.PLAYER_WIDTH > this.hitbox.x && 
				Driver.getPlayerhitBox().x < this.hitbox.x + this.hitbox.width &&
				GravityThread.playerDownSpeed > 0) {
			
			this.killed = true;
			GravityThread.jump();
		}
	}

	@Override
	public void killCondition() {
		// TODO Auto-generated method stub
		if (this.hitbox.intersects(Driver.getPlayerhitBox()) && 
				Driver.getPlayerhitBox().x+Constants.PLAYER_WIDTH > this.hitbox.x && 
				Driver.getPlayerhitBox().x < this.hitbox.x + this.hitbox.width &&
				GravityThread.playerDownSpeed > 0) {
			
		}
		else if (this.hitbox.intersects(Driver.getPlayerhitBox())) {
			Driver.killPlayer();
		}
		
	}

	@Override
	public void cleanup() {
	
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (hitbox.x + hitbox.width < rightBound && !left) {
			hitbox.x += speed;
			
		}
		if (hitbox.x > leftBound && left) {
			hitbox.x -= speed;
			
		}
		if (hitbox.x + hitbox.width == rightBound || hitbox.x == leftBound) {
			left = !left;
		}
	}

}
