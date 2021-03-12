package shapes;

import utility.Material;
import utility.Ray;

// abstract intersectable class
public abstract class Intersectable {
	public Material material;
	public abstract double intersection(Ray ray);
	public Material getMaterial() {
		return material;
	}
}
