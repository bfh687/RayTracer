package utility;

/**
 * Represents a vector in 3D space (XYZ).
 * @author bfh687
 */
public class Vector3D {
	
	/**
	 * The x-direction of the vector.
	 */
	private double x;
	
	/**
	 * The y-direction of the vector.
	 */
	private double y;
	
	/**
	 * The z-direction of the vector.
	 */
	private double z;
	
	/**
	 * Creates a new vector with the given xyz directions.
	 * @param x The x-direction of the vector.
	 * @param y The y-direction of the vector.
	 * @param z The z-direction of the vector.
	 */
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Copy constructor.
	 * @param otherVector Creates a vector copy from the given vector.
	 */
	public Vector3D(Vector3D otherVector) {
		this.x = otherVector.getX();
		this.y = otherVector.getY();
		this.z = otherVector.getZ();
	}
	
	/**
	 * Calculates the magnitude of the vector.
	 * @return The magnitude of the vector.
	 */
	public double magnitude() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * Calculates the vector's normal vector.
	 * @return The vector's normal vector.
	 */
	public Vector3D normalize() {
		double mag = magnitude();
		return new Vector3D(x / mag, y / mag, z / mag);
	}
	
	/**
	 * Calculates the cross product of <i>this</i> vector and the other vector.
	 * @param otherVector The other vector to calculate the cross product of.
	 * @return The cross product of <i>this</i> vector and the other vector.
	 */
	public Vector3D cross(Vector3D otherVector) {
		double x = (this.y * otherVector.getZ()) - (this.z * otherVector.getY());
		double y = (this.z * otherVector.getX()) - (this.x * otherVector.getZ());
		double z = (this.x * otherVector.getY()) - (this.y * otherVector.getX());
		return new Vector3D(x, y, z);
	}
	
	/**
	 * Calculates the dot product of <i>this</i> vector and the other vector.
	 * @param otherVector The other vector to calculate the dot product of.
	 * @return The dot product of <i>this</i> vector and the other vector.
	 */
	public double dotProduct(Vector3D otherVector) {
		double x = this.x * otherVector.getX();
		double y = this.y * otherVector.getY();
		double z = this.z * otherVector.getZ();
		return x + y + z;
	}
	
	/**
	 * Adds <i>this</i> vector to the given vector.
	 * @param otherVector The other vector to be added.
	 * @return The sum of <i>this</i> vector and the other vector.
	 */
	public Vector3D add(Vector3D otherVector) {
		double x = this.x + otherVector.getX();
		double y = this.y + otherVector.getY();
		double z = this.z + otherVector.getZ();
		return new Vector3D(x, y, z);
	}
	
	/**
	 * Subtracts the given vector from <i>this</i> vector.
	 * @param otherVector The other vector to be subtracted.
	 * @return The resulting vector from the subtraction operation..
	 */
	public Vector3D subtract(Vector3D otherVector) {
		double x = this.x - otherVector.getX();
		double y = this.y - otherVector.getY();
		double z = this.z - otherVector.getZ();
		return new Vector3D(x, y, z);
	}
	
	/**
	 * Multiplies the vector by the given scalar.
	 * @param scalar Scalar value to multiply by.
	 * @return The resulting vector of the scalar multiplation operation.
	 */
	public Vector3D multiply(double scalar) {
		return new Vector3D(x * scalar, y * scalar, z * scalar);
	}
	
	/**
	 * Divides the vector by the given scalar.
	 * @param scalar Scalar value to divide by.
	 * @return The resulting vector of the scalar division operation.
	 */
	public Vector3D divide(double scalar) {
		return new Vector3D(x / scalar, y / scalar, z / scalar);
	}
	
	/**
	 * Converts the vector to a point.
	 * @return The point representation of the vector.
	 */
	public Point3D toPoint() {
		return new Point3D(x, y, z);
	}
	
	/**
	 * Gets the x-direction of the vector.
	 * @return The x-direction of the vector.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Gets the y-direction of the vector.
	 * @return The y-direction of the vector.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Gets the z-direction of the vector.
	 * @return The z-direction of the vector.
	 */
	public double getZ() {
		return z;
	}
	
	public String toString() {
		return x + " " + y + " " + z;
	}
}
