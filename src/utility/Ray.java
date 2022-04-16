package utility;

/**
 * Represents a ray, with an origin and direction, in 3D space (XYZ).
 * @author bfh687
 */
public class Ray {
	
	/**
	 * The origin of the ray.
	 */
	private Vector3D origin;
	
	/**
	 * The direction of the ray.
	 */
	private Vector3D direction;
	
	/**
	 * Creates a new ray with the given origin and direction.
	 * @param origin The origin of the ray.
	 * @param direction The direction of the ray.
	 */
	public Ray(Vector3D origin, Vector3D direction) {
		this.origin = origin;
		this.direction = direction.normalize();
	}
	
	/**
	 * Calculates the point along the ray at <i>t</i>.
	 * @param t The distance along the ray.
	 * @return The point along the ray at <i>t</i>
	 */
	public Point3D pointAt(float t) {
		Vector3D temp = origin.add(direction.multiply(t));
		return new Point3D(temp.getX(), temp.getY(), temp.getZ());
	}
	
	/**
	 * Gets the origin of the ray.
	 * @return The origin of the ray.
	 */
	public Vector3D getOrigin() {
		return origin;
	}
	
	/**
	 * Gets the direction of the ray.
	 * @return The direction of the ray.
	 */
	public Vector3D getDirection() {
		return direction;
	}
	
	public String toString() {
		return direction.toString();
	}
}
