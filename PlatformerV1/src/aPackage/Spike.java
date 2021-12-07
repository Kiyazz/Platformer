package aPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;


public class Spike extends Hazard implements Show {
	/**
	 * Constructs a new Spike instance with a triangle as its bounds
	 * @param x The x coords of the spike corners
	 * @param y The y coords of the spike corners
	 */
	
	private Polygon tri;
	public Spike(int[] x, int[] y, Level l) {
		tri = new Polygon(x, y, 3);
		
		
		l.showableList.add(this);
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

	@Override
	public void show(Graphics g) {
		g.setColor(Color.RED);
		Polygon p = new Polygon(this.getPolygon().xpoints, this.getPolygon().ypoints, this.getPolygon().npoints);
		p.translate(-ScrollThread.scrollFactor, -ScrollThread.scrollFactorVertical);
		g.fillPolygon(p);
		
	}

	

	

	
	

}
