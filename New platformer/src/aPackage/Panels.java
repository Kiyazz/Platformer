package aPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

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
		if (Driver.lives == -1) {
			g.drawString("GAME OVER", 500, 200);
			g.drawString("Game closing in 5 seconds", 400, 260);
			
			
		}
		else {
			g.drawString("You died", 500, 200);
			g.drawString("Lives = " + Integer.toString(Driver.lives), 500, 250);
		}
	}
	/**
	 * This panel should contain the first level of the game. Whenever level 1 is to be played, 
	 * this should be set as the panel of the main frame
	 */
	public static JPanel level1 = new JPanel() {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
		}
		private static final long serialVersionUID = 5549405986276684852L;
	};
}
