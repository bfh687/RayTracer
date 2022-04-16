package utility;

/**
 * Represents a point in 3D space (XYZ).
 * @author bfh687
 */
public class Point3D {
	
	/**
	 * The x-position of the point.
	 */
	private double x;
	
	/**
	 * The y-position of the point.
	 */
	private double y;
	
	/**
	 * The z-position of the point.
	 */
	private double z;
	
	/**
	 * Creates a new point at the origin.
	 */
	public Point3D() {
		x = y = z = 0;
	}
	
	/**
	 * Creates a new point at the given xyz position.
	 * @param x The x-position of the point.
	 * @param y The y-position of the point.
	 * @param z The z-position of the point.
	 */
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = -y;
		this.z = z;
	}
	
	/**
	 * Copy constructor.
	 * @param otherPoint Creates a point copy from the given point.
	 */
	public Point3D(Point3D otherPoint) {
		this.x = otherPoint.getX();
		this.y = otherPoint.getY();
		this.z = otherPoint.getZ();
	}
	
	/**
	 * Gets the x-position of the point.
	 * @return The x-position of the point.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Gets the y-position of the point.
	 * @return The y-position of the point.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Gets the z-position of the point.
	 * @return The z-position of the point.
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Converts the point to a vector.
	 * @return The vector representation of the point.
	 */
	public Vector3D toVector() {
		return new Vector3D(x, y, z);
	}
}
