package aPackage;

import java.awt.Graphics;

public class NPC {
	private String name;
	public NPC(String name) {
		this.name = name;
		
	}
	public static boolean speaking = false;
	public void Speak(String speech) {
		Graphics g = Level.getactiveLevel().getGraphics();
		
		g.drawString(speech, 200, 300);
		
		
		speaking = true;
		while (!Driver.isMousePressed()) System.out.println(speaking);
		
		speaking = false;
		System.out.println(speaking);
		
		
	}
}
