package shapes;

import utility.Material;
import utility.Point3D;
import utility.Ray;
import utility.Vector3D;

/**
 * A sphere object.
 * @author bfh687
 */
public class Sphere extends Intersectable {
	
	/**
	 * The point representation of the sphere's center.
	 */
	private Point3D center;
	
	/**
	 * The sphere's radius.
	 */
	private double radius;
	
	/**
	 * Creates a new sphere with the given center, radius, and material.
	 * @param center The center point of the sphere.
	 * @param radius The radius of the sphere.
	 * @param material The material of the sphere.
	 */
	public Sphere(Point3D center, double radius, Material material) {
		this.center = center;
		this.radius = radius;
		this.material = material;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public double intersection(Ray ray) {
		double a = 1;
		double b = 2 * ray.getDirection().dotProduct(sphereToRay(ray));
		double c = sphereToRay(ray).dotProduct(sphereToRay(ray)) - (radius * radius);
		double discriminant = b * b - 4 * a * c;

		if (discriminant >= 0.0) {
			double t = (-b - Math.sqrt(discriminant)) / (2 * a);
			if (t > 0) {
				return t;
			} 
		}
		return 0;
	}
	
	/**
	 * Calulates the surface normal vector of the given point on the sphere.
	 * @param surfacePoint The sphere's surface point.
	 * @return The surface normal vector of the given point on the sphere.
	 */
	public Vector3D normal(Vector3D surfacePoint) {
		return (surfacePoint.subtract(center.toVector())).normalize();
	}
	
	/**
	 * Gets the center point of the circle.
	 * @return The center point of the circle.
	 */
	public Point3D getCenter() {
		return center;
	}
	
	/**
	 * Gets the radius of the circle.
	 * @return The radius of the circle.
	 */
	public double getRadius() {
		return radius;
	}
	
	/**
	 * Calculates the vector from the ray to the sphere's center.
	 * @param ray The ray to compare to the sphere's center.
	 * @return The vector from the ray to the sphere's center.
	 */
	private Vector3D sphereToRay(Ray ray) {
		return new Vector3D(ray.getOrigin().getX() - center.getX(),
							ray.getOrigin().getY() - center.getY(),
							ray.getOrigin().getZ() - center.getZ());
	}
	
	public void setCenter(Vector3D vec) {
		center = vec.toPoint();
	}
}
