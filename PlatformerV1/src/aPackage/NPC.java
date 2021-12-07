package aPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
/**
 * This class is used to represent a non-playable character, 
 * which for the puropse of this game is not physical, 
 * and only appears on screen to deliver speeches.
 * The NPC has an optional sprite, which will show up to the left of the text box each time the character speaks.
 * Otherwise, the speak method will pop up a text box with the NPC name and text in the box. 
 * NPC's should be static so there only exists one copy of a certain character across the program. 
 * If there is a need for a NPC with more funcions, extend this class
 * 
 * @author Kiya.Zadeh22
 *
 */
public class NPC {
	private String name;
	private Image img;
	
	private static final int MAX_CHARACTER_LENGTH_IN_TEXTBOX = 35;
	private static final int PIXELS_BETWEEN_LINES = 40;
	/**
	 * Creates a new NPC object
	 * @param name The name of this character
	 * @param sprite The image sprite for this character. This parameter is optional
	 */
	public NPC(String name, Image sprite) {
		this.setName(name);
		this.setImg(sprite);
	}
	public static volatile boolean speaking = false;
	/**
	 * Displays a text box on screen with the given string as its content
	 * @param speech The text to display on screen
	 */
	public synchronized void Speak(String speech) {
		Graphics g = Level.getactiveLevel().getGraphics();
		
		String[] speeches;
		int speechL = speech.length();
		int speechCount = 0;
		while (speechL > 20) {
			speechCount++;
			speechL -= 20;
		}
		if (speech.length() > 20) {
			speeches = new String[speechCount + 1];
			for (int i = 0; i <= speechCount; i++) {
				if ((i+1)*MAX_CHARACTER_LENGTH_IN_TEXTBOX > speech.length()) {
					speeches[i] = speech.substring(i*MAX_CHARACTER_LENGTH_IN_TEXTBOX, speech.length());
					break;
				}
				speeches[i] = speech.substring(i*MAX_CHARACTER_LENGTH_IN_TEXTBOX, (i+1)*MAX_CHARACTER_LENGTH_IN_TEXTBOX);
			}
		}
		else {
			speeches = new String[1];
			speeches[0] = speech;
		}
		
		
		g.setColor(Color.GRAY);
		g.fillRect(290, 255, 510, 200);
		g.setColor(Color.RED);
		g.fillRect(290, 250, 510, 5);
		g.fillRect(290, 250, 5, 200);
		g.fillRect(800, 250, 5, 200);
		g.fillRect(290, 450, 515, 5);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		g.setColor(Color.BLACK);
		for (int i = 0; i < speeches.length; i++) {
			try {
			g.drawString(speeches[i], 300, 300 + (PIXELS_BETWEEN_LINES*i));
			} catch (NullPointerException e) {
				break;
			}
		}
		g.setFont(new Font(Font.DIALOG, Font.ITALIC, 50));
		g.setColor(new Color(255, 255, 255));
		g.drawString(getName(), 270, 205);
		
		speaking = true;
		while (!Driver.isMousePressed()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		speaking = false;
		
		
		
	}
	/**
	 * Gets the name of this NPC
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of this NPC
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
}
