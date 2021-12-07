package aPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
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
	public static NPC mom = new NPC("Mom", null);
	
	Platform spikeRowPlatform;
	protected Level1(int levelNumber, Image img) {
		super(levelNumber, img);
		// This area should be used to create all game objects assosiated with this level.
		Platform ground = new Platform(new Rectangle(0, 550, 5000, 200), true, Color.BLACK, this);
		Platform houseWallR = new Platform(new Rectangle(400, 350, 40, 200), false, Color.BLACK, this);
		Platform HouseWallL = new Platform(new Rectangle(-40, 350, 40, 400), true, Color.BLACK, this);
		Platform roof = new Platform(new Rectangle(-40, 330, 480, 20), true, Color.BLACK, this);
		
		
		
		ShowPolygon roofSlant = new ShowPolygon(new int[] {-80, 480, 200}, new int[] {350, 350, 100}, 3, this);
		
		Spike startSpike = new Spike(new int[] {300, 400, 350}, new int[] {550, 550, 460}, this);
		startSpike.getPolygon().translate(300, 0);
		GroundEnemy testEnemy = new GroundEnemy(new Rectangle(900, 500, 50, 50), Constants.testEnemy, 600, 1200, 5, this);
		Spike spikeRowInit = new Spike(new int[] {2100, 2200, 2150}, new int[] {550, 550, 460}, this);
		// Create a line of spikes
		Spike[] spikeRow = new Spike[10];
		for (int i = 0; i < spikeRow.length; i++) {
			spikeRow[i] = new Spike(spikeRowInit.getPolygon().xpoints, spikeRowInit.getPolygon().ypoints, this);
			spikeRow[i].getPolygon().translate(100*i, 0);
		}
		
		spikeRowPlatform = new Platform(new Rectangle(2200, 350, 500, 70), true, Color.BLACK, this);
		GroundEnemy spikePlatformEnemy = new GroundEnemy(new Rectangle(2300, 300, 50, 50), Constants.testEnemy, 2200, 2700, 5, this);
	}
	
	public static Level1 L1 = new Level1(2, Constants.backGround);
	boolean spoken = false;
	/**
	 * This level should be used for any updates or checks specific to this level
	 */
	@Override
	public void levelLoop() {
		
		if (!spoken && Driver.xpos == 150) {
			Driver.setCutscene(true);
			this.getGraphics().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
			mom.Speak("Wake up, its time for school. Just move to the right and you'll be there. And click to exit any text.");
			while (Driver.mouseClicked) {
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			mom.Speak("Wait, what is this \'Click\' I speak of. Don't ask me, I don't know.");
			while (Driver.mouseClicked) {
				try {Thread.sleep(0);} catch (InterruptedException e) {}}
			mom.Speak("Oh, and there's a door to the right you can walk right on through");
			while (Driver.mouseClicked) {
				try {Thread.sleep(0);} catch (InterruptedException e) {} 
			}
			mom.Speak("Be careful of any lancers you find on the street. They will try    to stop you.");
		
			spoken = true;
			Driver.setCutscene(false);
		}
		if (Driver.xpos > 4000) {
			Level.setLevel(0, 500, 3);
		}
		
				
		
	}
	
	
	public static void initialize() {
		L1 = new Level1(2, Constants.backGround);
	}
	
	
	

}
