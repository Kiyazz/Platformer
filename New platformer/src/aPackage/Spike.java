package aPackage;

import java.awt.Polygon;


public class Spike extends Hazard {
	/**
	 * Constructs a new Spike instance with a triangle as its bounds
	 * @param x The x coords of the spike corners
	 * @param y The y coords of the spike corners
	 */
	
	private Polygon tri;
	public Spike(int[] x, int[] y, Level l) {
		tri = new Polygon(x, y, 3);
		
		
		l.spikeList.add(this);
	}
	
	@Override
	public void killCondition() {
		
		if (tri.intersects(Driver.getPlayerhitBox())) {
			Driver.killPlayer();
		}
	}
	
	public static void killConditionAll(Level l) {
		for (int i = 0; i < l.spikeList.size(); i++) {
			l.spikeList.get(i).killCondition();
		}
	}
	public Polygon getPolygon () {
		return tri;
	}

	

	

	
	

}
