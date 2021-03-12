package utility;

// 3d point in space
public class Point3D {
	private double x, y, z;
	
	public Point3D() {
		x = y = z = 0;
	}
	
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = -y;
		this.z = z;
	}
	
	public Point3D(Point3D otherPoint) {
		this.x = otherPoint.getX();
		this.y = otherPoint.getY();
		this.z = otherPoint.getZ();
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
	
	public Vector3D toVector() {
		return new Vector3D(x, y, z);
	}
	
	@Override
	public String toString() {
		return "P(" + x + ", " + y + ", " + z + ")";
	}
}
