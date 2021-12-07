package aPackage;

import java.awt.Graphics;
import java.awt.Polygon;

@SuppressWarnings("serial")
public class ShowPolygon extends Polygon implements Show, Cloneable {

	public ShowPolygon(int[] xpoints, int[] ypoints, int npoints, Level l) {
		super(xpoints, ypoints, npoints);
		l.showableList.add(this);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show(Graphics g) {
		Polygon p = (Polygon) this.clone();
		p.translate(-ScrollThread.scrollFactor, -ScrollThread.scrollFactorVertical);
		g.fillPolygon(p);
		

	}
	
	@Override
	public Object clone() {
		return new Polygon(this.xpoints, this.ypoints, this.npoints);
	}

}
