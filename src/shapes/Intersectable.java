package shapes;

import utility.Material;
import utility.Ray;

/**
 * Intersectable abstract class defining basic object characteristics.
 * @author bfh687
 */
public abstract class Intersectable {
	
	/**
	 * The material of the intersectable.
	 */
	public Material material;
	
	/**
	 * Calculates the intersection point along the ray of the 
	 * intersectable object with the given ray.
	 * @param ray The ray to calculate intersection with.
	 * @return The point of intersection along the ray.
	 */
	public abstract double intersection(Ray ray);
	
	/**
	 * Gets the material of the intersectable.
	 * @return The material of the intersectable.
	 */
	public Material getMaterial() {
		return material;
	}
}
