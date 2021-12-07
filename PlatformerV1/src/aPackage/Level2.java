package aPackage;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

@SuppressWarnings("serial")
/**
 * The second level of the game!!!!!
 * @author Kiya.Zadeh22
 *
 */
public class Level2 extends Level {
	
	protected static Level2 L2;
	
	Platform rightWall = new Platform(new Rectangle(3500, 0, 100, 600), true, Color.BLACK, this);
	Boss calculusTest = new Boss(new Rectangle(3350, 400, 150, 150), Constants.calcTest, 5, 3, this);
	NPC friend = new NPC("Friend", null);

	/**
	 * Constructor containing the necessary game objects for the level
	 * @param levelNumber a constant level number for this level
	 * @param img background image
	 */
	@SuppressWarnings("unused")
	public Level2(int levelNumber, Image img) {
		super(levelNumber, img);
		// TODO Auto-generated constructor stub
		
		Platform floor = new Platform(new Rectangle(0, 550, 4000, 100), true, Color.BLACK, this );
		//Platform stepStarter = new Platform(new Rectangle(2000, 500, 100, 100), true, Color.BLACK, this);
		for (int i = 0; i < 5; i++) {
			
			Platform p = new Platform(new Rectangle(2000 + 100*i, 500 - 100*i, 100, 100 + 100*i), true, Color.BLACK, this);
		}
		
	
	}
	boolean spoken;
	@Override
	public void levelLoop() {
		if (!spoken && Driver.xpos > 100) {
			friend.Speak("Hey man, good to see you here. Be  careful of that Calculus test today, its a hard one");
			while(!Driver.isMousePressed()) {
				try {Thread.sleep(0);} catch (InterruptedException e) {	e.printStackTrace();}
			}
			spoken = true;
		}
		if (Driver.killed) {
			ScrollThread.scrollFactor = 2000;
			
			Driver.xpos = 2400;
			Driver.ypos = 0;
			calculusTest.enabled = false;
		}
		if (calculusTest.killed) {
			this.platformList.remove(rightWall);
			this.showableList.remove(rightWall);
			
		}
		if (Driver.xpos > 3500) {
			friend.Speak("Hey, you got an A, i'm impressed. There will be more school days and more content to come later on, it is still version 1 of the game");
		}
		
	}

	

	public static void initialize() {
		L2 = new Level2(3, Scalr.resize(Constants.BG2, 1400, 600, (BufferedImageOp) null));
	}

}
