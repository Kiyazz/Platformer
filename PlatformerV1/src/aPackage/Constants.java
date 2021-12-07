package aPackage;


import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;



/**
 * 
 * This class contains most constants used throughout this program, as well as all image loading
 * @author Kiya Zadeh
 * 
 *
 */
public final class Constants {
	
	/*
	 * Declare all images here
	 */
	public static BufferedImage playerSprite1;
	
	
	public static Image testEnemy;
	
	public static BufferedImage backGround;
	public static BufferedImage BG2 , calcTest;
	
	public static final int SPEED = 10;
	public static final int PLAYER_WIDTH = 50;
	public static final int TERMINAL_VELOCITY = 20;
	public static final int JUMP_SPEED = 20;
	public static final int BOSS_INVULNERABILITY = 2500;
	
	public static final int SCROLL_BOUND_RIGHT = 700;
	public static final int SCROLL_BOUND_LEFT = 150;
	public static final int SCROLL_BOUND_DOWN = 500;
	public static final int SCROLL_BOUND_UP = 100;
	
	public static final int MAX_BOSS_HEALTH_BAR_WIDTH = 1000;
	
	public static final int FRAME_WIDTH = 2000;
	public static final int FRAME_HEIGHT = 800;
	
	// makes the class unable to be initialized
	private Constants() {
	}
	
	/**
	 * Initialize images
	 */
	static {
		
		try {
			
			testEnemy = ImageIO.read(Constants.class.getResourceAsStream("/Sprites/WIN_20191122_17_24_48_Pro.jpg"));
			playerSprite1 = ImageIO.read(Constants.class.getResourceAsStream("/Sprites/WIN_20191122_17_25_51_Pro.jpg"));
			backGround = ImageIO.read(Constants.class.getResourceAsStream("/Sprites/newWorkingBackground.png"));
			calcTest = ImageIO.read(Constants.class.getResourceAsStream("/Sprites/Calculus-Test-Page_2.jpg"));
			backGround = Scalr.resize(backGround, 1400, (BufferedImageOp) null);
			calcTest = Scalr.crop(calcTest, 200, 100, 700, 700, (BufferedImageOp) null);
			BG2 = (BufferedImage) ImageIO.read(Constants.class.getResourceAsStream("/Sprites/level2BG.jpg"));
			
			
			
			System.out.println("Image Loading Complete");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
