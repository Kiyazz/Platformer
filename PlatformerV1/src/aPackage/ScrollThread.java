package aPackage;

import java.awt.Point;
/**
 * Class contains a thread that represents scrolling of the screnn through the scrollFactor variable
 * @author Kiya.Zadeh22
 *
 */
public class ScrollThread {

	private ScrollThread() {}
	/**
	 * Single instance of this thread
	 */
	private static ScrollThread scrollThread = new ScrollThread();
	/**
	 * This variable contains the value for how much the screen is scrolled.
	 * All graphics should be translated by the opposite of this value.
	 */
	public static int scrollFactor = 0;
	public static int scrollFactorVertical = 0;
	/**
	 * Gets the instance of ScrollThread
	 * @return The instance of ScrollThread
	 */
	public static ScrollThread getInstance() {
		return scrollThread;
	}
	
	
	public static void updateScrolling() {
		Point p = Driver.getPlayerhitBox().getLocation();
		if (p.x - scrollFactor > Constants.SCROLL_BOUND_RIGHT) {
			scrollFactor += Constants.SPEED;
		}
		if (p.x - scrollFactor < Constants.SCROLL_BOUND_LEFT) {
			scrollFactor -= Constants.SPEED;
		}
		
		if (p.y - scrollFactorVertical < Constants.SCROLL_BOUND_UP) {
			scrollFactorVertical -= 10;
		}
		if (p.y - scrollFactorVertical > Constants.SCROLL_BOUND_DOWN) {
			scrollFactorVertical += 10;
		}
	}

}
