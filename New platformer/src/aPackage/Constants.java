package aPackage;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * This class contains all constants used throughout this program
 * @author Kiya.Zadeh22
 *
 */
public final class Constants {
	/*
	 * Declare all images here
	 */
	public static Image playerSprite1, backGround, testEnemy;
	
	public static final int SPEED = 10;
	public static final int PLAYER_WIDTH = 50;
	public static final int TERMINAL_VELOCITY = 20;
	public static final int JUMP_SPEED = 20;
	
	// makes the class unable to be initialized
	private Constants() {
		
	}
	private static Constants c = new Constants();
	/**
	 * Initialize images
	 */
	static {
		
		try {
			
			testEnemy = Toolkit.getDefaultToolkit().getImage(c.getClass().getResource("/Sprites/WIN_20191122_17_24_48_Pro.jpg"));
			backGround = Toolkit.getDefaultToolkit().getImage(c.getClass().getResource("/Sprites/OIP.jpg"));
			playerSprite1 = Toolkit.getDefaultToolkit().getImage(c.getClass().getResource("/Sprites/WIN_20191122_17_25_51_Pro.jpg"));
			
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
