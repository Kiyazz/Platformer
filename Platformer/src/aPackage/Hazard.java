package aPackage;

import java.awt.Shape;
import java.util.ArrayList;



/**
 * This class is the superclass of all things that are able to kill the player and appear on screen
 * @author Kiya.Zadeh22
 *
 */

public abstract class Hazard {
	public static ArrayList<Hazard> hazardList = new ArrayList<Hazard>();
	public Shape shape;
	
	public Hazard() {
		hazardList.add(this);
	}
	
	
	
	/** 
	 * This method should be overridden in order to set how this hazard kills the player.
	 *  Use <Code> Driver.killPlayer() </Code> to do this
	 */
	public abstract void killCondition();
	
}
