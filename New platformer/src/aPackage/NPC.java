package aPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class NPC {
	private String name;
	public NPC(String name, Image sprite) {
		this.name = name;
		
	}
	public static boolean speaking = false;
	public void Speak(String speech) {
		Graphics g = Level.getactiveLevel().getGraphics();
		String[] speeches;
		int speechL = speech.length();
		int speechCount = 0;
		while (speechL > 40) {
			speechCount++;
			speechL -= 40;
		}
		if (speech.length() > 40) {
			speeches = new String[speechCount + 1];
			for (int i = 0; i <= speechCount; i++) {
				speeches[i] = speech.substring(i*40, (i+1)*40);
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
		g.setFont(new Font(Font.SANS_SERIF, 50, 50));
		g.setColor(Color.BLACK);
		g.drawString(speech, 300, 300);
		
		
		speaking = true;
		while (!Driver.isMousePressed()) System.out.println(speaking);
		
		speaking = false;
		System.out.println(speaking);
		
		
	}
}
