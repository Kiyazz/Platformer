package aPackage;

import javazoom.jl.decoder.JavaLayerException;


/**
 * This level represents the intro screen and the the intro cutscene and selections
 * @author Kiya
 *
 */
@SuppressWarnings("serial")
public final class IntroPanel extends Level {
	
	public static IntroPanel ip;
	
	
	public IntroPanel(int id) {
		super(id, null);
		
		
		
	}
	boolean soundPlayed = false;
	
	@Override
	public void levelLoop() {
		Driver.setCutscene(true);
		if (!soundPlayed) {
			/*
			try {
				playSoundJLayer(this.getClass().getResourceAsStream("/audio/Recording.mp3"));
			} catch (JavaLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
		}
		Panels.showLoadingScreen(this.getGraphics());
			
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Driver.setCutscene(false);
		Level.setLevel(100, 500, 2);
		
	}
	
	public static void initialize() {
		ip = new IntroPanel(1);
	}

	

}
