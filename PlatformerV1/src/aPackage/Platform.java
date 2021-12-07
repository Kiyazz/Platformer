package aPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This class is the superclass for all physical platforms, walls, and other solid objects in levels
 * @author Kiya.Zadeh22
 *
 */
public class Platform extends Object implements Show {
	
	private Rectangle rect;
	private boolean isSolid;
	private Color color;
	
	/**
	 * 
	 * @param rect The location and size of the platform
	 * @param isSolid If the platform is 'Solid' (If a player can move through it)
	 * @param color The color of the platform
	 */
	public Platform (Rectangle rect, boolean isSolid, Color color, Level l) {
		this.rect = rect;
		this.setSolid(isSolid);
		this.setColor(color);
		l.showableList.add(this);
		l.platformList.add(this);
	}
	/**
	 * Checks if a player is 'inside' the platform
	 * Inside is defined as the center point being contained by the boundary rectangle
	 * @return a boolean if the player is inside
	 */
	public boolean isPlayerInside() {
		if (rect.contains(Driver.xpos + Constants.PLAYER_WIDTH/2, Driver.ypos + Constants.PLAYER_WIDTH/2)) return true;
		else return false;
	}
	/**
	 * Updates if the player is 'grounded'
	 * Grounded is defined as the bottom of the player sprite being within the downward speed of the player
	 * @return If the player is touching the ground
	 */
	
	public Boolean updateGrounded() {
		if (Math.abs(Driver.ypos + Constants.PLAYER_WIDTH - this.rect.getY()) < 10) {
			
			Driver.ypos = (int) (rect.getY() - 50);
			return true;
		}
		else return false;
		
	}
	/**
	 * Checks if the player is grounded on all platforms. 
	 * Will be true if player is on any platform and false if on no platform
	 */
	public static void updateGroundedAll (Level l) {
		boolean tempGrounded = false;
		for (int i = 0; i < l.platformList.size(); i++) {
			if (!(l.platformList.get(i).getRect().x < Driver.xpos + Constants.PLAYER_WIDTH && l.platformList.get(i).getRect().x + l.platformList.get(i).getRect().width > Driver.xpos)) continue;
			if (Math.abs(Driver.ypos + Constants.PLAYER_WIDTH - l.platformList.get(i).rect.getY()) <= GravityThread.playerDownSpeed && l.platformList.get(i).isSolid()) {
				tempGrounded = true;
				GravityThread.jumped = false;
				GravityThread.playerDownSpeed = 0;
				Driver.ypos = (int) (l.platformList.get(i).rect.getY() - Constants.PLAYER_WIDTH - GravityThread.playerDownSpeed);
			}
			
		}
		Driver.isGrounded = tempGrounded;
		
		
			
		
		
	}
	/**
	 * Checks if the player is bumping into the roof. If yes, stops any upward momentum the player has
	 */
	public void roofCollision() {
		Point playerPos = Driver.getPlayerhitBox().getLocation();
		// this.rect.x < playerPos.x + Constants.PLAYER_WIDTH 
		if (this.rect.x < playerPos.x + Constants.PLAYER_WIDTH && rect.x + rect.width > playerPos.x
				&& Math.abs(((this.rect.y + this.rect.height) + GravityThread.playerDownSpeed) - playerPos.y) <= -GravityThread.playerDownSpeed 
				&& this.isSolid()) {
			
			GravityThread.playerDownSpeed = 0;
		}
	}
	/**
	 * Does the same thing as {@code roofCollisioin()} but for ALL platforms in a level
	 * @param l The level to check the platforms for
	 */
	public static void roofCollisionAll(Level l) {
		for (Platform p : l.platformList) {
			p.roofCollision();
		}
	}
	/**
	 * Gets the color of the platform
	 * @return Color of the platform
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * Sets a new color for the house
	 * @param color The color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * Get the rectangle outline of the object
	 * @return The rectangle outline of this object
	 */
	public Rectangle getRect() {
		return rect;
	}
	public boolean isSolid() {
		return isSolid;
	}
	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}
	@Override
	public void show(Graphics g) {
			Rectangle r = this.getRect();
			g.setColor(this.getColor());
			g.fillRect(r.x - ScrollThread.scrollFactor, r.y - ScrollThread.scrollFactorVertical, r.width, r.height);
	}
	
	

}
