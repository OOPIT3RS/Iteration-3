package Model.Map.Grid;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import Model.Map.HexCoordinate;
import Model.Map.Vector2D;
import Model.Map.View.FlatHexagon;

public class HexGrid extends Grid implements DrawableHexGrid {
	
	public HexGrid(int width, int height) {
		super(width, height);
	}
	
	public void add(HexCoordinate hex, HexTile hex_tile) {
		add(hex.getU(), hex.getV(), hex_tile);
	}
	
	public void add(int u, int v, HexTile hex_tile) {
		if (isValid(u, v)) {
			hex_tile.setLocation(u, v);
			super.insert(u, v + u / 2, hex_tile);
		}
	}
	
	public void add(HexTile hex_tile) {
		HexCoordinate hex = hex_tile.getLocation();
		super.insert(hex.getU(), hex.getV(), hex_tile);
	}

	/*public void drawArc(Graphics g, Point origin, HexCoordinate center, Direction d,
			int size, int hexagon_size) {
		for (HexCoordinate h : HexCoordinate.arc(center, size, d)) {
			// Just outlines for now
			new FlatHexagon(new Point(
					(int)(origin.getX() + 3 / 2.0 * hexagon_size * (h.getU() - center.getU())), 
					(int)(origin.getY() + Math.sqrt(3) * hexagon_size * ((h.getV() - center.getV()) + (h.getU() - center.getU()) / 2.0))),
					hexagon_size, true).draw(g);
		}
	}*/
	
	protected boolean contains(HexTile hex_tile) {
		return (indexOf(hex_tile) != null) ? true : false;
	}
	
	@Override
	public void drawHex(Graphics g, Point origin, HexCoordinate center, int grid_radius, int hexagon_size) {
		ArrayList<HexTile> hex_tiles = getHexTiles(HexCoordinate.circle(center, grid_radius));
		for (HexTile hex_tile : hex_tiles) {
			HexCoordinate h = hex_tile.getLocation();
			// Just outlines for now
			new FlatHexagon(new Point(
					(int)(origin.getX() + 3 / 2.0 * hexagon_size * h.getU()), 
					(int)(origin.getY() + Math.sqrt(3) * hexagon_size * ((h.getV() - center.getV()) + (h.getU() - center.getU()) / 2.0))),
					hexagon_size, false).draw(g);
		}
	}
	
	@Override
	public void drawRectangle(Graphics g, Point origin, HexCoordinate center,
			int grid_width_radius, int grid_height_radius, int hexagon_size) {
		ArrayList<HexTile> hex_tiles = getHexTiles(HexCoordinate.rectangle(center, grid_width_radius, grid_height_radius));
		for (HexTile hex_tile : hex_tiles) {
			HexCoordinate h = hex_tile.getLocation();
			// Just outlines for now
			new FlatHexagon(new Point(
					(int)(origin.getX() + 3 / 2.0 * hexagon_size * h.getU()), 
					(int)(origin.getY() + Math.sqrt(3) * hexagon_size * ((h.getV() - center.getV()) + (h.getU() - center.getU()) / 2.0))),
					hexagon_size, false).draw(g);
		}
	}
	
	@Override
	public void drawRectangleWithCoords(Graphics g, Point origin, HexCoordinate center,
			int grid_width_radius, int grid_height_radius, int hexagon_size) {
		ArrayList<HexTile> hex_tiles = getHexTiles(HexCoordinate.rectangle(center, grid_width_radius, grid_height_radius));
		for (HexTile hex_tile : hex_tiles) {
			HexCoordinate h = hex_tile.getLocation();
			// Just outlines for now
			int x = (int)(origin.getX() + 3 / 2.0 * hexagon_size * (h.getU() - center.getU()));
			int y = (int)(origin.getY() + Math.sqrt(3) * hexagon_size * ((h.getV() - center.getV()) + (h.getU() - center.getU()) / 2.0));
			Point p = new Point(x, y);
			g.drawString(h.getU() + ", " + h.getV(), x-15, y+5);
			new FlatHexagon(p, hexagon_size, false).draw(g);
		}
	}
	
	public void fill(HexTile hex_tile) {
		for (int col = 0; col < super.getWidth(); col++)
			for (int row = 0; row < super.getHeight(); row++)
				insert(col, row, hex_tile);
		//SET SIZE!!!
	}
	
	public HexTile get(HexCoordinate hex) {
		return get(hex.getU(), hex.getV());
	}
	
	@Override
	public HexTile get(int u, int v) {
		if (isValid(u, v))
			return (HexTile)super.get(u, v + u / 2);
		return null;
	}
	
	public ArrayList<HexTile> getHexTiles(ArrayList<HexCoordinate> hex_coords) {		
		ArrayList<HexTile> hex_tiles = new ArrayList<HexTile>();
		for (HexCoordinate hex_coord : hex_coords) {
			HexTile hex_tile = get(hex_coord);
			if (hex_tile != null)
				hex_tiles.add(hex_tile);
		}
		return hex_tiles;
	}
	
	protected Vector2D indexOf(HexTile hex_tile) {
		if (!isEmpty()) {
			if (hex_tile == null) {
				for (int row = 0; row < getHeight(); row++)
					for (int col = 0; col < getWidth(); col++)
						if (see(col, row) == null)
							return new Vector2D(col, row);
			} else
				for (int row = 0; row < getHeight(); row++)
					for (int col = 0; col < getWidth(); col++)
						if (hex_tile.equals(see(col, row)))
							return new Vector2D(col, row);
		}
		return null;
	}
	
	public void insert(int x, int y, HexTile hex_tile) {
		hex_tile.setLocation(x, y - x / 2);
		super.insert(x, y, hex_tile);
	}
	
	protected boolean isValid(HexCoordinate hex) {
		return isValid(hex.getU(), hex.getV());
	}
	
	protected boolean isValid(int u, int v) {
		return u >= 0 && u < getWidth() && v + u / 2 >= 0 && v + u / 2 < getHeight();
	}
	
	protected HexCoordinate locationOf(HexTile hex_tile) {
		if (!isEmpty()) {
			if (hex_tile == null) {
				for (int col = 0; col < getWidth(); col++)
					for (int row = 0; row < getHeight(); row++)
						if (see(col, row) == null)
							return new HexCoordinate(col, row - col / 2);
			} else
				for (int col = 0; col < getWidth(); col++)
					for (int row = 0; row < getHeight(); row++)
						if (hex_tile.equals(see(col, row)))
							return new HexCoordinate(col, row - col / 2);
		}
		return null;
	}
	
	public void remove(HexCoordinate hex) {
		remove(hex.getU(), hex.getV());
	}
	
	@Override
	public void remove(int u, int v) {
		if (isValid(u, v))
			super.remove(u, v - u / 2);
	}
	
	@Override
	public HexTile see(int x, int y) {
		return (HexTile)(super.see(x, y));
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("[ ");
		for (int i = 0; i < getWidth(); i++) {
			string.append("[ ");
			for (int j = 0; j < getHeight(); j++) {
				if (see(i, j) != null)
					string.append(see(i, j).toString());
				if (j < getHeight() - 1)
					string.append(", ");
			}
			string.append(" ]");
			if (i < getHeight() - 1) {
				string.append(", ");
				string.append("\n  ");
			}
		}
		string.append(" ]");
		return string.toString();
	}
	
}
