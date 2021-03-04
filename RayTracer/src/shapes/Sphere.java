package shapes;

import utility.Material;
import utility.Point3D;
import utility.Ray;
import utility.Vector3D;

public class Sphere extends Intersectable {
	private Point3D center;
	private double radius;
	private Material material;
	
	public Sphere(Point3D center, double radius, Material material) {
		this.center = center;
		this.radius = radius;
		this.material = material;
	}
	
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
	
	public Vector3D sphereToRay(Ray ray) {
		return new Vector3D(ray.getOrigin().getX() - center.getX(),
							ray.getOrigin().getY() - center.getY(),
							ray.getOrigin().getZ() - center.getZ());
	}
	
	public Vector3D normal(Vector3D surfacePoint) {
		return (surfacePoint.subtract(center.toVector())).normalize();
	}
	
	public Point3D getCenter() {
		return center;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	@Override
	public String toString() {
		return "Sphere[" + center.toString() + ", " + radius + ", " + material.getColor().toString() + "]";
	}
}
