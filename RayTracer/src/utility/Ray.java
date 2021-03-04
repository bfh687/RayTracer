package utility;

public class Ray {
	private Vector3D origin;
	private Vector3D direction;
	
	public Ray(Vector3D origin, Vector3D direction) {
		this.origin = origin;
		this.direction = direction.normalize();
	}
	
	public Point3D pointAt(float t) {
		Vector3D temp = origin.add(direction.multiply(t));
		return new Point3D(temp.getX(), temp.getY(), temp.getZ());
	}
	
	public Vector3D getOrigin() {
		return origin;
	}
	
	public Vector3D getDirection() {
		return direction;
	}
	
	@Override
	public String toString() {
		return "Ray[" + origin.toString() + ", " + direction.toString();
	}
}
