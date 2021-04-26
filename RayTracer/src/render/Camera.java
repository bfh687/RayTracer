package render;

import utility.Vector3D;

public class Camera {
	
	public static final Vector3D UP_VECTOR = new Vector3D(0, 1, 0);
	private Vector3D lookFrom, lookTo;
	
	public Camera() {
		lookFrom = (new Vector3D(0, -.5, -10));
		lookTo = new Vector3D(0, 0, 0);
	}
	
	public Camera(Vector3D lookFrom, Vector3D lookTo) {
		this.lookFrom = lookFrom;
		this.lookTo = lookTo;
	}
	
	public Vector3D getLookFrom() { 
		return lookFrom;
	}
	public Vector3D getLookTo() {
		return lookTo;
	}
	public void setLookFrom(Vector3D lookFrom) {
		this.lookFrom = lookFrom;
	}
	public void setLookTo(Vector3D lookTo) {
		this.lookTo = lookTo;
	}
}
