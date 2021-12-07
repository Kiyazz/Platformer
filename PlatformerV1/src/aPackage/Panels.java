package aPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
/**
 * Class that conains static methods for showing a few single-use screens
 * @author Kiya.Zadeh22
 *
 */
public class Panels {
	/**
	 * Used to show the graphical side of things after a death is recorded. Lasts 5 seconds
	 * @param g A reference to the graphics object where this is called
	 * @throws InterruptedException If the thread this is called in is interrupted and unable to be slept
	 */
	public static void showKilledScreen(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 100000, 4000);
		g.setColor(Color.RED);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		Driver.setCutscene(true);
		if (Driver.lives == -1) {
			g.drawString("GAME OVER", 500, 200);
			g.drawString("Game closing in 5 seconds", 400, 260);
			
			
			
		}
		else {
			g.drawString("You died", 500, 200);
			g.drawString("Lives = " + Integer.toString(Driver.lives), 500, 250);
		}
		Driver.setCutscene(false);
	}
	public static void showLoadingScreen(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 5000, 5000);
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		g.drawString("Loading Screen. Graphic to come later. No complaining.", 20, 400);
		
	}
	
}
