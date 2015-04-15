package Model.Map.View;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

@SuppressWarnings("serial")
public class FlatHexagon extends Polygon {
	
	private static final int SIDES = 6;
	private double PI2 = 2 * Math.PI;
	
	private final Point center_;
	private final int size_;
	private final boolean filled_;
	
	public FlatHexagon(Point p, int size, boolean filled) {
		npoints = SIDES;
		xpoints = new int[SIDES];
		ypoints = new int[SIDES];
		
		center_ = p;
		size_ = size;
		filled_ = filled;
		
		for (int i = 0; i < npoints; i++) {
			double angle = PI2 * i / SIDES;
			int x;
			int y;
			if (filled) {
				x = (int)(center_.x + Math.cos(angle) * (size_ - 1));
				y = (int)(center_.y + Math.sin(angle) * (size_ - 1));
			} else {
				x = (int)(center_.x + Math.cos(angle) * size_);
				y = (int)(center_.y + Math.sin(angle) * size_);
			}
			xpoints[i] = x;
			ypoints[i] = y;
		}
	}
	
	public void draw(Graphics g) {
		if (!filled_)
			g.drawPolygon(xpoints, ypoints, npoints);
		else
			g.fillPolygon(xpoints, ypoints, npoints);
	}
	
}
