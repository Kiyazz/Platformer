package aPackage;

import java.awt.FlowLayout;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
/**
 * This level represents the intro screen and the the intro cutscene and selections
 * @author Kiya
 *
 */
@SuppressWarnings("serial")
public final class IntroPanel extends Level {
	
	public static IntroPanel ip;
	
	public FlowLayout flowManager = new FlowLayout();
	public IntroPanel(int id) {
		super(id);
		this.setLayout(flowManager);
		flowManager.setAlignment(FlowLayout.CENTER);
		
		
	}
	boolean soundPlayed = false;
	
	@Override
	public void levelLoop() {
		if (!soundPlayed) playSound("Recording.mp3");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Level.setLevel(0, 500, 2);
		
	}

}
