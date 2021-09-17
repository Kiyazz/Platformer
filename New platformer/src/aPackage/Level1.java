package aPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * This will be the first level of this game. 
 * <p>
 * It can be used as a template for other level creators to use
 * @author Kiya.Zadeh22
 *
 */
@SuppressWarnings({ "serial", "unused" })
public class Level1 extends Level {
	NPC bob = new NPC("bob");
	protected Level1(int levelNumber) {
		super(levelNumber);
		// This area should be used to create all game objects assosiated with this level.
		Platform testyboi = new Platform(new Rectangle(0, 550, 5000, 200), true, Color.BLACK, this);
		Platform testyboi2 = new Platform(new Rectangle(200, 350, 100, 200), true, Color.BLACK, this);
		Platform testyboi3 = new Platform(new Rectangle(600, 300, 200, 50), true, Color.BLACK, this);
		
		Spike sTest = new Spike(new int[] {300, 400, 350}, new int[] {550, 550, 460}, this);
		GroundEnemy G = new GroundEnemy(new Rectangle(900, 500, 50, 50), Constants.testEnemy, 600, 1200, 5, this);
		
	}
	
	public static Level1 L1;
	boolean spoken = false;
	/**
	 * This level should be used for any updates or checks specific to this level
	 */
	@Override
	public void levelLoop() {
		if (!spoken && Driver.xpos == 500) {
			this.getGraphics().setFont(new Font(Font.SANS_SERIF, 40, 40));
			bob.Speak("Hello, my name is bob");
			spoken = true;
		}
		
	}
	
	
	
	

}
