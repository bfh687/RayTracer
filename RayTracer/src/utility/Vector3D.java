package utility;


public class Vector3D {
	private double x, y, z;
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Vector3D otherVector) {
		this.x = otherVector.getX();
		this.y = otherVector.getY();
		this.z = otherVector.getZ();
	}
	
	public double magnitude() {
		return Math.sqrt(x*x + y*y + z*z);
	}

	public Vector3D normalize() {
		double mag = magnitude();
		return new Vector3D(x / mag, y / mag, z / mag);
	}

	public double dotProduct(Vector3D otherVector) {
		double x = this.x * otherVector.getX();
		double y = this.y * otherVector.getY();
		double z = this.z * otherVector.getZ();
		return x + y + z;
	}
	
	public Vector3D add(Vector3D otherVector) {
		double x = this.x + otherVector.getX();
		double y = this.y + otherVector.getY();
		double z = this.z + otherVector.getZ();
		return new Vector3D(x, y, z);
	}
	
	public Vector3D subtract(Vector3D otherVector) {
		double x = this.x - otherVector.getX();
		double y = this.y - otherVector.getY();
		double z = this.z - otherVector.getZ();
		return new Vector3D(x, y, z);
	}
	
	public Vector3D multiply(double scalar) {
		return new Vector3D(x * scalar, y * scalar, z * scalar);
	}
	
	public Vector3D divide(double scalar) {
		return new Vector3D(x / scalar, y / scalar, z / scalar);
	}
	
	public Point3D toPoint() {
		return new Point3D(x, y, z);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	public String toString() {
		return "V(" + x + ", " + y + "," + z + ")";
	}
}
