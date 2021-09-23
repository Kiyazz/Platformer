package aPackage;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
/**
 * An implementation of windowListener for the main JFrame of the program
 * @author Kiya.Zadeh22
 *
 */
public class WindowEvents implements WindowListener {
	
	public WindowEvents() {}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * This method should be called in order to close the program
	 */
	public void windowClosing(WindowEvent e) {
		Driver.closeGame();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
